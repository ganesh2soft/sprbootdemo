package com.hcl.sprbootdemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hcl.sprbootdemo.entity.Orders;
import com.hcl.sprbootdemo.entity.Payments;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.PaymentDTO;
import com.hcl.sprbootdemo.payload.StripePaymentDto;
import com.hcl.sprbootdemo.repository.OrdersRepository;
import com.hcl.sprbootdemo.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;

@Service
public class StripePaymentServiceImpl extends StripePaymentServiceAbstract {

    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public StripePaymentServiceImpl(PaymentRepository paymentRepository,
                                    OrdersRepository ordersRepository,
                                    ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.ordersRepository = ordersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO createStripePayment(StripePaymentDto stripePaymentDto, Long orderId) throws StripeException {
        // 1️⃣ Set API key
        com.stripe.Stripe.apiKey = stripeApiKey;

        // 2️⃣ Create PaymentIntent
        PaymentIntent paymentIntent = createPaymentIntent(stripePaymentDto);

        // 3️⃣ Fetch the associated order
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));
        //
        Payments payment = new Payments();
        payment.setPaymentIntentId(paymentIntent.getId());
        payment.setAmount(stripePaymentDto.getAmount() / 100.0);
        payment.setCurrency(stripePaymentDto.getCurrency());
        payment.setStatus(paymentIntent.getStatus());
        payment.setDescription(paymentIntent.getDescription());
        payment.setCustomerEmail(stripePaymentDto.getEmail());
        payment.setCustomerName(stripePaymentDto.getName());
        payment.setPaymentGateway("Stripe");
        payment.setPaymentMethod("card"); // ✅ Set a non-null value
        payment.setOrder(order); // link to order

        paymentRepository.save(payment);

        // 5️⃣ Set payment reference in order
        order.setPayment(payment);
        ordersRepository.save(order);

        // 6️⃣ Map to DTO and include orderId
        PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
        paymentDTO.setOrderId(order.getOrderId());

        return paymentDTO;
    }

    @Override
    protected PaymentIntent createPaymentIntent(StripePaymentDto stripePaymentDto) throws StripeException {
        // 1️⃣ Find or create Stripe customer
        Customer customer;
        CustomerSearchParams searchParams = CustomerSearchParams.builder()
                .setQuery("email:'" + stripePaymentDto.getEmail() + "'")
                .build();
        CustomerSearchResult customers = Customer.search(searchParams);

        if (customers.getData().isEmpty()) {
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setEmail(stripePaymentDto.getEmail())
                    .setName(stripePaymentDto.getName())
                    .build();
            customer = Customer.create(customerParams);
        } else {
            customer = customers.getData().get(0);
        }

        // 2️⃣ Create PaymentIntent
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(stripePaymentDto.getAmount())
                .setCurrency(stripePaymentDto.getCurrency())
                .setCustomer(customer.getId())
                .setDescription(stripePaymentDto.getDescription())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        return PaymentIntent.create(params);
    }
}
