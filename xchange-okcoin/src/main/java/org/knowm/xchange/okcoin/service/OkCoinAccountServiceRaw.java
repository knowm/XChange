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
import com.okcoin.commons.okex.open.api.bean.account.param.Withdraw;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;

public class OkCoinAccountServiceRaw extends OKCoinBaseTradeService {
  private final String tradepwd;
  private final String apiKeyV3;
  private final String secretKeyV3;
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
    
    // For using API v3 methods (withdraw, request deposit address), user
    // must provide API key and secret 
    apiKeyV3 = (String) exchange
    		.getExchangeSpecification()
    		.getExchangeSpecificParametersItem("apikey_v3");
    
	secretKeyV3 = (String) exchange
    		.getExchangeSpecification()
    		.getExchangeSpecificParametersItem("secretkey_v3");
	
    this.accountAPIService = new AccountAPIServiceImpl(this.config());
    
  }

  public APIConfiguration config() {
	  ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
	  
      APIConfiguration config = new APIConfiguration();

      config.setEndpoint("https://" + exchangeSpecification.getHost());
      config.setApiKey(apiKeyV3);
      config.setSecretKey(secretKeyV3);
      config.setPassphrase(tradepwd);
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
	  
    return withdraw(currencySymbol, withdrawAddress, amount, target, null, null);
  }
  
  public OKCoinWithdraw withdraw(
      String currencySymbol, String withdrawAddress, BigDecimal amount, String target, BigDecimal fee, String tag)
      throws IOException {

    if (tradepwd == null) {
      throw new ExchangeException(
          "You need to provide the 'trade/admin password' using exchange.getExchangeSpecification().setExchangeSpecificParametersItem(\"tradepwd\", \"SECRET\");");
    }
    
    if (fee == null) {
    	if (target.equals("address")) { // External address
    	  fee = getWithdrawalFee(currencySymbol);
        
        } else if (target.equals("okex")
            || target.equals("okcn")
            || target.equals("okcom")) { // Internal address
          fee = BigDecimal.ZERO;
        } else {
          throw new IllegalArgumentException("Unsupported withdraw target");
        }
    }
    
    Withdraw withdraw = new Withdraw();
    withdraw.setTo_address(withdrawAddress);
    withdraw.setAmount(amount);
    withdraw.setCurrency(currencySymbol);
    withdraw.setFee(fee);
    withdraw.setTrade_pwd(tradepwd);
    if (tag != null) { 
    	withdraw.setTag(tag);
    }
    // Default withdraw target is external address. Use withdraw function in OkCoinAccountServiceRaw
    // for internal withdraw
    int destination;
    if (target.equals("address")) { // External address
    	destination = 4;
    } else if (target.equals("okex")
            || target.equals("okcn")) {
    	destination = 3;
    } else if (target.equals("okcom")) { // Internal address
    	destination = 2;
    } else {
    	throw new IllegalArgumentException("Unsupported withdraw target");
    }
    withdraw.setDestination(destination);  // 2:OKCoin International 3:OKEx 4:others
    
    // Execute withdrawal
    JSONObject result = this.accountAPIService.withdraw(withdraw);
    OKCoinWithdraw withdrawResult = new OKCoinWithdraw(
    		result.getBoolean("result"), 
    		200,  // Mock HTTP.OK as response
    		result.getString("withdrawal_id"));
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
  
  public BigDecimal getWithdrawalFee(String symbol) {
	  return this.accountAPIService.getWithdrawFee(symbol).get(0).getMin_fee();
  }
}
