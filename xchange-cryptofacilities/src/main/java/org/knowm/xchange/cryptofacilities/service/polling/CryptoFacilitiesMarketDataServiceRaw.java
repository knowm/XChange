package org.knowm.xchange.cryptofacilities.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulativeBidAsk;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesInstruments;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTicker;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTickers;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesMarketDataServiceRaw extends CryptoFacilitiesBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CryptoFacilitiesTicker getCryptoFacilitiesTicker(CurrencyPair currencyPair) throws IOException {

    CryptoFacilitiesTicker ticker = cryptoFacilities.getTickers().getTicker(currencyPair.base.toString());

    return ticker;
  }

  public CryptoFacilitiesTickers getCryptoFacilitiesTickers() throws IOException {

    CryptoFacilitiesTickers tickers = cryptoFacilities.getTickers();

    return tickers;
  }

  public CryptoFacilitiesInstruments getCryptoFacilitiesInstruments() throws IOException {

    CryptoFacilitiesInstruments instruments = cryptoFacilities.getInstruments();

    return instruments;
  }

  public CryptoFacilitiesCumulativeBidAsk getCryptoFacilitiesCumulativeBidAsk(CurrencyPair currencyPair) {
    CryptoFacilitiesCumulativeBidAsk cfcbidask = cryptoFacilities.getCumulativeBidAsk(currencyPair.base.toString(), currencyPair.counter.toString());

    return cfcbidask;
  }

}
