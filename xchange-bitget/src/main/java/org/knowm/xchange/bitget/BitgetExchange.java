package org.knowm.xchange.bitget;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitget.dto.marketdata.BitgetSymbolDto;
import org.knowm.xchange.bitget.service.BitgetAccountService;
import org.knowm.xchange.bitget.service.BitgetMarketDataService;
import org.knowm.xchange.bitget.service.BitgetMarketDataServiceRaw;
import org.knowm.xchange.bitget.service.BitgetTradeService;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

public class BitgetExchange extends BaseExchange {

  @Override
  protected void initServices() {
    accountService = new BitgetAccountService(this);
    marketDataService = new BitgetMarketDataService(this);
    tradeService = new BitgetTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification = new ExchangeSpecification(getClass());
    specification.setSslUri("https://api.bitget.com");
    specification.setHost("www.bitget.com");
    specification.setExchangeName("Bitget");
    return specification;
  }

  @Override
  public void remoteInit() throws IOException {
    BitgetMarketDataServiceRaw bitgetMarketDataServiceRaw =
        (BitgetMarketDataServiceRaw) marketDataService;

    // initialize symbol mappings
    List<BitgetSymbolDto> bitgetSymbolDtos = bitgetMarketDataServiceRaw.getBitgetSymbolDtos(null);
    bitgetSymbolDtos.forEach(
        bitgetSymbolDto -> {
          BitgetAdapters.putSymbolMapping(
              bitgetSymbolDto.getSymbol(), bitgetSymbolDto.getCurrencyPair());
        });

    // initialize instrument metadata
    Map<Instrument, InstrumentMetaData> instruments =
        bitgetSymbolDtos.stream()
            .collect(
                Collectors.toMap(
                    BitgetSymbolDto::getCurrencyPair, BitgetAdapters::toInstrumentMetaData));

    exchangeMetaData = new ExchangeMetaData(instruments, null, null, null, null);
  }
}
