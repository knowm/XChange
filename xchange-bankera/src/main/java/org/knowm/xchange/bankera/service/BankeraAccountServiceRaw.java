package org.knowm.xchange.bankera.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bankera.BankeraAdapters;
import org.knowm.xchange.bankera.BankeraExchange;
import org.knowm.xchange.bankera.dto.BankeraException;
import org.knowm.xchange.bankera.dto.account.BankeraUserInfo;

public class BankeraAccountServiceRaw extends BankeraBaseService {

  public BankeraAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BankeraUserInfo getUserInfo() throws IOException {
    try {
      BankeraExchange bankeraExchange = (BankeraExchange) exchange;
      String auth = "Bearer " + bankeraExchange.getToken().getAccessToken();
      return bankeraAuthenticated.getUserInfo(auth);
    } catch (BankeraException e) {
      throw BankeraAdapters.adaptError(e);
    }
  }
}
