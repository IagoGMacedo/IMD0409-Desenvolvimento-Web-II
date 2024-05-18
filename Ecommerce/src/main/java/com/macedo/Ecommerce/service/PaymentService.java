package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.Payment;
import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;
import com.macedo.Ecommerce.rest.dto.RegisterPaymentDTO;

public interface PaymentService {
    public List<ResponsePaymentDTO> getPayments(Payment filtro);

    public ResponsePaymentDTO getPaymentById(Integer id);

    public List<ResponsePaymentDTO> getPaymentsByUserId(Integer id);
}
