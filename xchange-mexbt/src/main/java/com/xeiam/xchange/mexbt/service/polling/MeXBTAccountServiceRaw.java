package com.xeiam.xchange.mexbt.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.mexbt.dto.MeXBTException;
import com.xeiam.xchange.mexbt.dto.MeXBTRequest;
import com.xeiam.xchange.mexbt.dto.MeXBTResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTBalanceResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTDepositAddressesResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTUserInfoResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTWithdrawRequest;

public class MeXBTAccountServiceRaw extends MeXBTAuthenticatedPollingService {

  protected MeXBTAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public MeXBTUserInfoResponse getMe() throws MeXBTException, IOException {
    return meXBTAuthenticated.getMe(new MeXBTRequest(apiKey, nonceFactory, meXBTDigest));
  }

  public MeXBTBalanceResponse getBalance() throws MeXBTException, IOException {
    return meXBTAuthenticated.getBalance(new MeXBTRequest(apiKey, nonceFactory, meXBTDigest));
  }

  public MeXBTDepositAddressesResponse getDepositAddresses() throws MeXBTException, IOException {
    return meXBTAuthenticated.getDepositAddresses(new MeXBTRequest(apiKey, nonceFactory, meXBTDigest));
  }

  public MeXBTResponse withdraw(String ins, BigDecimal amount, String sendToAddress) throws MeXBTException, IOException {
    return meXBTAuthenticated.withdraw(new MeXBTWithdrawRequest(apiKey, nonceFactory, meXBTDigest, ins, amount, sendToAddress));
  }

}
