package romanav.analizermoc.controlers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
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
        return repository.findByPublisherOrderByTimeDesc(body.getPublisher(), PageRequest.of(0,body.getEntryCount()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Throwable> generalExceptionHandler(Throwable ex, WebRequest request) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
}
