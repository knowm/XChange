package com.xeiam.xchange.bitvc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.dto.account.BitVcAccountInfo;

public class BitVcAccountServiceRaw extends BitVcBaseTradeService {

  protected BitVcAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public BitVcAccountInfo getBitVcAccountInfo() throws IOException {

    BitVcAccountInfo rawAccountInfo = bitvc.getAccountInfo(accessKey, nextCreated(), digest);

    if (rawAccountInfo.getMessage() != null) {
      throw new ExchangeException(rawAccountInfo.getMessage());
    }
    else {
      return rawAccountInfo;
    }
  }
}
