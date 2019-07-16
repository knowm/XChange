package org.knowm.xchange.examples.enigma.account;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.enigma.dto.account.EnigmaBalance;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawal;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawalRequest;
import org.knowm.xchange.enigma.service.EnigmaAccountServiceRaw;
import org.knowm.xchange.examples.enigma.EnigmaDemoUtils;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.knowm.xchange.enigma.model.Infrastructure.DEVELOPMENT;

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

    EnigmaWithdrawalRequest withdrawalRequest =
        new EnigmaWithdrawalRequest(2, new BigDecimal("2"), "USD", DEVELOPMENT.getValue());
    EnigmaWithdrawal withdrawal = accountService.withdrawal(withdrawalRequest);
    log.info(withdrawal.toString());
  }
}
