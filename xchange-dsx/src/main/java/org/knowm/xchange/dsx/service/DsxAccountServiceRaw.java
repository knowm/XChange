package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dsx.dto.DsxAddress;
import org.knowm.xchange.dsx.dto.DsxBalance;
import org.knowm.xchange.dsx.dto.DsxInternalTransferResponse;
import org.knowm.xchange.dsx.dto.DsxSort;
import org.knowm.xchange.dsx.dto.DsxTransaction;
import org.knowm.xchange.dsx.dto.DsxTransferType;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.HttpStatusIOException;

public class DsxAccountServiceRaw extends DsxBaseService {

  public DsxAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public String withdrawFundsRaw(
      Currency currency, BigDecimal amount, String address, String paymentId)
      throws HttpStatusIOException {

    return withdrawFundsRaw(currency, amount, address, paymentId, false);
  }

  public String withdrawFundsRaw(
      Currency currency, BigDecimal amount, String address, String paymentId, Boolean includeFee)
      throws HttpStatusIOException {
    Map response = dsx.payout(amount, currency.getCurrencyCode(), address, paymentId, includeFee);

    return response.get("id").toString();
  }

  public DsxInternalTransferResponse transferFunds(
      Currency currency, BigDecimal amount, DsxTransferType dsxTransferType) throws IOException {
    return dsx.transferToTrading(amount, currency.getCurrencyCode(), dsxTransferType.getType());
  }

  public String transferToTrading(Currency currency, BigDecimal amount) throws IOException {

    DsxInternalTransferResponse response =
        transferFunds(currency, amount, DsxTransferType.BANK_TO_EXCHANGE);

    if (response.id == null) {
      throw new ExchangeException("transfer failed: " + response);
    }
    return response.id;
  }

  public String transferToMain(Currency currency, BigDecimal amount) throws IOException {
    DsxInternalTransferResponse response =
        transferFunds(currency, amount, DsxTransferType.EXCHANGE_TO_BANK);

    if (response.id == null) {
      throw new ExchangeException("transfer failed: " + response);
    }
    return response.id;
  }

  public List<DsxBalance> getMainBalance() throws IOException {
    return dsx.getMainBalance();
  }

  public List<DsxBalance> getTradingBalance() throws IOException {
    return dsx.getTradingBalance();
  }

  public DsxAddress getDepositAddress(Currency currency) throws IOException {
    return dsx.getDsxDepositAddress(currency.toString());
  }

  public List<DsxTransaction> getTransactions(String currency, Integer limit, Integer offset)
      throws HttpStatusIOException {
    return dsx.transactions(currency, null, null, null, null, limit, offset);
  }

  public List<DsxTransaction> getTransactions(
      String currency, DsxSort sort, Date from, Date till, Integer limit, Integer offset)
      throws HttpStatusIOException {

    String sortValue = sort != null ? sort.toString().toUpperCase() : null;
    String fromValue = from != null ? Instant.ofEpochMilli(from.getTime()).toString() : null;
    String tillValue = till != null ? Instant.ofEpochMilli(till.getTime()).toString() : null;
    return dsx.transactions(currency, sortValue, "timestamp", fromValue, tillValue, limit, offset);
  }

  public List<DsxTransaction> getTransactions(
      String currency, DsxSort sort, Long fromIndex, Long tillIndex, Integer limit, Integer offset)
      throws HttpStatusIOException {

    String sortValue = sort != null ? sort.toString().toUpperCase() : null;
    String fromValue = fromIndex != null ? fromIndex.toString() : null;
    String tillValue = fromIndex != null ? tillIndex.toString() : null;
    return dsx.transactions(currency, sortValue, "index", fromValue, tillValue, limit, offset);
  }
}
