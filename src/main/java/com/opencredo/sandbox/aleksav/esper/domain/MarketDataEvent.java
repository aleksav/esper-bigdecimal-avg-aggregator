package com.opencredo.sandbox.aleksav.esper.domain;

import com.espertech.esper.client.EventBean;

import java.math.BigDecimal;

/**
 * @author Aleksa Vukotic
 */
public class MarketDataEvent {
    private String symbol;

    private BigDecimal price;

    public MarketDataEvent() {
    }

    public MarketDataEvent(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
