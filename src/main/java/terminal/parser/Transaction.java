package terminal.parser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * Created by DOTIN SCHOOL 4 on 9/11/2016.
 */
@XmlRootElement(name = "transaction")
public class Transaction {
    private int id;
    private String type;
    private BigDecimal amount;
    private String deposit;

    public int getId() {
        return id;
    }

    @XmlAttribute(name = "id")
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    @XmlAttribute(name = "type")
    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @XmlAttribute(name = "amount")
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDeposit() {
        return deposit;
    }

    @XmlAttribute(name = "deposit")
    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }


}
