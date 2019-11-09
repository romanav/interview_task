package romanav.analizermoc.controlers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import romanav.analizermoc.Entities.DetectedDataEntry;
import romanav.analizermoc.be.MedianCalculator;
import romanav.analizermoc.controlers.body.DetectorData;
import romanav.analizermoc.mongo.DetectedDataRepository;

import java.util.logging.Logger;


@ControllerAdvice
@RestController
public class InputController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private DetectedDataRepository repository;

    @Autowired
    public InputController(DetectedDataRepository repository){
        this.repository = repository;
    }

    @PostMapping("/input/addEntry")
    public void addEntry(@RequestBody DetectorData data){
        logger.info(String.format("Adding entry: %s, %s, %s",data.getPublisher(), data.getReadings(), data.getTime()));
        MedianCalculator calc = new MedianCalculator();
        DetectedDataEntry entry = new DetectedDataEntry(data.getPublisher(), data.getTime(), calc.calculate(data.getReadings()));
        repository.save(entry);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Throwable> generalExceptionHandler(Throwable ex, WebRequest request) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
}
