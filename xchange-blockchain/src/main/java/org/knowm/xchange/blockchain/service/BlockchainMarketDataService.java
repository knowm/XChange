package org.knowm.xchange.blockchain.service;

import java.io.IOException;
import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainErrorAdapter;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BlockchainMarketDataService extends BlockchainMarketDataServiceRaw
    implements MarketDataService {

  public BlockchainMarketDataService(
      BlockchainExchange exchange,
      BlockchainAuthenticated blockchainApi,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, blockchainApi, resilienceRegistries);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      return BlockchainAdapters.toOrderBook(this.getOrderBookL3(currencyPair));
    } catch (BlockchainException e) {
      throw BlockchainErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    try {
      return BlockchainAdapters.toOrderBook(
          this.getOrderBookL3(BlockchainAdapters.toCurrencyPair(instrument)));
    } catch (BlockchainException e) {
      throw BlockchainErrorAdapter.adapt(e);
    }
  }
}
