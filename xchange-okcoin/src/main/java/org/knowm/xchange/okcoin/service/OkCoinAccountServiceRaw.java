package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
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

    tradepwd =
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("tradepwd");
  }

  public OkCoinUserInfo getUserInfo() throws IOException {

    OkCoinUserInfo userInfo = okCoin.getUserInfo(apikey, signatureCreator());

    return returnOrThrow(userInfo);
  }

  public OkCoinFuturesUserInfoCross getFutureUserInfo() throws IOException {

    OkCoinFuturesUserInfoCross futuresUserInfoCross =
        okCoin.getFuturesUserInfoCross(apikey, signatureCreator());

    return returnOrThrow(futuresUserInfoCross);
  }

  public OKCoinWithdraw withdraw(
      String currencySymbol, String withdrawAddress, BigDecimal amount, String target, String fee)
      throws IOException {

    if (tradepwd == null) {
      throw new ExchangeException(
          "You need to provide the 'trade/admin password' using exchange.getExchangeSpecification().setExchangeSpecificParametersItem(\"tradepwd\", \"SECRET\");");
    }
    OKCoinWithdraw withdrawResult =
        okCoin.withdraw(
            exchange.getExchangeSpecification().getApiKey(),
            currencySymbol,
            signatureCreator(),
            fee,
            tradepwd,
            withdrawAddress,
            amount.toString(),
            target);

    return returnOrThrow(withdrawResult);
  }

  public OkCoinAccountRecords getAccountRecords(
      String symbol, String type, String currentPage, String pageLength) throws IOException {

    OkCoinAccountRecords accountRecords =
        okCoin.getAccountRecords(apikey, symbol, type, currentPage, pageLength, signatureCreator());

    return returnOrThrow(accountRecords);
  }

  public enum AccountType {
    SPOT(1),
    FUTURES(3),
    MY_WALLET(6);
    private final int value;

    private AccountType(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  public boolean moveFunds(String symbol, BigDecimal amount, AccountType from, AccountType to)
      throws IOException {
    return okCoin
        .fundsTransfer(
            apikey,
            symbol,
            amount.toPlainString(),
            from.getValue(),
            to.getValue(),
            signatureCreator())
        .isResult();
  }
}
