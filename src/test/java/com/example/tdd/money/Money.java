package com.example.tdd.money;

class Money implements Expression{
    protected int amount;
    protected String currency;

    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount && currency().equals(money.currency);
    }

    Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Expression times(int multiplier) {
        return new Money(amount * multiplier, currency);
    };


    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    String currency() {
        return currency;
    }

    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    public Money reduce(String to) {
        int rate = (currency.equals("CHF") && to.equals("USD")) ? 2 : 1;
        return new Money(amount / rate, to);
    }

    @Override
    public Money reduce(Bank bank, String to) {
//        int rate = (currency.equals("CHF") && to.equals("USD")) ? 2 : 1;
        int rate = bank.rate(currency, to);
        return new Money(amount / rate, to);
    }
}
