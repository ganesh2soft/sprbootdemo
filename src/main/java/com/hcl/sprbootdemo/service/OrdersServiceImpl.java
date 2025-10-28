package com.hcl.sprbootdemo.service;

import com.hcl.sprbootdemo.entity.*;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.*;
import com.hcl.sprbootdemo.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        return ordersRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByEmail(String email) {
        return ordersRepository.findByEmail(email).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO createOrder(String email, OrderDTO orderDTO) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Orders order = new Orders();
        order.setEmail(user.getEmail());
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("PENDING");
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setAddress(orderDTO.getAddress());

        // Map Order Items and validate stock
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream().map(itemDTO -> {
            Products product = productsRepository.findById(itemDTO.getProductDTO().getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemDTO.getProductDTO().getProductId()));

            if (product.getQuantity() < itemDTO.getPlacedQty()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setPlacedQty(itemDTO.getPlacedQty());
            item.setDiscount(itemDTO.getDiscount());
            item.setOrderedProductPrice(itemDTO.getOrderedProductPrice());

            product.setQuantity(product.getQuantity() - itemDTO.getPlacedQty());
            productsRepository.save(product);

            return item;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        Orders savedOrder = ordersRepository.save(order);

        return convertToDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long orderId, OrderDTO updatedOrderDTO) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));

        order.setOrderStatus(updatedOrderDTO.getOrderStatus());
        order.setAddress(updatedOrderDTO.getAddress());
        order.setTotalAmount(updatedOrderDTO.getTotalAmount());

        // Update payment details safely
        if (updatedOrderDTO.getPayment() != null) {
            if (order.getPayment() == null) {
                Payments newPayment = modelMapper.map(updatedOrderDTO.getPayment(), Payments.class);
                newPayment.setOrder(order);
                paymentRepository.save(newPayment);
                order.setPayment(newPayment);
            } else {
                Payments payment = order.getPayment();
                modelMapper.map(updatedOrderDTO.getPayment(), payment);
                paymentRepository.save(payment);
            }
        }

        ordersRepository.save(order);
        return convertToDTO(order);
    }

    // 🔁 Helper: Entity → DTO mapping
    private OrderDTO convertToDTO(Orders order) {
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);

        // Ensure payment mapping if present
        if (order.getPayment() != null) {
            dto.setPayment(modelMapper.map(order.getPayment(), PaymentDTO.class));
        }

        return dto;
    }
}
