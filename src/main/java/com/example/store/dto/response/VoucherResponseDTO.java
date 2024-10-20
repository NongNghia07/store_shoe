package com.example.store.dto.response;


import com.example.store.entity.Vouchers;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherResponseDTO {
    private UUID id;

    public VoucherResponseDTO(Vouchers vouchers){
        if(vouchers.getId() != null)
            this.id = vouchers.getId();

    }
}
