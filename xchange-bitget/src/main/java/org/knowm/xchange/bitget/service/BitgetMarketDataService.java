package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetErrorAdapter;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.config.Config;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.marketdata.BitgetCoinDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetSymbolDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetSymbolDto.Status;
import org.knowm.xchange.bitget.dto.marketdata.BitgetTickerDto;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BitgetMarketDataService extends BitgetMarketDataServiceRaw
    implements MarketDataService {

  public BitgetMarketDataService(BitgetExchange exchange) {
    super(exchange);
  }

  public List<Currency> getCurrencies() throws IOException {
    try {
      return getBitgetCoinDtoList(null).stream()
          .map(BitgetCoinDto::getCurrency)
          .distinct()
          .collect(Collectors.toList());
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  public List<Instrument> getInstruments() throws IOException {
    try {
      List<BitgetSymbolDto> metadata = getBitgetSymbolDtos(null);

      return metadata.stream()
          .filter(details -> details.getStatus() == Status.ONLINE)
          .map(BitgetSymbolDto::getCurrencyPair)
          .distinct()
          .collect(Collectors.toList());
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public ExchangeHealth getExchangeHealth() {
    try {
      Instant serverTime = getBitgetServerTime().getServerTime();
      Instant localTime = Instant.now(Config.getInstance().getClock());

      // timestamps shouldn't diverge by more than 10 minutes
      if (Duration.between(serverTime, localTime).toMinutes() < 10) {
        return ExchangeHealth.ONLINE;
      }
    } catch (BitgetException | IOException e) {
      return ExchangeHealth.OFFLINE;
    }

    return ExchangeHealth.OFFLINE;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    try {
      List<BitgetTickerDto> tickers = getBitgetTickerDtos(instrument);
      return BitgetAdapters.toTicker(tickers.get(0));

    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      return getBitgetTickerDtos(null).stream()
          .map(BitgetAdapters::toTicker)
          .filter(Objects::nonNull)
          .collect(Collectors.toList());

    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    Objects.requireNonNull(instrument);

    try {
      return BitgetAdapters.toOrderBook(getBitgetMarketDepthDtos(instrument), instrument);
    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }
}
