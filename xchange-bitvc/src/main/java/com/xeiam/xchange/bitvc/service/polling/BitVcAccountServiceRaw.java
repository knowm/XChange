package com.xeiam.xchange.bitvc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitvc.dto.account.BitVcAccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;

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
