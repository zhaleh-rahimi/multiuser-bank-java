package terminal.parser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by DOTIN SCHOOL 4 on 9/11/2016.
 */
@XmlRootElement(name = "outLog")
public class OutLog {

    private String path;

    public String getPath() {
        return path;
    }

    @XmlAttribute(name = "path")
    public void setPath(String path) {
        this.path = path;
    }
}
