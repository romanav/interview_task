package romanav.analizermoc.controlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import romanav.analizermoc.Entities.DetectedDataEntry;
import romanav.analizermoc.controlers.body.MedianGetterBody;
import romanav.analizermoc.mongo.DetectedDataRepository;

import java.util.List;

@ControllerAdvice
@RestController
public class ServerController {


    private DetectedDataRepository repository;

    @Autowired
    public ServerController(DetectedDataRepository repository){

        this.repository = repository;
    }

    @GetMapping("/server/getMedians")
    public List<DetectedDataEntry> getMedians(@RequestBody MedianGetterBody body){

        List<DetectedDataEntry> values = repository.findByPublisher(body.getPublisher());


        return null;

    }
}
