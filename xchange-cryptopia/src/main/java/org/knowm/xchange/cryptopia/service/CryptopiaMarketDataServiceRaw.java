package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptopia.Cryptopia;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTicker;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import si.mazi.rescu.RestProxyFactory;

public class CryptopiaMarketDataServiceRaw extends CryptopiaBaseService {

  private final Cryptopia cryptopia;

  public CryptopiaMarketDataServiceRaw(Exchange exchange) {
    super(exchange);

    this.cryptopia = RestProxyFactory.createProxy(Cryptopia.class, exchange.getExchangeSpecification().getSslUri());
  }

  public List<CryptopiaCurrency> getCryptopiaCurrencies() throws IOException {
    CryptopiaBaseResponse<List<CryptopiaCurrency>> response = cryptopia.getCurrencies();

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public List<CryptopiaTradePair> getCryptopiaTradePairs() throws IOException {
    CryptopiaBaseResponse<List<CryptopiaTradePair>> response = cryptopia.getTradePairs();

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public List<CryptopiaTicker> getCryptopiaMarkets() throws IOException {
    CryptopiaBaseResponse<List<CryptopiaTicker>> response = cryptopia.getMarkets();

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public List<CryptopiaTicker> getCryptopiaMarkets(Currency baseMarket) throws IOException {
    CryptopiaBaseResponse<List<CryptopiaTicker>> response = cryptopia.getMarkets(baseMarket.getCurrencyCode());

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public List<CryptopiaTicker> getCryptopiaMarkets(Currency baseMarket, long hours) throws IOException {
    CryptopiaBaseResponse<List<CryptopiaTicker>> response = cryptopia.getMarkets(baseMarket.getCurrencyCode(), hours);

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public CryptopiaTicker getCryptopiaTicker(CurrencyPair market) throws IOException {
    CryptopiaBaseResponse<CryptopiaTicker> response = cryptopia.getMarket(getPair(market));

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public CryptopiaTicker getCryptopiaTicker(CurrencyPair market, long hours) throws IOException {
    CryptopiaBaseResponse<CryptopiaTicker> response = cryptopia.getMarket(getPair(market), hours);

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public List<CryptopiaMarketHistory> getCryptopiaTrades(CurrencyPair market) throws IOException {
    CryptopiaBaseResponse<List<CryptopiaMarketHistory>> response = cryptopia.getMarketHistory(getPair(market));

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public List<CryptopiaMarketHistory> getCryptopiaTrades(CurrencyPair market, long hours) throws IOException {
    CryptopiaBaseResponse<List<CryptopiaMarketHistory>> response = cryptopia.getMarketHistory(getPair(market), hours);

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public CryptopiaOrderBook getCryptopiaOrderBook(CurrencyPair market) throws IOException {
    CryptopiaBaseResponse<CryptopiaOrderBook> response = cryptopia.getMarketOrders(getPair(market));

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  public CryptopiaOrderBook getCryptopiaOrderBook(CurrencyPair market, long orderCount) throws IOException {
    CryptopiaBaseResponse<CryptopiaOrderBook> response = cryptopia.getMarketOrders(getPair(market), orderCount);

    if (response.getError() != null) {
      throw new ExchangeException(response.getError());
    }

    return response.getData();
  }

  private String getPair(CurrencyPair pair) {
    return pair.base.getCurrencyCode() + "_" + pair.counter.getCurrencyCode();
  }
}
