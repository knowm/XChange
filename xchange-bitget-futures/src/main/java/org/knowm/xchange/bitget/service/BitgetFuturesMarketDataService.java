package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.knowm.xchange.bitget.BitgetErrorAdapter;
import org.knowm.xchange.bitget.BitgetFuturesAdapters;
import org.knowm.xchange.bitget.BitgetFuturesExchange;
import org.knowm.xchange.bitget.config.Config;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.marketdata.BitgetContractDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetFuturesTickerDto;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BitgetFuturesMarketDataService extends BitgetFuturesMarketDataServiceRaw
    implements MarketDataService {

  public BitgetFuturesMarketDataService(BitgetFuturesExchange exchange) {
    super(exchange);
  }

  public List<Instrument> getInstruments() throws IOException {
    try {
      List<BitgetContractDto> metadata = getBitgetContractDtos(null);

      return metadata.stream()
          .filter(details -> details.getSymbolStatus() == BitgetContractDto.SymbolStatus.NORMAL)
          .map(BitgetContractDto::getFuturesContract)
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
    return getTicker(new FuturesContract(currencyPair, "PERP"), args);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    try {
      List<BitgetFuturesTickerDto> tickers = getBitgetFuturesTickerDtos(instrument);
      return BitgetFuturesAdapters.toTicker(tickers.get(0));

    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      return getBitgetFuturesTickerDtos(null).stream()
          .map(BitgetFuturesAdapters::toTicker)
          .filter(Objects::nonNull)
          .collect(Collectors.toList());

    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }
}
