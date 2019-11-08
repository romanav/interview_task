package romanav.analizermoc.controlers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import romanav.analizermoc.controlers.body.DetectorData;

public class InputControler {

    @PostMapping("input/addEntry")
    public void addEntry(@RequestBody DetectorData data){

    }
}
