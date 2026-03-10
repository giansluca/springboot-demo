package org.gmdev.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/v1/async")
@Validated
@RestController
public class AsyncController {

    @Async
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/slow-operation")
    public void slowOperation() {
        try {
            log.info("Incoming call to [AsyncController - slowOperation] 7");
            Thread.sleep(8000);
            log.info("Finish [AsyncController - slowOperation]");
        } catch (InterruptedException e) {
            log.error("Process error {}", e.getMessage());
        }
    }

}
