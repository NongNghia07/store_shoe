package com.example.store.service.impl;

import com.example.store.dto.request.OrderRequestDTO;
import com.example.store.dto.request.Order_ProductRequestDTO;
import com.example.store.dto.response.OrderResponseDTO;
import com.example.store.dto.response.Order_ProductResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Order;
import com.example.store.enums.OrderStatus;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.OrderRepository;
import com.example.store.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final Order_ProductService order_productService;
    private final ProductService productService;
    private final Product_VariantService productVariantService;
    private final BillService billService;
    private final VoucherService voucherService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, Order_ProductService orderProductService, ProductService productService, Product_VariantService productVariantService, BillService billService, VoucherService voucherService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        order_productService = orderProductService;
        this.productService = productService;
        this.productVariantService = productVariantService;
        this.billService = billService;
        this.voucherService = voucherService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceResponseDTO<OrderResponseDTO> create(OrderRequestDTO orderRequestDTO, Boolean isByCustomer) {
        Order order = modelMapper.map(orderRequestDTO, Order.class);
        order.setCreateDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount(orderRequestDTO.getOrder_products()));
        if(!voucherService.useVoucher(orderRequestDTO.getVoucher().getId(), order.getTotalAmount(),
                orderRepository.quantityUserUseVoucher(orderRequestDTO.getUser().getId(), order.getVoucher().getId())))
        {
            order.setVoucher(null);
        }
        Order savedOrder = orderRepository.save(order);
        Set<Order_ProductResponseDTO> savedOrderProductDTO = order_productService.createAll(savedOrder, orderRequestDTO.getOrder_products());
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order);
        orderResponseDTO.setOrderProductDTOs(savedOrderProductDTO);
        return ServiceResponseDTO.success(HttpStatus.OK, orderResponseDTO);
    }

    // Tạo bill
    @Override
    public ServiceResponseDTO<Set<OrderResponseDTO>> updateOrderStatus(List<UUID> ids, String toStatus) {
        try {
            List<Order> orders = new ArrayList<>();
            OrderStatus status = OrderStatus.valueOf(toStatus.toUpperCase());
            for (UUID id : ids) {
                Order order = orderRepository.findOrderById(id);
                if(!isEditStatus(order.getStatus(), status)) {
                    throw new ApiRequestException("Order has not been paid or the customer has not received the goods.");
                }
                order.setStatus(status);
                orders.add(order);
            }
            List<Order> savedOrder = orderRepository.saveAll(orders);
            if(status == OrderStatus.PROCESSING || status == OrderStatus.RETURNED || status == OrderStatus.CANCELLED)
                updateQuantityProduct(savedOrder, status);
            if(status == OrderStatus.COMPLETED) billService.create(savedOrder); // create bill
            Set<OrderResponseDTO> orderResponseDTOs = new HashSet<>();
            for (Order order : savedOrder) {
                orderResponseDTOs.add(new OrderResponseDTO(order));
            }
            return ServiceResponseDTO.success(HttpStatus.OK, orderResponseDTOs);
        }catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    private Double totalAmount(List<Order_ProductRequestDTO> order_productRequestDTOs){
        double totalAmount = 0;
        for (Order_ProductRequestDTO order_product : order_productRequestDTOs) {
            totalAmount += order_product.getUnitPrice() * order_product.getQuantity();
        }
        return totalAmount;
    }

    private void updateQuantityProduct(List<Order> orders, OrderStatus status){
        for (Order order : orders) {
            Map<String, Object> map = new HashMap<>();
            if(status == OrderStatus.PROCESSING){
                map = productVariantService.sellQuantity(order.getOrder_Products());
            }else {
                map = productVariantService.addQuantity(null, order.getOrder_Products());
            }
            productService.updateQuantity((UUID) map.get("productID"), (Integer) map.get("totalQuantity"));
        }
    }

    private Boolean isEditStatus(OrderStatus status, OrderStatus toStatus){
        if(toStatus == OrderStatus.CONFIRMED){
            return status == OrderStatus.PENDING || status == OrderStatus.PAID;
        }
        if(toStatus == OrderStatus.PROCESSING){
            return status == OrderStatus.CONFIRMED || status == OrderStatus.PAID;
        }
        if(toStatus == OrderStatus.SHIPPED){
            return status == OrderStatus.PROCESSING;
        }
        if(toStatus == OrderStatus.DELIVERED){
            return status == OrderStatus.SHIPPED;
        }
        if(toStatus == OrderStatus.CANCELLED){
            return status != OrderStatus.SHIPPED && status != OrderStatus.COMPLETED;
        }
        if(toStatus == OrderStatus.RETURNED){
            return status == OrderStatus.DELIVERED || status == OrderStatus.COMPLETED;
        }
        if(toStatus == OrderStatus.PAID){
            return status == OrderStatus.PENDING;
        }
        if(toStatus == OrderStatus.AWAITING_PAYMENT){
            return status == OrderStatus.DELIVERED;
        }
        if(toStatus == OrderStatus.PAYMENT_COLLECTED){
            return status == OrderStatus.AWAITING_PAYMENT;
        }
        if(toStatus == OrderStatus.COD_FAILED){
            return status == OrderStatus.AWAITING_PAYMENT;
        }
        if(toStatus == OrderStatus.COMPLETED){
            return status == OrderStatus.PAYMENT_COLLECTED || status == OrderStatus.DELIVERED;
        }
        return false;
    }
}
