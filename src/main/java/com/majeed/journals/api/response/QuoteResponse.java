package com.majeed.journals.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponse {
    private List<Quote> quotes;
    private int total;
    private int skip;
    private int limit;

    @Getter
    @Setter
    public static class Quote {
        private int id;
        private String quote;
        private String author;
    }
}




