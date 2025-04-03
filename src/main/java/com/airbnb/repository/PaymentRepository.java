package com.airbnb.repository;

import com.airbnb.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

   public Optional<Payment> findByOrderId(String orderId);
}