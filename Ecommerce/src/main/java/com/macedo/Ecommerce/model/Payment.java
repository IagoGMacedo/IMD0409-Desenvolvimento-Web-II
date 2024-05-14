package com.macedo.Ecommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "creditCard_id") 
    private CreditCard creditCard; //armazena se o pagamento tiver sido como cartão de credito;

    @Column
    private Integer installments; //quantidade de parcelas de um pagamento

    @Column(precision = 10,scale = 2)
    private BigDecimal price;

}
