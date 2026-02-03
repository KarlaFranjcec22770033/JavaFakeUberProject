package model.entity.XML;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "log")
@XmlAccessorType(XmlAccessType.FIELD)
public class LogBook {

    @XmlElement(name = "entry")
    private List<LogEntry> logs=new ArrayList<>();

    public LogBook() {}

    public List<LogEntry> getLogs() {
        return logs;
    }

    public void addLogs(LogEntry log) {
        logs.add(log);
    }
}
