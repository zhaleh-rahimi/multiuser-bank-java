package server.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by DOTIN SCHOOL 4 on 9/14/2016.
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response implements Serializable {
    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "deposit")
    private String deposit;
    @XmlElement(name = "type")
    private String type;
    @XmlElement(name = "depositBalance")
    private BigDecimal depositBalance;
    @XmlElement(name = "message")
    private String message;

    public Response() {


    }

    public BigDecimal getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id: " + getId() + ", " + "deposit: " + getDeposit() + ", " + "deposit Balance: " + getDepositBalance() + ", " + "type: " + getType() + ", " + getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
