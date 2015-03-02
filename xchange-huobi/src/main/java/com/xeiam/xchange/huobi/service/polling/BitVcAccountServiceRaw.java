package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.huobi.dto.account.BitVcAccountInfo;

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
