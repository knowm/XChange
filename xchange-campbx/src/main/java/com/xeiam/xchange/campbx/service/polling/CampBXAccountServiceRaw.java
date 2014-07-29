package com.xeiam.xchange.campbx.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.campbx.dto.CampBXResponse;
import com.xeiam.xchange.campbx.dto.account.MyFunds;

/**
 * @author Matija Mazi
 */
public class CampBXAccountServiceRaw extends CampBXBasePollingService {

  private final CampBX campBX;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CampBXAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.campBX = RestProxyFactory.createProxy(CampBX.class, exchangeSpecification.getSslUri());
  }

  public MyFunds getCampBXAccountInfo() throws IOException {

    MyFunds myFunds = campBX.getMyFunds(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    return myFunds;
  }

  public CampBXResponse withdrawCampBXFunds(BigDecimal amount, String address) throws IOException {

    CampBXResponse campBXResponse = campBX.withdrawBtc(exchangeSpecification.getUserName(), exchangeSpecification.getPassword(), address, amount);
    return campBXResponse;
  }

  public CampBXResponse requestCampBXBitcoinDepositAddress() throws IOException {

    CampBXResponse campBXResponse = campBX.getDepositAddress(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
    return campBXResponse;
  }

}
