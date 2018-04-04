package org.knowm.xchange.itbit.v1.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.itbit.v1.ItBitAdapters;
import org.knowm.xchange.itbit.v1.dto.ItBitFundingHistoryResponse;
import org.knowm.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;
import org.knowm.xchange.itbit.v1.dto.account.ItBitDepositRequest;
import org.knowm.xchange.itbit.v1.dto.account.ItBitDepositResponse;
import org.knowm.xchange.itbit.v1.dto.account.ItBitWithdrawalRequest;
import org.knowm.xchange.itbit.v1.dto.account.ItBitWithdrawalResponse;

public class ItBitAccountServiceRaw extends ItBitBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public ItBitAccountInfoReturn[] getItBitAccountInfo() throws IOException {

    ItBitAccountInfoReturn[] info =
        itBitAuthenticated.getInfo(
            signatureCreator, new Date().getTime(), exchange.getNonceFactory(), userId);
    return info;
  }

  public List getAllWallets() {
    return itBitAuthenticated.wallets(
        signatureCreator, exchange.getNonceFactory(), new Date().getTime(), userId, 1, 50);
  }

  public ItBitFundingHistoryResponse getFunding(int page, int perPage) {
    return itBitAuthenticated.fundingHistory(
        signatureCreator,
        exchange.getNonceFactory(),
        new Date().getTime(),
        walletId,
        page,
        perPage);
  }

  public String withdrawItBitFunds(String currency, BigDecimal amount, String address)
      throws IOException {

    String formattedAmount = ItBitAdapters.formatCryptoAmount(amount);

    ItBitWithdrawalRequest request = new ItBitWithdrawalRequest(currency, formattedAmount, address);
    ItBitWithdrawalResponse response =
        itBitAuthenticated.requestWithdrawal(
            signatureCreator, new Date().getTime(), exchange.getNonceFactory(), walletId, request);
    return response.getId();
  }

  public String requestItBitDepositAddress(String currency, String... args) throws IOException {

    Map<String, String> metadata = new HashMap<>();
    for (int i = 0; i < args.length - 1; i += 2) {
      metadata.put(args[i], args[i + 1]);
    }

    ItBitDepositRequest request = new ItBitDepositRequest(currency, metadata);
    ItBitDepositResponse response =
        itBitAuthenticated.requestDeposit(
            signatureCreator, new Date().getTime(), exchange.getNonceFactory(), walletId, request);
    return response.getDepositAddress();
  }

  public ItBitAccountInfoReturn getItBitAccountInfo(String walletId) throws IOException {

    ItBitAccountInfoReturn itBitAccountInfoReturn =
        itBitAuthenticated.getWallet(
            signatureCreator, new Date().getTime(), exchange.getNonceFactory(), walletId);
    return itBitAccountInfoReturn;
  }
}
