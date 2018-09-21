package org.knowm.xchange.getbtc.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.getbtc.GetbtcAdapters;
import org.knowm.xchange.service.account.AccountService;
/**
 * kevinobamatheus@gmail.com
 * @author kevingates
 *
 */
public class GetbtcAccountService extends GetbtcAccountServiceRaw implements AccountService {
  public GetbtcAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
	  
    return GetbtcAdapters.convertBalance(super.getGetbtcAccountInfo());
  }
}
