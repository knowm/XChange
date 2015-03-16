package com.xeiam.xchange.examples.coinsetter.financialtransaction;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccount;
import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccountList;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransaction;
import com.xeiam.xchange.coinsetter.dto.financialtransaction.CoinsetterFinancialTransactionList;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterAccountServiceRaw;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterClientSessionServiceRaw;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterFinancialTransactionServiceRaw;
import com.xeiam.xchange.examples.coinsetter.CoinsetterExamplesUtils;

public class CoinsetterFinancialTransactionDemo {

  private static final Logger log = LoggerFactory.getLogger(CoinsetterFinancialTransactionDemo.class);

  public static void main(String[] args) throws IOException {

    String username = args[0];
    String password = args[1];
    String ipAddress = args[2];

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange();
    CoinsetterClientSessionServiceRaw clientSessionService = new CoinsetterClientSessionServiceRaw(coinsetter);
    CoinsetterAccountServiceRaw accountService = (CoinsetterAccountServiceRaw) coinsetter.getPollingAccountService();
    CoinsetterFinancialTransactionServiceRaw financialTransactionService = new CoinsetterFinancialTransactionServiceRaw(coinsetter);

    CoinsetterClientSession clientSession = clientSessionService.login(username, password, ipAddress);
    log.info("Client session: {}", clientSession);

    CoinsetterAccountList coinsetterAccounts = accountService.list(clientSession.getUuid());
    for (CoinsetterAccount account : coinsetterAccounts.getAccountList()) {
      log.info("account: {}", account.getAccountUuid());

      CoinsetterAccount a = accountService.get(clientSession.getUuid(), account.getAccountUuid());
      log.info("account: {}", a);

      CoinsetterFinancialTransactionList financialTransactions = financialTransactionService.list(clientSession.getUuid(), account.getAccountUuid());
      for (CoinsetterFinancialTransaction transaction : financialTransactions.getFinancialTransactionList()) {
        CoinsetterFinancialTransaction t = financialTransactionService.get(clientSession.getUuid(), transaction.getUuid());
        log.info("Transaction: {}", t);
      }
    }

    clientSessionService.logout(clientSession.getUuid());
  }

}
