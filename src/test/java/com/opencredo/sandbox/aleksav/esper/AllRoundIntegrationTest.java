package com.opencredo.sandbox.aleksav.esper;

import com.opencredo.sandbox.aleksav.esper.domain.MarketDataEvent;
import com.opencredo.sandbox.aleksav.esper.listener.SimpleListener;
import org.junit.Before;
import org.junit.Test;
import org.opencredo.esper.EsperStatement;
import org.opencredo.esper.EsperTemplate;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static junit.framework.Assert.assertEquals;

/**
 * @author Aleksa Vukotic
 */
public class AllRoundIntegrationTest {
    EsperTemplate esperTemplate;
    SimpleListener listener = new SimpleListener();


    @Before
    public void setup() {
        esperTemplate = new EsperTemplate();
        esperTemplate.setConfiguration(new ClassPathResource("/esper-configuration.xml"));
        EsperStatement statement = new EsperStatement("select symbol,avgRound(price) as avgPrice from com.opencredo.sandbox.aleksav.esper.domain.MarketDataEvent.win:time(30 second) group by symbol");
        statement.addListener(listener);
        esperTemplate.addStatement(statement);
        esperTemplate.initialize();

    }

    @Test
    public void testIndefinitePrecision() throws InterruptedException {

        esperTemplate.sendEvent(new MarketDataEvent("ACME", new BigDecimal(5.0)));
        esperTemplate.sendEvent(new MarketDataEvent("ACME", new BigDecimal(3.0)));
        esperTemplate.sendEvent(new MarketDataEvent("ACME", new BigDecimal(2.0)));

        Thread.sleep(7000);
        assertEquals("Must have correct average price", new BigDecimal("3.33"), listener.getAveragePrice());
    }


}
