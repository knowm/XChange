package org.knowm.xchange.mexc.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.account.MEXCBalance;

public class MEXCAccountServiceRaw extends MEXCBaseService {
  public MEXCAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public MEXCResult<Map<String, MEXCBalance>> getWalletBalances() throws IOException {
    return mexcAuthenticated.getWalletBalances(apiKey, nonceFactory, signatureCreator);
  }
}
