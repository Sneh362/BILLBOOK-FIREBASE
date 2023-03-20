package com.example.myapp;

public class MoneyFire {
    Number Money;
    String Source;
    String userId;

    public MoneyFire(Number money, String source,String userId) {
        this.Money = money;
        this.Source = source;
        this.userId=userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Number getMoney() {
        return Money;
    }

    public void setMoney(Number money) {
        Money = money;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }
}
