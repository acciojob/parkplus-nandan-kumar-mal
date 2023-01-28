package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation = reservationRepository2.findById(reservationId).get();

        Payment payment = new Payment();

        if(mode.toUpperCase().equals("CASH")){
            payment.setPaymentMode(PaymentMode.CASH);
        } else if (mode.toUpperCase().equals("CARD")) {
            payment.setPaymentMode(PaymentMode.CARD);
        } else if (mode.toUpperCase().equals("UPI")) {
            payment.setPaymentMode(PaymentMode.UPI);
        }else{
            throw new Exception("Payment mode not detected");
        }

        if(reservation.getBillAmount()==amountSent){
            payment.setIsPaymentCompleted(true);
        }
        payment.setReservation(reservation);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        paymentRepository2.save(payment);

        return payment;
    }
}
