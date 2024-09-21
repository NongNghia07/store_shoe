package com.example.store.service.impl;

import com.example.store.dto.request.BillRequestDTO;
import com.example.store.dto.response.BillResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Bill;
import com.example.store.entity.Order;
import com.example.store.entity.Vouchers;
import com.example.store.enums.BillStatus;
import com.example.store.enums.DiscountType;
import com.example.store.enums.OrderStatus;
import com.example.store.repository.BillRepository;
import com.example.store.service.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final ModelMapper modelMapper;
    private static final double TAX_RATE = 0.10;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, ModelMapper modelMapper) {
        this.billRepository = billRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceResponseDTO<List<BillResponseDTO>> findAll() {
        return null;
    }

    @Override
    public ServiceResponseDTO<Page<BillResponseDTO>> findAllPage(Pageable pageable) {
        return null;
    }

    @Override
    public ServiceResponseDTO<BillResponseDTO> findById(Long id) {
        return null;
    }

    @Override
    public Boolean create(List<Order> orders) {
        List<Bill> bills = new ArrayList<>();
        for (Order order : orders) {
            Bill bill = new Bill();
            bill.setOrder(order);
            bill.setCreateDate(LocalDateTime.now());
            bill.setTotalAmount(order.getTotalAmount());
            bill.setTaxAmount(calculateTax(order.getTotalAmount()));
            bill.setPaymentStatus("paid");
            bill.setDiscount(order.getVoucher() != null? order.getVoucher().getValue() : 0);
            bill.setNetAmount(calculateNetAmount(order.getTotalAmount(), bill.getTaxAmount(), order.getVoucher()));
            bills.add(bill);
        }
        billRepository.saveAll(bills);
        return true;
    }

    @Override
    public Boolean updateStatus(Order order) {
        Bill bill = billRepository.findByOrder_Id(order.getId());
        bill.setStatus(setBillStatus(order.getStatus()));
        billRepository.save(bill);
        return true;
    }

    private BillStatus setBillStatus(OrderStatus orderStatus) {
        if(orderStatus == OrderStatus.RETURNED) {
            return BillStatus.CANCELLED;
        }
        return BillStatus.PAID;
    }

    private Double calculateTax(Double totalAmount){
        return totalAmount * TAX_RATE;
    }

    private Double calculateNetAmount(Double totalAmount, Double taxAmount, Vouchers vouchers){
        if(vouchers == null){
            return totalAmount + taxAmount;
        }else {
            if(vouchers.getType() == DiscountType.FIXED_AMOUNT){
                return totalAmount + taxAmount - vouchers.getValue();
            }else {
                return (totalAmount + taxAmount) * (1 - vouchers.getValue() / 100.0);
            }
        }
    }

    // note chưa làm...
    @Override
    public ServiceResponseDTO<BillResponseDTO> update(BillRequestDTO billRequestDTO) {
        Bill bill = modelMapper.map(billRequestDTO, Bill.class);
        bill = billRepository.save(bill);
        BillResponseDTO billResponseDTO = new BillResponseDTO(bill);
        return ServiceResponseDTO.success(HttpStatus.OK, billResponseDTO);
    }
}
