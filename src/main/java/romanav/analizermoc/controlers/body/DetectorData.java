package romanav.analizermoc.controlers.body;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

public class DetectorData {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS", timezone = "UTC")
    private Date time;
    private String publisher;
    private List<Integer> readings;


    public String getPublisher() {
        return publisher;
    }

    public List<Integer> getReadings() {
        return readings;
    }

    public Date getTime() {
        return time;
    }

}
