package org.knowm.xchange.bitfinex.v2.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v2.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BitfinexMarketDataService extends BitfinexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return Arrays.stream(getBitfinexTickers(exchange.getExchangeSymbols()))
        .map(BitfinexAdapters::adaptTicker)
        .collect(Collectors.toList());
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitfinexAdapters.adaptTicker(getBitfinexTicker(currencyPair));
  }

   /**
   * @param currencyPair The CurrencyPair for which to query public trades.
   * @param args Upto 4 numeric arguments may be supplied
   *     limitTrades, startTimestamp (Unix millisecs), endTimestamp (Unix millisecs), sort -1 / 1
   *     (if = 1 it sorts results returned with old > new)
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    int limitTrades = 1000;
    long startTimestamp = 0;
    long endTimestamp = 0;
    int sort = -1;

    if (args != null) {
      for (int i=0; i < args.length; i++) {
        if (args[i] instanceof Number) {
            Number arg = (Number)args[i]; 
            switch(i) {
                case 0: limitTrades = arg.intValue();
                        break;
                case 1: startTimestamp = arg.longValue();
                        break;
                case 2: endTimestamp = arg.longValue();
                        break;
                case 3: sort = arg.intValue();
                        break;
            }
        } else {
          throw new IllegalArgumentException("Extra argument #" + i + " must be an int/long was: " + args[i].getClass());
        }
      }
    }
    
    return BitfinexAdapters.adaptPublicTrades(getBitfinexPublicTrades(currencyPair, limitTrades, startTimestamp, endTimestamp, sort), currencyPair);
  }
}
