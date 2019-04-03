package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OKCoinWithdraw;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;
import org.knowm.xchange.okcoin.dto.account.OkCoinFuturesUserInfoCross;
import org.knowm.xchange.okcoin.dto.account.OkCoinFuturesUserInfoFixed;
import org.knowm.xchange.okcoin.dto.account.OkCoinUserInfo;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.param.Transfer;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;

public class OkCoinAccountServiceRaw extends OKCoinBaseTradeService {
  private final String tradepwd;
  public AccountAPIService accountAPIService;
  /**
   * Constructor
   *
   * @param exchange
   */
  protected OkCoinAccountServiceRaw(Exchange exchange) {

    super(exchange);

    tradepwd = (String) exchange
    		.getExchangeSpecification()
    		.getExchangeSpecificParametersItem("tradepwd");
    
    this.accountAPIService = new AccountAPIServiceImpl(this.config());
    
  }

  public APIConfiguration config() {
	  ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
	  
      APIConfiguration config = new APIConfiguration();

      // config.setEndpoint(exchangeSpecification.getSslUri());
//      config.setApiKey(exchangeSpecification.getApiKey());
//      config.setSecretKey(exchangeSpecification.getSecretKey());
//      config.setPassphrase(tradepwd);
      config.setEndpoint("https://www.okex.com");
      config.setApiKey("516825d8-5c99-491b-a504-a84095499e4e");
      config.setSecretKey("39EABBADE7DF7C486BFF1674FDC4F178");
      config.setPassphrase("okex19851985");
      
      config.setPrint(true);
      config.setConnectTimeout(exchangeSpecification.getHttpConnTimeout());
      config.setReadTimeout(exchangeSpecification.getHttpReadTimeout());
     
      return config;
  }
  
  /**
   * 获取用户信息
   *
   * @return
   * @throws IOException
   */
  public OkCoinUserInfo getUserInfo() throws IOException {

    OkCoinUserInfo userInfo = okCoin.getUserInfo(apikey, signatureCreator());

    return returnOrThrow(userInfo);
  }

  /**
   * 获取OKEx合约账户信息(全仓)
   *
   * @return
   * @throws IOException
   */
  public OkCoinFuturesUserInfoCross getFutureUserInfo() throws IOException {

    OkCoinFuturesUserInfoCross futuresUserInfoCross =
        okCoin.getFuturesUserInfoCross(apikey, signatureCreator());

    return returnOrThrow(futuresUserInfoCross);
  }

  /**
   * 获取逐仓合约账户信息（逐仓）
   *
   * @return
   * @throws IOException
   */
  public OkCoinFuturesUserInfoFixed getFuturesUserInfoFixed() throws IOException {

    OkCoinFuturesUserInfoFixed okCoinFuturesUserInfoFixed =
        okCoin.getFuturesUserInfoFixed(apikey, signatureCreator());

    return returnOrThrow(okCoinFuturesUserInfoFixed);
  }

  public OKCoinWithdraw withdraw(
      String currencySymbol, String withdrawAddress, BigDecimal amount, String target)
      throws IOException {
    String fee = null;
    if (target.equals("address")) { // External address
      if (currencySymbol.startsWith("btc")) fee = "0.002";
      else if (currencySymbol.startsWith("ltc")) fee = "0.001";
      else if (currencySymbol.startsWith("eth")) fee = "0.01";
      else throw new IllegalArgumentException("Unsupported withdraw currency");
    } else if (target.equals("okex")
        || target.equals("okcn")
        || target.equals("okcom")) { // Internal address
      fee = "0";
    } else {
      throw new IllegalArgumentException("Unsupported withdraw target");
    }

    return withdraw(currencySymbol, withdrawAddress, amount, target, fee);
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
	
	Transfer transfer = new Transfer();
    transfer.setFrom(from.getValue());
    transfer.setTo(to.getValue());
    transfer.setCurrency(symbol);
    transfer.setAmount(amount);
    JSONObject result = this.accountAPIService.transfer(transfer);
	return result.getBoolean("result");
  }
  
  public org.knowm.xchange.dto.account.Wallet getWallet(Currency currency) {
	  Wallet okexWallet = this.accountAPIService.getWallet(OkCoinAdapters.adaptSymbol(currency)).get(0);
	  Balance balance = new Balance(
			  currency, 
			  okexWallet.getBalance(),
			  okexWallet.getAvailable(),
			  okexWallet.getFrozen() == null ? BigDecimal.ZERO : okexWallet.getFrozen());
	  return new org.knowm.xchange.dto.account.Wallet(balance);
  }
}
