package com.xeiam.xchange.mercadobitcoin;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.mercadobitcoin.service.polling.account.MercadoBitcoinAccountService;
import com.xeiam.xchange.mercadobitcoin.service.polling.marketdata.MercadoBitcoinMarketDataService;
import com.xeiam.xchange.mercadobitcoin.service.polling.trade.MercadoBitcoinTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.mercadobitcoin.net");
    exchangeSpecification.setHost("www.mercadobitcoin.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Mercado Bitcoin");
    exchangeSpecification.setExchangeDescription("Mercado Bitcoin is a Bitcoin and Litecoin exchange registered in Brazil.");
    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new MercadoBitcoinMarketDataService(exchangeSpecification);
    this.pollingAccountService = new MercadoBitcoinAccountService(exchangeSpecification);
    this.pollingTradeService = new MercadoBitcoinTradeService(exchangeSpecification);
  }

  @Override
  public StreamingExchangeService getStreamingExchangeService(ExchangeStreamingConfiguration configuration) {

    // "Mercado Bitcoin does not support streaming yet (Nov 2014)."
    throw new NotAvailableFromExchangeException();
  }

}
