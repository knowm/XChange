package org.knowm.xchange.lykke.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lykke.LykkeException;
import org.knowm.xchange.lykke.dto.account.LykkeWallet;

public class LykkeAccountServiceRaw extends LykkeBaseService {

  public LykkeAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<LykkeWallet> getWallets() throws IOException {
    try {
      return lykke.getWallets(apiKey);
    } catch (LykkeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
