package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.enigma.EnigmaAdapters;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaOrderBook;
import org.knowm.xchange.enigma.model.EnigmaException;
import org.knowm.xchange.enigma.model.EnigmaNotImplementedException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;

@Slf4j
public class EnigmaMarketDataService extends EnigmaMarketDataServiceRaw
    implements MarketDataService {

  public EnigmaMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return EnigmaAdapters.adaptTicker(getEnigmaTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws IOException, EnigmaNotImplementedException {
    EnigmaOrderBook enigmaOrderBook = getEnigmaOrderBook(currencyPair.toString().replace("/", "-"));
    return EnigmaAdapters.adaptOrderBook(enigmaOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return EnigmaAdapters.adaptTrades(getEnigmaTransactions(), currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) {
    if (!(params instanceof CurrencyPairsParam)) {
      throw new IllegalArgumentException("Params must be instance of CurrencyPairsParam");
    }

    Collection<CurrencyPair> pairs = ((CurrencyPairsParam) params).getCurrencyPairs();
    return pairs.stream()
        .map(
            currencyPair -> {
              try {
                return getTicker(currencyPair, currencyPair);
              } catch (IOException e) {
                throw new EnigmaException("Error while fetching ticker");
              }
            })
        .collect(Collectors.toList());
  }
}
