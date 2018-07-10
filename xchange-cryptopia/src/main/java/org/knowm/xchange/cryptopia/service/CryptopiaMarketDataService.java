package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptopia.CryptopiaAdapters;
import org.knowm.xchange.cryptopia.CryptopiaErrorAdapter;
import org.knowm.xchange.cryptopia.dto.CryptopiaException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptopiaMarketDataService extends CryptopiaMarketDataServiceRaw
    implements MarketDataService {

  public CryptopiaMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      if (args != null && args.length > 0) {
        long hours = (long) args[0];

        return CryptopiaAdapters.adaptTicker(getCryptopiaTicker(currencyPair, hours), currencyPair);
      }

      return CryptopiaAdapters.adaptTicker(getCryptopiaTicker(currencyPair), currencyPair);
    } catch (CryptopiaException e) {
      throw CryptopiaErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      final List<CurrencyPair> currencyPairs = new ArrayList<>();
      if (params instanceof CurrencyPairsParam) {
        currencyPairs.addAll(((CurrencyPairsParam) params).getCurrencyPairs());
      }
      return getCryptopiaMarkets()
          .stream()
          .map(
              cryptopiaTicker ->
                  CryptopiaAdapters.adaptTicker(
                      cryptopiaTicker,
                      CurrencyPairDeserializer.getCurrencyPairFromString(
                          cryptopiaTicker.getLabel())))
          .filter(
              ticker ->
                  currencyPairs.size() == 0 || currencyPairs.contains(ticker.getCurrencyPair()))
          .collect(Collectors.toList());
    } catch (CryptopiaException e) {
      throw CryptopiaErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      if (args != null && args.length > 0) {
        Long orderCount = null;
        if (args[0] instanceof Integer) {
          orderCount = ((Integer) args[0]).longValue();
        } else if (args[0] instanceof Long) {
          orderCount = (Long) args[0];
        }

        return CryptopiaAdapters.adaptOrderBook(
            getCryptopiaOrderBook(currencyPair, orderCount), currencyPair);
      }

      return CryptopiaAdapters.adaptOrderBook(getCryptopiaOrderBook(currencyPair), currencyPair);
    } catch (CryptopiaException e) {
      throw CryptopiaErrorAdapter.adapt(e);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      if (args != null && args.length > 0) {
        long hours = (long) args[0];

        return CryptopiaAdapters.adaptTrades(getCryptopiaTrades(currencyPair, hours));
      }

      return CryptopiaAdapters.adaptTrades(getCryptopiaTrades(currencyPair));
    } catch (CryptopiaException e) {
      throw CryptopiaErrorAdapter.adapt(e);
    }
  }
}
