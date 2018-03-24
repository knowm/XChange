package org.knowm.xchange.empoex.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.empoex.EmpoExErrorException;
import org.knowm.xchange.empoex.dto.account.EmpoExBalance;
import org.knowm.xchange.exceptions.ExchangeException;

public class EmpoExAccountServiceRaw extends EmpoExBaseService {

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
      throw new ExchangeException(e.getError(), e);
    }
  }

}
