package org.knowm.xchange.yacuna.service.polling;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.yacuna.Yacuna;
import org.knowm.xchange.yacuna.dto.marketdata.YacunaTicker;
import org.knowm.xchange.yacuna.dto.marketdata.YacunaTickerReturn;

/**
 * Created by Yingzhe on 12/27/2014.
 */
public class YacunaMarketDataServiceRaw extends YacunaBasePollingService<Yacuna> {

  /**
   * Constructor
   *
   * @param exchange
   */
  public YacunaMarketDataServiceRaw(Exchange exchange) {

    super(Yacuna.class, exchange);
  }

  public List<YacunaTicker> getAllTickers() throws IOException {

    YacunaTickerReturn tickersReturn = this.yacuna.getTickers();
    return tickersReturn != null && tickersReturn.getTickerList() != null ? tickersReturn.getTickerList() : null;
  }

  public YacunaTicker getYacunaTicker(CurrencyPair currencyPair) throws IOException {

    if (!this.getCurrencyPairMap()
        .containsKey(currencyPair.base.getCurrencyCode().toUpperCase() + "_" + currencyPair.counter.getCurrencyCode().toUpperCase())) {
      return null;
    }

    YacunaTickerReturn tickerReturn = this.yacuna.getTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    return tickerReturn != null && tickerReturn.getTickerList() != null && tickerReturn.getTickerList().size() == 1
        ? tickerReturn.getTickerList().get(0) : null;
  }

  protected HashMap<String, CurrencyPair> getCurrencyPairMap() throws IOException {

    HashMap<String, CurrencyPair> currencyPairMap = new HashMap<String, CurrencyPair>();
    YacunaTickerReturn tickers = this.yacuna.getTickers();

    if (tickers != null && tickers.getTickerList() != null) {
      for (YacunaTicker yt : tickers.getTickerList()) {
        String base = yt.getBaseCurrency().toUpperCase();
        String target = yt.getTargetCurrency().toUpperCase();
        currencyPairMap.put(base + "_" + target, new CurrencyPair(base, target));
      }
    }

    return currencyPairMap;
  }
}
