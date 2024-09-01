package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.trade.BitgetOrderInfoDto;

public class BitgetTradeServiceRaw extends BitgetBaseService {

  public BitgetTradeServiceRaw(BitgetExchange exchange) {
    super(exchange);
  }


  public BitgetOrderInfoDto bitgetOrderInfoDto(String orderId) throws IOException {
    List<BitgetOrderInfoDto> results = bitgetAuthenticated.orderInfo(
        apiKey, bitgetDigest, passphrase, exchange.getNonceFactory(), orderId).getData();
    if (results.size() != 1) {
      return null;
    }
    return results.get(0);
  }

}
