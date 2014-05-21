package com.xeiam.xchange.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.Hitbtc;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.*;
import com.xeiam.xchange.service.HitbtcBaseService;
import si.mazi.rescu.RestProxyFactory;

import javax.ws.rs.QueryParam;
import java.io.IOException;

/**
 * @author kpysniak
 */
public abstract class HitbtcMarketDataServiceRaw extends HitbtcBaseService {

  private final Hitbtc hitbtc;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected HitbtcMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    this.hitbtc = RestProxyFactory.createProxy(Hitbtc.class, exchangeSpecification.getSslUri());
  }

  public HitbtcTicker getHitbtcTicker(CurrencyPair currencyPair) throws IOException {
    return hitbtc.getHitbtcTicker(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

  public HitbtcOrderBook getHitbtcOrderBook(CurrencyPair currencyPair) throws IOException {
    return hitbtc.getOrderBook(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

  public HitbtcTrades getHitbtcTrades(CurrencyPair currencyPair, long from,
                                      HitbtcTrades.HitbtcTradesSortOrder sortBy, long startIndex, long maxResults) throws IOException {

    return hitbtc.getTrades(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString(),
                            from + "", sortBy.toString(), startIndex + "", maxResults + "", "object");
  }

  public HitbtcSymbols getHitbtcSymbols() throws IOException { return hitbtc.getSymbols();}


}
