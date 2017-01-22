package org.knowm.xchange.huobi.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.dto.account.BitVcAccountInfo;

public class BitVcAccountServiceRaw extends BitVcBaseTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitVcAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitVcAccountInfo getBitVcAccountInfo() throws IOException {

    BitVcAccountInfo rawAccountInfo = bitvc.getAccountInfo(accessKey, nextCreated(), digest);

    if (rawAccountInfo.getMessage() != null) {
      throw new ExchangeException(rawAccountInfo.getMessage());
    } else {
      return rawAccountInfo;
    }
  }
}
