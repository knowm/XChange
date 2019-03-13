package org.knowm.xchange.lykke.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lykke.LykkeAdapter;
import org.knowm.xchange.lykke.LykkeException;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAsset;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAssetPair;
import org.knowm.xchange.lykke.dto.marketdata.LykkeOrderBook;

public class LykkeMarketDataServiceRaw extends LykkeBaseService {

  public LykkeMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<LykkeAssetPair> getAssetPairs() throws IOException {
    try {
      return lykke.getAssetPairs();
    } catch (LykkeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public LykkeAssetPair getAssetPairById(CurrencyPair currencyPair) throws IOException {
    return lykke.getAssetPairById(LykkeAdapter.adaptToAssetPair(currencyPair));
  }

  public List<LykkeOrderBook> getAllOrderBooks() throws IOException {
    return lykke.getAllOrderBooks();
  }

  public List<LykkeOrderBook> getLykkeOrderBook(CurrencyPair currencyPair) throws IOException {
    return lykke.getOrderBookByAssetPair(LykkeAdapter.adaptToAssetPair(currencyPair));
  }

  public List<LykkeAsset> getLykkeAssets() throws IOException {
    return lykkePublic.getLykkeAsset();
  }
}
