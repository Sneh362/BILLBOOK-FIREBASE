package com.example.myapp;

public class Money {
    private String purpose;
    private String money;

    public Money(int id, String purpose, String money) {

        this.purpose = purpose;
        this.money = money;
    }


    public String getMoney() {
        return money;
    }

    public String getPurpose() {
        return purpose;
    }
}
