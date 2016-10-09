package terminal.parser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by DOTIN SCHOOL 4 on 9/11/2016.
 */
@XmlRootElement(name = "terminal")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Terminal {
    @XmlElement(name = "server")
    private ServerInfo serverInfo;
    private String ip;
    private Integer port;
    private String path;
    private String terminalId;
    private String terminalType;
    @XmlElement(name = "outlog")
    private OutLog outLog;
    @XmlElement(name = "transactions")
    private Transactions transactions;
    private ArrayList<Transaction> transactionList;

    public String getTerminalId() {
        return terminalId;
    }

    @XmlAttribute(name = "id")
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalType() {
        return terminalType;
    }

    @XmlAttribute(name = "type")
    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getIp() {
        this.ip = serverInfo.getServerIp();
        return ip;
    }

    public Integer getPort() {
        this.port = serverInfo.getServerPort();
        return port;
    }

    public String getPath() {
        this.path = outLog.getPath();
        return path;
    }

    public ArrayList<Transaction> getTransactions() {
        this.transactionList = transactions.getTransactions();
        return transactionList;
    }

}
