package romanav.analizermoc.Entities;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class DetectorEntry {

//    {
//
//        "publisher": "publisher-id",
//        "time": "2015-11-03 15:03:30.352",
//         "median": 5
//
//    }

    @Id
    private String uuid;
    private String publisher;
    private Date time;
    private double median;

    public DetectorEntry(String uuid, String publisher, Date time, double median){
        this.uuid = uuid;
        this.publisher = publisher;
        this.time = time;
        this.median = median;
    }

}
