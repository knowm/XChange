package org.knowm.xchange.campbx.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.campbx.dto.CampBXResponse;
import org.knowm.xchange.campbx.dto.account.MyFunds;

/** @author Matija Mazi */
public class CampBXAccountServiceRaw extends CampBXBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CampBXAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public MyFunds getCampBXAccountInfo() throws IOException {

    MyFunds myFunds =
        campBX.getMyFunds(
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getPassword());
    return myFunds;
  }

  public CampBXResponse withdrawCampBXFunds(BigDecimal amount, String address) throws IOException {

    CampBXResponse campBXResponse =
        campBX.withdrawBtc(
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getPassword(),
            address,
            amount);
    return campBXResponse;
  }

  public CampBXResponse requestCampBXBitcoinDepositAddress() throws IOException {

    CampBXResponse campBXResponse =
        campBX.getDepositAddress(
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getPassword());
    return campBXResponse;
  }
}
