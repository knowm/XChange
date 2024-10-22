package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.account.BitgetBalanceDto;

public class BitgetAccountServiceRaw extends BitgetBaseService {

  public BitgetAccountServiceRaw(BitgetExchange exchange) {
    super(exchange);
  }

  public List<BitgetBalanceDto> getBitgetBalances() throws IOException {
    return bitgetAuthenticated
        .balances(apiKey, bitgetDigest, passphrase, exchange.getNonceFactory())
        .getData();
  }
}
