package romanav.analizermoc.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class DetectedDataEntry {

    @Id
    private String uuid;
    private String publisher;
    private Date time;
    private double median;

    public DetectedDataEntry(String publisher, Date time, double median){
        this.publisher = publisher;
        this.time = time;
        this.median = median;
    }

    public String getPublisher() {
        return publisher;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone = "UTC")
    public Date getTime() {
        return time;
    }

    public double getMedian() {
        return median;
    }
}
