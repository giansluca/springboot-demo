package org.gmdev.springbootdemo.api;

import org.gmdev.springbootdemo.model.dto.quote.QuoteDto;
import org.gmdev.springbootdemo.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@RequestMapping("api/v1/quote")
@RestController
public class QuoteController {

    private static final Logger LOG = Logger.getLogger(QuoteController.class.getName());
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/locale")
    public String index(HttpServletRequest request) {
        String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toLanguageTag();
        LOG.info("Locale: " + locale);
        return locale;
    }

    @GetMapping
    public QuoteDto getQuote() {
        return quoteService.getQuote();
    }
}
