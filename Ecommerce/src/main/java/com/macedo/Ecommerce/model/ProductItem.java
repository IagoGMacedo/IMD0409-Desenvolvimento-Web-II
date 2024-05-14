package com.macedo.Ecommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "shoppingCart_id") //produto está no carrinho
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "purchase_id") //produto foi comprado
    private Purchase purchase;

    /* 
    @ManyToOne
    @JoinColumn(name = "shoppingCart_id")
    private ShoppingCart shoppingCart; //produto está no carrinho
    */



    

    

    public BigDecimal getTotalItemPrice(){
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
