package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.empoex.EmpoExErrorException;
import com.xeiam.xchange.empoex.dto.account.EmpoExBalance;
import com.xeiam.xchange.exceptions.ExchangeException;

public class EmpoExAccountServiceRaw extends EmpoExBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<String, List<EmpoExBalance>> getEmpoExBalances() throws IOException {

    try {
      return empoExAuthenticated.getEmpoExBalances(apiKey);
    } catch (EmpoExErrorException e) {
      throw new ExchangeException(e.getError());
    }
  }

}
