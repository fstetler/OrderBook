package com.example.orderbook.orderbook.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Currencies {
    SEK,
    USD,
    EUR;

    @JsonValue
    public String toJson() {
        return name();
    }
}
