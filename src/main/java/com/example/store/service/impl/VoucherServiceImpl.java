package com.example.store.service.impl;

import com.example.store.dto.request.VoucherRequestDTO;
import com.example.store.dto.response.VoucherResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Vouchers;
import com.example.store.enums.DiscountType;
import com.example.store.repository.VoucherRepository;
import com.example.store.service.VoucherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository, ModelMapper modelMapper) {
        this.voucherRepository = voucherRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceResponseDTO<VoucherResponseDTO> save(VoucherRequestDTO voucherRequestDTO) {
        Vouchers vouchers = modelMapper.map(voucherRequestDTO, Vouchers.class);
        vouchers.setType(DiscountType.valueOf(voucherRequestDTO.getType().toUpperCase()));
        vouchers.setValue(valueDiscountByType(vouchers.getType(), voucherRequestDTO.getValue()));
        voucherRepository.save(vouchers);
        VoucherResponseDTO voucherResponseDTO = new VoucherResponseDTO(vouchers);
        return ServiceResponseDTO.success(HttpStatus.OK,"", voucherResponseDTO);
    }

    @Override
    public Boolean useVoucher(UUID id, Double totalAmount, Integer quantityUseVoucher) {
        Vouchers voucher = voucherRepository.findById(id).orElseThrow(() -> new RuntimeException("Voucher not found"));
        if(voucher.getIsActive()){
            if (voucher.getUserLimit() != null && voucher.getUserLimit() >= quantityUseVoucher) {
                return false;
            }
            if(totalAmount >= voucher.getMinPurchaseAmount()){
                if(checkDate(voucher.getStartDate(), voucher.getEndDate())){
                    voucher.setUsageLimit(voucher.getUsageLimit() - 1);
                    voucherRepository.save(voucher);
                    return true;
                }
            }
        }
        return false;
    }

    private Double valueDiscountByType (DiscountType discountType, double value) {
        if(discountType == DiscountType.PERCENTAGE) {
            return value <= 100.0 ? value : 100;
        }else {
            return value;
        }
    }

    private Boolean checkDate(Instant start, Instant end) {
        LocalDateTime now = LocalDateTime.now();
        return (now.isEqual(ChronoLocalDateTime.from(start)) || now.isAfter(ChronoLocalDateTime.from(start))) &&
                (now.isEqual(ChronoLocalDateTime.from(end)) || now.isBefore(ChronoLocalDateTime.from(end)));
    }
}
