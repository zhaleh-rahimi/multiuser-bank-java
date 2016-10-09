package terminal.parser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by DOTIN SCHOOL 4 on 9/11/2016.
 */
@XmlRootElement(name = "server")
//@XmlAccessorType(XmlAccessType.FIELD)
public class ServerInfo {
    private String serverIp;
    private Integer serverPort;

    @XmlAttribute(name = "ip")
    public void setIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    @XmlAttribute(name = "port")
    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }


}
