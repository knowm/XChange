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

  public OKCoinWithdraw withdraw(String currencySymbol, String withdrawAddress, BigDecimal amount, String target) throws IOException {
    String fee = null;
    if (target.equals("address")) { //External address
      if (currencySymbol.startsWith("btc")) fee = "0.002";
      else if (currencySymbol.startsWith("ltc")) fee = "0.001";
      else if (currencySymbol.startsWith("eth")) fee = "0.01";
      else throw new IllegalArgumentException("Unsupported withdraw currency");
    } else if (target.equals("okex") || target.equals("okcn") || target.equals("okcom")) { //Internal address
      fee = "0";
    } else {
      throw new IllegalArgumentException("Unsupported withdraw target");
    }

    OKCoinWithdraw withdrawResult = okCoin.withdraw(exchange.getExchangeSpecification().getApiKey(), currencySymbol,
        signatureCreator, fee, tradepwd, withdrawAddress, amount.toString(), target);

    return returnOrThrow(withdrawResult);
  }

  public OkCoinAccountRecords getAccountRecords(String symbol, String type, String currentPage, String pageLength) throws IOException {

    OkCoinAccountRecords accountRecords = okCoin.getAccountRecords(apikey, symbol, type, currentPage, pageLength, signatureCreator);

    return returnOrThrow(accountRecords);
  }

}
