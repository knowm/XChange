package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.empoex.EmpoExAuthenticated;
import com.xeiam.xchange.empoex.EmpoExErrorException;
import com.xeiam.xchange.empoex.dto.account.EmpoExBalance;

public class EmpoExAccountServiceRaw extends EmpoExBasePollingService<EmpoExAuthenticated> {

  public EmpoExAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(EmpoExAuthenticated.class, exchangeSpecification);
  }

  public Map<String, List<EmpoExBalance>> getEmpoExBalances() throws IOException {

    try {
      return empoex.getEmpoExBalances(apiKey);
    } catch (EmpoExErrorException e) {
      throw new ExchangeException(e.getError());
    }
  }

}
