package org.gmdev.api.quote;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.quote.model.QuoteClientRes;
import org.gmdev.service.quote.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("api/v1/quote")
@Validated
@RestController
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public List<QuoteClientRes> getQuote() {
        log.info("Incoming call to [QuoteController - getQuote]");
        return quoteService.getQuote();
    }

}
