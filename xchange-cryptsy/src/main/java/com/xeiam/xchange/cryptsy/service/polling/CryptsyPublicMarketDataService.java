package com.xeiam.xchange.cryptsy.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptsy.CryptsyAdapters;
import com.xeiam.xchange.cryptsy.CryptsyCurrencyUtils;
import com.xeiam.xchange.cryptsy.CryptsyExchange;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class CryptsyPublicMarketDataService extends CryptsyPublicMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public CryptsyPublicMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public CryptsyPublicMarketDataService() {

    this(CryptsyExchange.getDefaultPublicExchangeSpecification());
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = super.getCryptsyMarketData(CryptsyCurrencyUtils.convertToMarketId(currencyPair));

    return CryptsyAdapters.adaptPublicTickers(cryptsyMarketData).get(0);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    Map<Integer, CryptsyPublicOrderbook> cryptsyOrderBook = super.getCryptsyOrderBook(CryptsyCurrencyUtils.convertToMarketId(currencyPair));
    return CryptsyAdapters.adaptPublicOrderBooks(cryptsyOrderBook).get(0);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {

    Map<Integer, CryptsyPublicMarketData> cryptsyMarketData = super.getCryptsyMarketData(CryptsyCurrencyUtils.convertToMarketId(currencyPair));

    return CryptsyAdapters.adaptPublicTrades(cryptsyMarketData).get(currencyPair);
  }

}
