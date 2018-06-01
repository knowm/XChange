package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptopia.CryptopiaErrorAdapter;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTicker;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public class CryptopiaMarketDataServiceRaw extends CryptopiaBaseService {

  private static final Pattern MARKET_NOT_FOUND_ERROR_PATTERN =
      Pattern.compile("^Market .*not found$");

  public CryptopiaMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<CryptopiaCurrency> getCryptopiaCurrencies() throws IOException {
    CryptopiaBaseResponse<List<CryptopiaCurrency>> response = cryptopia.getCurrencies();
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public List<CryptopiaTradePair> getCryptopiaTradePairs() throws IOException {
    CryptopiaBaseResponse<List<CryptopiaTradePair>> response = cryptopia.getTradePairs();
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public List<CryptopiaTicker> getCryptopiaMarkets() throws IOException {
    CryptopiaBaseResponse<List<CryptopiaTicker>> response = cryptopia.getMarkets();
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public List<CryptopiaTicker> getCryptopiaMarkets(Currency baseMarket) throws IOException {
    CryptopiaBaseResponse<List<CryptopiaTicker>> response =
        cryptopia.getMarkets(baseMarket.getCurrencyCode());
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public List<CryptopiaTicker> getCryptopiaMarkets(Currency baseMarket, long hours)
      throws IOException {
    CryptopiaBaseResponse<List<CryptopiaTicker>> response =
        cryptopia.getMarkets(baseMarket.getCurrencyCode(), hours);
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public CryptopiaTicker getCryptopiaTicker(CurrencyPair market) throws IOException {
    CryptopiaBaseResponse<CryptopiaTicker> response = cryptopia.getMarket(getPair(market));
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public CryptopiaTicker getCryptopiaTicker(CurrencyPair market, long hours) throws IOException {
    CryptopiaBaseResponse<CryptopiaTicker> response = cryptopia.getMarket(getPair(market), hours);
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public List<CryptopiaMarketHistory> getCryptopiaTrades(CurrencyPair market) throws IOException {
    CryptopiaBaseResponse<List<CryptopiaMarketHistory>> response =
        cryptopia.getMarketHistory(getPair(market));
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public List<CryptopiaMarketHistory> getCryptopiaTrades(CurrencyPair market, long hours)
      throws IOException {
    CryptopiaBaseResponse<List<CryptopiaMarketHistory>> response =
        cryptopia.getMarketHistory(getPair(market), hours);
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public CryptopiaOrderBook getCryptopiaOrderBook(CurrencyPair market) throws IOException {
    CryptopiaBaseResponse<CryptopiaOrderBook> response = cryptopia.getMarketOrders(getPair(market));
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  public CryptopiaOrderBook getCryptopiaOrderBook(CurrencyPair market, long orderCount)
      throws IOException {
    CryptopiaBaseResponse<CryptopiaOrderBook> response =
        cryptopia.getMarketOrders(getPair(market), orderCount);
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
    return response.getData();
  }

  private String getPair(CurrencyPair pair) {
    return pair.base.getCurrencyCode() + "_" + pair.counter.getCurrencyCode();
  }
}
