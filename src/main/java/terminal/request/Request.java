package terminal.request;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by DOTIN SCHOOL 4 on 9/21/2016.
 */
public class Request implements Serializable, Comparable<Request> {
    private String terminalType;
    private String terminalID;
    private int id;
    private String type;
    private BigDecimal amount;
    private String deposit;

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return getTerminalID() + ", " + getTerminalType() + ", " + getId() + ", " + getDeposit() + ", " + getAmount() + ", " + getType();
    }

    /*public int compareTo(Transaction o) {
        return deposit.compareTo(o.getDeposit());
    }*/

    public int compareTo(Request o) {
        return deposit.compareTo(o.getDeposit());
    }
}
