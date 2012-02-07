package com.opencredo.sandbox.aleksav.esper.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import java.math.BigDecimal;

/**
 * @author Aleksa Vukotic
 */
public class SimpleListener implements UpdateListener{

    private BigDecimal averagePrice;
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if(newEvents.length > 0){
            this.averagePrice = new BigDecimal(newEvents[newEvents.length-1].get("avgPrice").toString());
        }
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }
}
