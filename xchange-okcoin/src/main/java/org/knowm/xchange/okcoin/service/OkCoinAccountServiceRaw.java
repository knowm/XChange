package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.okcoin.dto.account.OKCoinWithdraw;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;
import org.knowm.xchange.okcoin.dto.account.OkCoinFuturesUserInfoCross;
import org.knowm.xchange.okcoin.dto.account.OkCoinUserInfo;

public class OkCoinAccountServiceRaw extends OKCoinBaseTradeService {
  private final String tradepwd;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected OkCoinAccountServiceRaw(Exchange exchange) {

    super(exchange);

    tradepwd = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("tradepwd");
  }

  public OkCoinUserInfo getUserInfo() throws IOException {

    OkCoinUserInfo userInfo = okCoin.getUserInfo(apikey, signatureCreator);

    return returnOrThrow(userInfo);
  }

  public OkCoinFuturesUserInfoCross getFutureUserInfo() throws IOException {

    OkCoinFuturesUserInfoCross futuresUserInfoCross = okCoin.getFuturesUserInfoCross(apikey, signatureCreator);

    return returnOrThrow(futuresUserInfoCross);
  }

  public OKCoinWithdraw withdraw(String assetPairs, String assets, String key, BigDecimal amount) throws IOException {
    OKCoinWithdraw withdrawResult = okCoin.withdraw(exchange.getExchangeSpecification().getApiKey(), assets, signatureCreator, "0.0001", tradepwd,
        key, amount.toString());

    return returnOrThrow(withdrawResult);
  }

  public OkCoinAccountRecords getAccountRecords(String symbol, String type, String currentPage, String pageLength) throws IOException {

    OkCoinAccountRecords accountRecords = okCoin.getAccountRecords(apikey, symbol, type, currentPage, pageLength, signatureCreator);

    return returnOrThrow(accountRecords);
  }

}
