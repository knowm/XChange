package org.knowm.xchange.kuna.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kuna.dto.KunaAccountDto;
import org.knowm.xchange.kuna.dto.KunaAskBid;
import org.knowm.xchange.kuna.dto.KunaDepthDto;
import org.knowm.xchange.kuna.dto.KunaException;
import org.knowm.xchange.kuna.dto.KunaTimeTicker;
import org.knowm.xchange.kuna.dto.KunaTrade;
import org.knowm.xchange.kuna.util.KunaUtils;

/** @author Dat Bui */
public class KunaMarketDataServiceRaw extends KunaBaseService {
  /**
   * Constructor.
   *
   * @param exchange
   */
  protected KunaMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public KunaTimeTicker getKunaTicker(CurrencyPair pair) throws IOException {
    KunaTimeTicker ticker;
    try {
      ticker = getKuna().getTicker(KunaUtils.toPairString(pair));
    } catch (KunaException e) {
      throw new ExchangeException(e.getMessage());
    }
    return ticker;
  }

  public Map<String, KunaTimeTicker> getKunaTickers() throws IOException {
    Map<String, KunaTimeTicker> tickers;
    try {
      tickers = getKuna().getTickers();
    } catch (KunaException e) {
      throw new ExchangeException(e.getMessage());
    }
    return tickers;
  }

  public KunaAccountDto getAccountInfo() throws IOException {
    return getKunaAuthenticated()
        .getAccountInfo(
            exchange.getNonceFactory(),
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator);
  }

  public KunaAskBid getKunaOrderBook(CurrencyPair pair) throws IOException {
    KunaAskBid askBid;
    try {
      askBid = getKuna().getOrders(KunaUtils.toPairString(pair));
    } catch (KunaException e) {
      throw new ExchangeException(e.getMessage());
    }
    return askBid;
  }

  public KunaDepthDto getKunaDepthList(CurrencyPair pair) throws IOException {
    KunaDepthDto depthList;
    try {
      depthList = getKuna().getDepth(KunaUtils.toPairString(pair));
    } catch (KunaException e) {
      throw new ExchangeException(e.getMessage());
    }
    return depthList;
  }

  public KunaTrade[] getKunaTradesHistory(CurrencyPair pair) throws IOException {
    KunaTrade[] trades;
    try {
      trades = getKuna().getTradesHistory(KunaUtils.toPairString(pair));
    } catch (KunaException e) {
      throw new ExchangeException(e.getMessage());
    }
    return trades;
  }

  public Date getServerTime() throws IOException {
    return new Date(getKuna().getTimestamp());
  }
}
