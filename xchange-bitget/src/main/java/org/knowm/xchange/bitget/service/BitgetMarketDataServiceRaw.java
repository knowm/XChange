package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.BitgetCoinDto;
import org.knowm.xchange.bitget.dto.BitgetServerTime;
import org.knowm.xchange.bitget.dto.BitgetSymbolDto;
import org.knowm.xchange.instrument.Instrument;

public class BitgetMarketDataServiceRaw extends BitgetBaseService {


  public BitgetMarketDataServiceRaw(BitgetExchange exchange) {
    super(exchange);
  }


  public BitgetServerTime getBitgetServerTime() throws IOException {
    return bitget.serverTime().getData();
  }


  public List<BitgetCoinDto> getBitgetCoinDtoList() throws IOException {
    return bitget.coins().getData();
  }


  public List<BitgetSymbolDto> getBitgetSymbolDtos(Instrument instrument) throws IOException {
    return bitget.symbols(BitgetAdapters.toString(instrument)).getData();
  }


}
