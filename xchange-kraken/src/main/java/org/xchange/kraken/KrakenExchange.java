package org.xchange.kraken;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
/**
 * 
 * @author Benedikt Bünz
 *
 */
public class KrakenExchange extends BaseExchange implements Exchange {

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {

        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
        exchangeSpecification.setSslUri("https://api.kraken.com");
        exchangeSpecification.setHost("api.kraken.com");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("Kraken");
        exchangeSpecification.setExchangeDescription("Kraken is a Bitcoin exchange operated by Payward, Inc.");
        return exchangeSpecification;        
    }

}
