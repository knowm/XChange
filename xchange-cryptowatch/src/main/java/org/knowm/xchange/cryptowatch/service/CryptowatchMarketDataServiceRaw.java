package org.knowm.xchange.cryptowatch.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptowatch.dto.CryptowatchException;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAsset;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAssetPair;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOHLCs;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOrderBook;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchSummary;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchTrade;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchOHLCResult;
import org.knowm.xchange.cryptowatch.dto.marketdata.results.CryptowatchOrderBookResult;
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

  public CryptowatchSummary getCryptowatchSummary(CurrencyPair pair, String market) {
    CryptowatchSummaryResult result = cryptowatch.getTicker(market, pair.toString().toLowerCase());
    return checkResult(result);
  }

  public CryptowatchOHLCs getCryptowatchOHLCs(
      CurrencyPair pair, String market, long before, long after, int period) {
    CryptowatchOHLCResult result =
        cryptowatch.getOHLC(market, pair.toString().toLowerCase(), before, after, period);
    return checkResult(result);
  }

  public CryptowatchOrderBook getCryptowatchOrderBook(CurrencyPair pair, String market) {
    CryptowatchOrderBookResult result =
        cryptowatch.getOrderBook(market, pair.toString().toLowerCase());
    return checkResult(result);
  }

  public List<CryptowatchTrade> getTrades(
      CurrencyPair pair, String market, Integer since, Integer limit) throws IOException {
    CryptowatchTradesResult result = cryptowatch.getTrades(market, pair.toString(), since, limit);
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
