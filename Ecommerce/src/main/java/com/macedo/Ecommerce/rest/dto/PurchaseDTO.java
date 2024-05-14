package com.macedo.Ecommerce.rest.dto;

import com.macedo.Ecommerce.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDTO {
    private Integer id;
    private Integer idUser;
    private List<ProductItemDTO> productItems;

    private PaymentDTO payment;

    // DECIDIR SE EU TIRO OU NÃO ESSES CARAS DAQUI
    private BigDecimal totalPrice;

    private Integer idAddress;
    private LocalDate date;

}
