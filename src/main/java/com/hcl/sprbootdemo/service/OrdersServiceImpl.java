package com.hcl.sprbootdemo.service;

import com.hcl.sprbootdemo.entity.*;
import com.hcl.sprbootdemo.exception.ResourceNotFoundException;
import com.hcl.sprbootdemo.payload.*;
import com.hcl.sprbootdemo.repository.*;
import com.hcl.sprbootdemo.service.OrdersService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        return ordersRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByEmail(String email) {
        List<Orders> orders = ordersRepository.findByEmail(email);
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (Orders order : orders) {
            OrderDTO dto = new OrderDTO();
            dto.setOrderId(order.getOrderId());
            dto.setEmail(order.getEmail());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setAddress(order.getAddress());

            // ✅ Payment mapping
            if (order.getPayment() != null) {
                PaymentDTO paymentDTO = new PaymentDTO();
                paymentDTO.setPaymentId(order.getPayment().getPaymentId());
                paymentDTO.setPaymentMethod(order.getPayment().getPaymentMethod());
                paymentDTO.setPgPaymentId(order.getPayment().getPgPaymentId());
                paymentDTO.setPgStatus(order.getPayment().getPgStatus());
                paymentDTO.setPgResponseMessage(order.getPayment().getPgResponseMessage());
                dto.setPayment(paymentDTO);
            }

            // ✅ Order items mapping
            List<OrderItemDTO> itemDTOs = new ArrayList<>();
            for (OrderItem item : order.getOrderItems()) {
                OrderItemDTO itemDTO = new OrderItemDTO();
                itemDTO.setOrderItemId(item.getOrderItemId());
                itemDTO.setPlacedQty(item.getPlacedQty());
                itemDTO.setOrderedProductPrice(item.getOrderedProductPrice());
                itemDTO.setDiscount(item.getDiscount());

                // ✅ Product mapping
                if (item.getProduct() != null) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductId(item.getProduct().getProductId());
                    productDTO.setProductName(item.getProduct().getProductName());
                    productDTO.setBrandName(item.getProduct().getBrandName());
                    productDTO.setCategory(item.getProduct().getCategory());
                    productDTO.setPrice(item.getProduct().getPrice());
                    productDTO.setSpecialPrice(item.getProduct().getSpecialPrice());
                    productDTO.setDiscount(item.getProduct().getDiscount());
                    productDTO.setImageURL(item.getProduct().getImageURL());
                    productDTO.setQuantity(item.getProduct().getQuantity());
                    itemDTO.setProductDTO(productDTO);
                }

                itemDTOs.add(itemDTO);
            }

            dto.setOrderItems(itemDTOs);
            orderDTOs.add(dto);
        }

        return orderDTOs;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(String email, OrderDTO orderDTO) {

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Orders order = new Orders();
        order.setEmail(user.getEmail()); // ✅ just store email string
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("PENDING");
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setAddress(orderDTO.getAddress());

        // ... then continue with your order items etc.
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream().map(itemDTO -> {
            Products product = productsRepository.findById(itemDTO.getProductDTO().getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemDTO.getProductDTO().getProductId()));

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

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    
   

    @Transactional
    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO updatedOrderDTO) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));

        order.setOrderStatus(updatedOrderDTO.getOrderStatus());
        order.setAddress(updatedOrderDTO.getAddress());
        order.setTotalAmount(updatedOrderDTO.getTotalAmount());

        // ✅ Update payment details if present
        if (updatedOrderDTO.getPayment() != null) {
            Payments payment = order.getPayment();
            payment.setPgStatus(updatedOrderDTO.getPayment().getPgStatus());
            payment.setPgResponseMessage(updatedOrderDTO.getPayment().getPgResponseMessage());
            paymentRepository.save(payment);
        }

        ordersRepository.save(order);
        return convertToDTO(order);
    }

    // 🔁 Entity → DTO mapping helper
    private OrderDTO convertToDTO(Orders order) {
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);

        if (order.getPayment() != null) {
            dto.setPayment(modelMapper.map(order.getPayment(), PaymentDTO.class));
        }

        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(item -> {
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    itemDTO.setOrderItemId(item.getOrderItemId());
                    itemDTO.setPlacedQty(item.getPlacedQty());
                    itemDTO.setDiscount(item.getDiscount());
                    itemDTO.setOrderedProductPrice(item.getOrderedProductPrice());
                    itemDTO.setProductDTO(modelMapper.map(item.getProduct(), ProductDTO.class));
                    return itemDTO;
                })
                .collect(Collectors.toList());

        dto.setOrderItems(orderItemDTOs);
        return dto;
    }
}
