package org.knowm.xchange.cryptowatch.service;

import static org.knowm.xchange.cryptowatch.CryptowatchAdapters.adaptCurrencyPair;

import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptowatch.dto.CryptowatchException;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAsset;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAssetPair;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOHLCs;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOrderBook;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchPrice;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchSummary;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchTrade;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchOHLCResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchOrderBookResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchPriceResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchSummaryResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchTradesResult;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeUnavailableException;

public class CryptowatchMarketDataServiceRaw extends CryptowatchBaseService {

  public CryptowatchMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<CryptowatchAssetPair> getCryptowatchAssetPairs() {
    return checkResult(cryptowatch.getAssetPairs());
  }

  public List<CryptowatchAsset> getCryptowatchAssets() {
    return checkResult(cryptowatch.getAssets());
  }

  public CryptowatchPrice getCryptowatchPrice(CurrencyPair pair, String market) {
    CryptowatchPriceResult result = cryptowatch.getPrice(market, adaptCurrencyPair(pair));
    return checkResult(result);
  }

  public CryptowatchSummary getCryptowatchSummary(CurrencyPair pair, String market) {
    CryptowatchSummaryResult result =
        cryptowatch.getTicker(market, adaptCurrencyPair(pair).toLowerCase());
    return checkResult(result);
  }

  public CryptowatchOHLCs getCryptowatchOHLCs(
      CurrencyPair pair, String market, Long before, Long after, Integer period) {
    CryptowatchOHLCResult result =
        cryptowatch.getOHLC(market, adaptCurrencyPair(pair).toLowerCase(), before, after, period);
    return checkResult(result);
  }

  public CryptowatchOrderBook getCryptowatchOrderBook(CurrencyPair pair, String market) {
    CryptowatchOrderBookResult result =
        cryptowatch.getOrderBook(market, adaptCurrencyPair(pair).toLowerCase());
    return checkResult(result);
  }

  public List<CryptowatchTrade> getCryptowatchTrades(
      CurrencyPair pair, String market, Integer limit, Long since) {
    CryptowatchTradesResult result =
        cryptowatch.getTrades(market, adaptCurrencyPair(pair), limit, since);
    return checkResult(result);
  }

  private <R> R checkResult(CryptowatchResult<R> result) {
    if (!result.isSuccess()) {
      String error = result.getError();
      switch (error) {
        case "Exchange not found":
          throw new ExchangeUnavailableException(error);
        case "Instrument not found":
        case "Pair not found":
          throw new CurrencyPairNotValidException(error);
        default:
          throw new CryptowatchException(error);
      }
    }
    return result.getResult();
  }
}
