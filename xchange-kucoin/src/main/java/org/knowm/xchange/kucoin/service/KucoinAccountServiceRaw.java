package org.knowm.xchange.kucoin.service;

import static org.knowm.xchange.kucoin.KucoinUtils.checkSuccess;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kucoin.KucoinException;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.KucoinSimpleResponse;
import org.knowm.xchange.kucoin.dto.account.KucoinCoinBalances;
import org.knowm.xchange.kucoin.dto.account.KucoinDepositAddress;
import org.knowm.xchange.kucoin.dto.account.KucoinWalletOperation;
import org.knowm.xchange.kucoin.dto.account.KucoinWalletRecords;

public class KucoinAccountServiceRaw extends KucoinBaseService {

  protected KucoinAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public KucoinResponse<KucoinCoinBalances> getKucoinBalances(Integer limit, Integer page)
      throws IOException {

    try {
      return checkSuccess(
          kucoin.accountBalances(
              apiKey, exchange.getNonceFactory(), signatureCreator, limit, page));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public KucoinResponse<KucoinDepositAddress> getKucoinDepositAddress(Currency cur)
      throws IOException {

    try {
      return checkSuccess(
          kucoin.walletAddress(
              apiKey, exchange.getNonceFactory(), signatureCreator, cur.getCurrencyCode()));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  KucoinSimpleResponse<Object> withdrawalApply(Currency cur, BigDecimal amount, String address)
      throws IOException {

    try {
      KucoinSimpleResponse<Object> response =
          kucoin.withdrawalApply(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              cur.getCurrencyCode(),
              amount,
              address);
      if (response.isSuccess()) {
        return response;
      } else {
        throw new ExchangeException(response.getCode());
      }
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  KucoinResponse<KucoinWalletRecords> walletRecords(
      Currency currency, FundingRecord.Type type, Integer limit, Integer page) throws IOException {

    try {
      return checkSuccess(
          kucoin.walletRecords(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              currency.getCurrencyCode(),
              type == null ? "" : KucoinWalletOperation.fromFundingRecordType(type).toString(),
              "",
              limit,
              page));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
