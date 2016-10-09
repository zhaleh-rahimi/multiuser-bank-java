package server.parser;

import java.math.BigDecimal;

/**
 * Created by DOTIN SCHOOL 4 on 9/13/2016.
 */
public class Deposit {
    private String customer;
    private String id;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }


    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }


    @Override
    public String toString() {
        return getCustomer() + ", " + getId() + ", " + getInitialBalance() + ", " + getUpperBound();
    }


}
