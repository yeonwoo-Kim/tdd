package com.example.tdd.money;

public class Franc extends Money {
    Franc(int amount) {
        this.amount = amount;
    }

    Franc times(int multilier) {
        return new Franc(amount * multilier);
    }
}
