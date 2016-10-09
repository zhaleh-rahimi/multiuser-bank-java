package terminal.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by DOTIN SCHOOL 4 on 9/11/2016.
 */
@XmlRootElement(name = "transactions")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transactions {
    @XmlElement(name = "transaction")
    private ArrayList<Transaction> transactions = null;

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

}
