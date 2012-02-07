package com.opencredo.sandbox.aleksav.esper.agg;

import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.agg.AggregationValidationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Aleksa Vukotic
 */
public class BigDecimalRoundingAverageAggregator extends AggregationSupport {
    private BigDecimal sum = BigDecimal.ZERO;

    private long numDataPoints;
    private int scale = 2;
    private RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    public void clear() {
        sum = BigDecimal.ZERO;
        numDataPoints = 0;
    }

    public void enter(Object object) {
        if (object == null) {
            return;
        }
        numDataPoints++;
        if (object instanceof BigDecimal) {
            sum = sum.add((BigDecimal) object);
        } else if (object instanceof Number) {
            sum = sum.add(new BigDecimal(((Number) object).doubleValue()));
        }else{
            throw new RuntimeException("Must be a number");
        }

    }

    public void leave(Object object) {
        if (object == null) {
            return;
        }
        numDataPoints--;
        if (object instanceof BigDecimal) {
            sum = sum.add((BigDecimal) object);
        } else if (object instanceof Number) {
            sum = sum.add(new BigDecimal(((Number) object).doubleValue()));
        }else{
            throw new RuntimeException("Must be a number");
        }
    }

    public Object getValue() {
        if (numDataPoints == 0) {
            return null;
        }
        return sum.divide(new BigDecimal(numDataPoints), scale, roundingMode);
    }

    public Class getValueType() {
        return BigDecimal.class;
    }

    @Override
    public void validate(AggregationValidationContext validationContext) {
        for (Class clazz : validationContext.getParameterTypes()) {
            if (!clazz.isAssignableFrom(BigDecimal.class) && !clazz.isAssignableFrom(Number.class)) {
                throw new RuntimeException("Argument must be either BigDecimal or Number");
            }
        }
    }
}
