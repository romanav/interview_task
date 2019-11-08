package romanav.analizermoc.controlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import romanav.analizermoc.controlers.body.DetectorData;


@ControllerAdvice
@RestController
public class InputControler {

    @PostMapping("/input/addEntry")
    public void addEntry(@RequestBody DetectorData data){

        return;

    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Throwable> generalExceptionHandler(Throwable ex, WebRequest request) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
}
