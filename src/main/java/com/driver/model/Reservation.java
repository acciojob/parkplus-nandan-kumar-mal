package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    private int id;

    private int NumberOfHours;


    private int billAmount;


    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Spot spot;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    public Reservation(){

    }

    public Reservation(int NumberOfHours, int billAmount){
        this.NumberOfHours = NumberOfHours;
        this.billAmount = billAmount;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfHours() {
        return NumberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.NumberOfHours = numberOfHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    public int getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }



}
