package org.knowm.xchange.examples.enigma.account;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.enigma.dto.account.EnigmaBalance;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawal;
import org.knowm.xchange.enigma.service.EnigmaAccountServiceRaw;
import org.knowm.xchange.examples.enigma.EnigmaDemoUtils;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
public class EnigmaAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange enigma = EnigmaDemoUtils.createExchange();

    AccountService accountService = enigma.getAccountService();
    generic(accountService);
    raw((EnigmaAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {
    AccountInfo accountInfo = accountService.getAccountInfo();
    log.info("AccountInfo as String: " + accountInfo.toString());

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    log.info(depositAddress);

    String result =
        accountService.withdrawFunds(Currency.BTC, BigDecimal.valueOf(0.002), "address");
    log.info(result);
  }

  private static void raw(EnigmaAccountServiceRaw accountService) throws IOException {

    EnigmaBalance enigmaBalance = accountService.getBalance();
    log.info("Enigma balances: " + enigmaBalance);

    Map<String, BigDecimal> limits = accountService.getRiskLimits();
    for (Map.Entry<String, BigDecimal> limit : limits.entrySet()) {
      log.info("key: {}, value: {}", limit.getKey(), limit.getValue().toString());
    }

    List<EnigmaWithdrawal> withdrawalResponses = accountService.getWithdrawals();
    for (EnigmaWithdrawal withdrawalResponse : withdrawalResponses) {
      log.info(withdrawalResponse.toString());
    }
  }
}
