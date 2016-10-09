package server.response;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DOTIN SCHOOL 4 on 9/14/2016.
 */
@XmlRootElement(name = "transactions")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionList implements Serializable {
    @XmlAttribute(name = "terminalId")
    private String terminalID;
    @XmlElement(name = "response")
    private ArrayList<Response> responses = null;

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String id) {
        this.terminalID = id;
    }

    public ArrayList<Response> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<Response> responses) {
        this.responses = responses;
    }

    @Override
    public String toString() {
        return getTerminalID() + ", " + getResponses();
    }
}
