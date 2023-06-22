package com.example.tdd.money;

public class Dollar extends Money {
    Dollar(int amount) {
        this.amount = amount;
    }

    Dollar times(int multilier) {
        return new Dollar(amount * multilier);
    }
}
