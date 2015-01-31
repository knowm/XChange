package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.empoex.EmpoExUtils;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExLevel;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTicker;
import com.xeiam.xchange.empoex.dto.marketdata.EmpoExTrade;

public class EmpoExMarketDataServiceRaw extends EmpoExBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<EmpoExTicker> getEmpoExTickers() throws IOException {

    return empoEx.getEmpoExTickers();
  }

  public EmpoExTicker getEmpoExTicker(CurrencyPair currencyPair) throws IOException {

    String pairString = EmpoExUtils.toPairString(currencyPair);
    return empoEx.getEmpoExTicker(pairString).get(0);
  }

  public Map<String, List<EmpoExTrade>> getEmpoExTrades(CurrencyPair currencyPair) throws IOException {

    String pairString = EmpoExUtils.toPairString(currencyPair);
    return empoEx.getEmpoExTrades(pairString);
  }

  public Map<String, Map<String, List<EmpoExLevel>>> getEmpoExDepth(CurrencyPair currencyPair) throws IOException {

    String pairString = EmpoExUtils.toPairString(currencyPair);
    return empoEx.getEmpoExDepth(pairString);
  }
}
