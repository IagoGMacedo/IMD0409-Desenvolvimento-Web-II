package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.Payment;
import com.macedo.Ecommerce.rest.dto.PaymentResponses.ResponsePaymentDTO;
import com.macedo.Ecommerce.rest.dto.RegisterPaymentDTO;

public interface PaymentService {
    public ResponsePaymentDTO findById(Integer id);
    public ResponsePaymentDTO save(RegisterPaymentDTO Payment);
    public void delete(Integer id);
    public ResponsePaymentDTO update(Integer id, RegisterPaymentDTO Payment);
    public List<ResponsePaymentDTO> findAll(Payment filtro);
    public ResponsePaymentDTO patch(Integer id, RegisterPaymentDTO PaymentIncompletaDto);
}

