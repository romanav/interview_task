package romanav.analizermoc.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import romanav.analizermoc.Entities.DetectedDataEntry;
import romanav.analizermoc.be.MedianCalculator;
import romanav.analizermoc.controlers.body.DetectorData;
import romanav.analizermoc.mongo.DetectedDataRepository;


@ControllerAdvice
@RestController
public class InputController {

    private DetectedDataRepository repository;

    @Autowired
    public InputController(DetectedDataRepository repository){
        this.repository = repository;
    }

    @PostMapping("/input/addEntry")
    public void addEntry(@RequestBody DetectorData data){
        MedianCalculator calc = new MedianCalculator();
        DetectedDataEntry entry = new DetectedDataEntry(data.getPublisher(), data.getTime(), calc.calculate(data.getReadings()));
        repository.save(entry);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Throwable> generalExceptionHandler(Throwable ex, WebRequest request) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
}
