package model.entity.XML;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class LogEntry {

    private String time;
    private String happened;

    public LogEntry(String happened) {
        time= LocalDateTime.now().toString();
        this.happened = happened;
    }

    public LogEntry() {}

    public String getTime() {
        return time;
    }

    public String getHappened() {
        return happened;
    }
}
