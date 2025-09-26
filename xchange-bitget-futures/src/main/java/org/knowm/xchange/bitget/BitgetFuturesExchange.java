package org.knowm.xchange.bitget;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitget.dto.marketdata.BitgetContractDto;
import org.knowm.xchange.bitget.service.BitgetFuturesMarketDataService;
import org.knowm.xchange.bitget.service.BitgetFuturesMarketDataServiceRaw;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

public class BitgetFuturesExchange extends BaseExchange {

  @Override
  protected void initServices() {
    marketDataService = new BitgetFuturesMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification = new ExchangeSpecification(getClass());
    specification.setSslUri("https://api.bitget.com");
    specification.setHost("www.bitget.com");
    specification.setExchangeName("Bitget Futures");
    return specification;
  }

  @Override
  public void remoteInit() throws IOException {
    BitgetFuturesMarketDataServiceRaw bitgetFuturesMarketDataServiceRaw =
        (BitgetFuturesMarketDataServiceRaw) marketDataService;

    // initialize symbol mappings
    List<BitgetContractDto> bitgetContractDtos =
        bitgetFuturesMarketDataServiceRaw.getBitgetContractDtos(null);
    bitgetContractDtos.forEach(
        bitgetContractDto -> {
          BitgetAdapters.putSymbolMapping(
              bitgetContractDto.getSymbol(),
              bitgetContractDto.getFuturesContract().getCurrencyPair());
        });

    // initialize instrument metadata
    Map<Instrument, InstrumentMetaData> instruments =
        bitgetContractDtos.stream()
            .collect(
                Collectors.toMap(
                    BitgetContractDto::getFuturesContract,
                    BitgetFuturesAdapters::toInstrumentMetaData));

    exchangeMetaData = new ExchangeMetaData(instruments, null, null, null, null);
  }
}
