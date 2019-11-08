package romanav.analizermoc.controlers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import romanav.analizermoc.controlers.responses.ServiceStatus;

import javax.annotation.PostConstruct;
import java.io.IOException;

@ControllerAdvice
@RestController
public class StatusController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void afterInit(){
        logger.info("MS status controller started");
    }

    @GetMapping(value = {"/status"})
    public ServiceStatus getStatus() throws IOException {
        return new ServiceStatus("up");
    }

}
