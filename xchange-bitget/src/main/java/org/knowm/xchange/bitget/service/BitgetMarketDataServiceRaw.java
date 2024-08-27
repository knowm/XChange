package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.BitgetCoinDto;
import org.knowm.xchange.bitget.dto.BitgetServerTime;

public class BitgetMarketDataServiceRaw extends BitgetBaseService {


  public BitgetMarketDataServiceRaw(BitgetExchange exchange) {
    super(exchange);
  }


  public BitgetServerTime getBitgetServerTime() throws IOException {
    return bitget.serverTime().getData();
  }


  public List<BitgetCoinDto> getBitgetCoinDtoList() throws IOException {
    return bitget.coinDtos().getData();
  }


}
