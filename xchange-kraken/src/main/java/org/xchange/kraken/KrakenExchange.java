package org.xchange.kraken;

import org.xchange.kraken.service.polling.KrakenPollingAccountService;
import org.xchange.kraken.service.polling.KrakenPollingMarketDataService;
import org.xchange.kraken.service.polling.KrakenPollingTradeService;

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
    public void applySpecification (ExchangeSpecification exchangeSpecification){
         
        super.applySpecification(exchangeSpecification);
        // Configure the basic services if configuration does not apply
        this.pollingMarketDataService = new KrakenPollingMarketDataService(exchangeSpecification);
        this.pollingTradeService = new KrakenPollingTradeService(exchangeSpecification);
        this.pollingAccountService = new KrakenPollingAccountService(exchangeSpecification);
    }
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
