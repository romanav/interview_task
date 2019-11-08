package romanav.analizermoc.Entities;

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

}
