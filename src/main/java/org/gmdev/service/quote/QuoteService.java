package org.gmdev.service.quote;

import org.gmdev.api.quote.model.GetQuoteApiRes;
import org.gmdev.api.quote.model.GetQuoteClientRes;
import org.gmdev.clients.QuoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {

    private final QuoteClient quoteClient;

    @Autowired
    public QuoteService(QuoteClient quoteClient) {
        this.quoteClient = quoteClient;
    }

    public List<GetQuoteApiRes> getQuote() {
        return quoteClient.getQuote().stream()
                .map(GetQuoteClientRes::toApiRes)
                .toList();
    }

}
