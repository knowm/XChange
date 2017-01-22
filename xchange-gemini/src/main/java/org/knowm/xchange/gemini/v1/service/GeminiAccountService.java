package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.gemini.v1.GeminiAdapters;
import org.knowm.xchange.gemini.v1.GeminiUtils;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressResponse;
import org.knowm.xchange.service.account.AccountService;

public class GeminiAccountService extends GeminiAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(GeminiAdapters.adaptWallet(getGeminiAccountInfo()));
  }

  /**
   * Withdrawal suppport
   *
   * @param currency
   * @param amount
   * @param address
   * @return
   * @throws IOException
   */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    //determine withdrawal type
    String type = GeminiUtils.convertToGeminiWithdrawalType(currency.toString());
    //Gemini withdeawal can be from different type of wallets    *
    // we have to use one of these for now: Exchange -
    //to be able to withdraw instantly after trading for example
    //The wallet to withdraw from, can be “trading”, “exchange”, or “deposit”.
    String walletSelected = "exchange";
    //We have to convert XChange currencies to Gemini currencies: can be “bitcoin”, “litecoin” or “ether” or “tether” or “wire”.
    return withdraw(type, walletSelected, amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    final GeminiDepositAddressResponse response = super.requestDepositAddressRaw(currency.getCurrencyCode());
    return response.getAddress();
  }
}
