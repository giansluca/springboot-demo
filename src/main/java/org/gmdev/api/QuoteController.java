package org.gmdev.api;

import org.gmdev.service.QuoteService;
import org.gmdev.model.dto.QuoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

@RequestMapping("api/v1/quote")
@Validated
@RestController
public class QuoteController {

    private static final Logger logger = Logger.getLogger(QuoteController.class.getName());
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public QuoteDto getQuote() {
        return quoteService.getQuote();
    }
}
