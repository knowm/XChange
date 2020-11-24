package org.knowm.xchange.upbit.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.upbit.dto.UpbitException;
import org.knowm.xchange.upbit.dto.account.UpbitBalances;

public class UpbitAccountServiceRaw extends UpbitBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public UpbitAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public UpbitBalances getWallet() throws UpbitException, IOException {
    return upbit.getWallet(this.signatureCreator);
  }
}
