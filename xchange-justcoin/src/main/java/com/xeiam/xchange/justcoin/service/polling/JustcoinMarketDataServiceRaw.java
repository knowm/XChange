package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.justcoin.Justcoin;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinPublicTrade;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;

/**
 * @author jamespedwards42
 */
public class JustcoinMarketDataServiceRaw extends JustcoinBasePollingService<Justcoin> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public JustcoinMarketDataServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(Justcoin.class, exchangeSpecification);
  }

  public List<JustcoinTicker> getTickers() throws IOException {

    return justcoin.getTickers();
  }

  public JustcoinDepth getMarketDepth(final String tradableIdentifier, final String currency) throws IOException {

    return justcoin.getDepth(tradableIdentifier.toUpperCase(), currency.toUpperCase());
  }

  public List<JustcoinPublicTrade> getTrades(final String priceCurrency, final Long sinceId) throws IOException {

    return justcoin.getTrades(priceCurrency.toUpperCase(), sinceId);
  }
}
