package com.example.store.dto.response;

import com.example.store.entity.Price_History;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price_HistoryResponseDTO {
    private UUID id;
    private Double oldPrice;
    private Double newPrice;
    private LocalDateTime changeDate;
    private Product_VariantsResponseDTO product_Variants;

    public Price_HistoryResponseDTO(Price_History price_History){
        if(price_History.getId() != null)
            this.id = price_History.getId();
        this.oldPrice = price_History.getOldPrice();
        this.newPrice = price_History.getNewPrice();
        this.product_Variants = new Product_VariantsResponseDTO(price_History.getProductVariant());
    }
}
