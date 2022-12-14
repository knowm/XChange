package org.knowm.xchange.okex;

import org.junit.Before;
import org.junit.Ignore;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

import java.io.IOException;
import java.util.Properties;

@Ignore
public class OkexPrivateDataTest {

    Exchange exchange;

    @Before
    public void setUp(){
        Properties properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream("/secret.keys"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExchangeSpecification spec = new OkexExchange().getDefaultExchangeSpecification();

        spec.setApiKey(properties.getProperty("apikey"));
        spec.setSecretKey(properties.getProperty("secret"));
        spec.setUserName(properties.getProperty("passphrase"));

        exchange = ExchangeFactory.INSTANCE.createExchange(spec);
    }
}
