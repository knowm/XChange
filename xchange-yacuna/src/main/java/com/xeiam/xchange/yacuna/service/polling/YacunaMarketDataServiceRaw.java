package com.xeiam.xchange.yacuna.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.yacuna.Yacuna;
import com.xeiam.xchange.yacuna.dto.marketdata.YacunaTicker;
import com.xeiam.xchange.yacuna.dto.marketdata.YacunaTickerReturn;

import java.io.IOException;
import java.util.List;

/**
 * Created by Yingzhe on 12/27/2014.
 */
public class YacunaMarketDataServiceRaw extends YacunaBasePollingService<Yacuna> {

  public YacunaMarketDataServiceRaw(Exchange exchange) {

    super(Yacuna.class, exchange);
  }

  public List<YacunaTicker> getAllTickers() throws IOException {

    YacunaTickerReturn tickersReturn = this.yacuna.getTickers();
    return tickersReturn != null && tickersReturn.getTickerList() != null ? tickersReturn.getTickerList() : null;
  }

  public YacunaTicker getYacunaTicker(CurrencyPair currencyPair) throws IOException {

    if (!this.getCurrencyPairMap().containsKey(currencyPair.baseSymbol.toUpperCase() + "_" + currencyPair.counterSymbol.toUpperCase())) {
      return null;
    }

    YacunaTickerReturn tickerReturn = this.yacuna.getTicker(currencyPair.baseSymbol, currencyPair.counterSymbol);
    return tickerReturn != null && tickerReturn.getTickerList() != null && tickerReturn.getTickerList().size() == 1 ? tickerReturn.getTickerList().get(0) : null;
  }
}
