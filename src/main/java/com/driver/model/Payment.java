package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {

    private int id;

    Boolean isPaymentCompleted;

    @Enumerated(value = EnumType.STRING)
    PaymentMode paymentMode;

    @OneToOne(mappedBy = "payment",cascade = CascadeType.ALL)
    @JoinColumn
    Reservation reservation;

    public Payment(){

    }

    public Payment(Boolean isPaymentCompleted, PaymentMode paymentMode, Reservation reservation){

        this.isPaymentCompleted = isPaymentCompleted;
        this.paymentMode = paymentMode;
        this.reservation = reservation;
    }

    public Boolean isPaymentCompleted(){

        return this.isPaymentCompleted;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIsPaymentCompleted() {
        return isPaymentCompleted;
    }

    public void setIsPaymentCompleted(Boolean isPaymentCompleted) {
        this.isPaymentCompleted = isPaymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

}
