package com.macedo.Ecommerce.service;

import java.util.List;
import com.macedo.Ecommerce.model.Payment;
import com.macedo.Ecommerce.rest.dto.PaymentDTO;

public interface PaymentService {
    public PaymentDTO findById(Integer id);
    public PaymentDTO save(PaymentDTO Payment);
    public void delete(Integer id);
    public PaymentDTO update(Integer id, PaymentDTO Payment);
    public List<PaymentDTO> findAll(Payment filtro);
    public PaymentDTO patch(Integer id, PaymentDTO PaymentIncompletaDto);
}

