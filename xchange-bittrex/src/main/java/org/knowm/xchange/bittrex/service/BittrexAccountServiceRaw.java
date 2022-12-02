package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexAuthenticated;
import org.knowm.xchange.bittrex.BittrexConstants;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.dto.account.BittrexAccountVolume;
import org.knowm.xchange.bittrex.dto.account.BittrexAddress;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.dto.account.BittrexBalances;
import org.knowm.xchange.bittrex.dto.account.BittrexComissionRatesWithMarket;
import org.knowm.xchange.bittrex.dto.account.BittrexDepositHistory;
import org.knowm.xchange.bittrex.dto.account.BittrexNewAddress;
import org.knowm.xchange.bittrex.dto.account.BittrexWithdrawalHistory;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.bittrex.dto.withdrawal.BittrexNewWithdrawal;
import org.knowm.xchange.bittrex.dto.withdrawal.BittrexWithdrawal;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

public class BittrexAccountServiceRaw extends BittrexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexAccountServiceRaw(
      BittrexExchange exchange,
      BittrexAuthenticated bittrex,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, bittrex, resilienceRegistries);
  }

  public Collection<BittrexBalance> getBittrexBalances() throws IOException {
    return bittrexAuthenticated.getBalances(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator);
  }

  public SequencedBalances getBittrexSequencedBalances() throws IOException {
    BittrexBalances bittrexBalances =
        bittrexAuthenticated.getBalances(
            apiKey, System.currentTimeMillis(), contentCreator, signatureCreator);

    Map<Currency, Balance> balances =
        bittrexBalances.stream()
            .collect(
                Collectors.toMap(
                    BittrexBalance::getCurrencySymbol,
                    balance ->
                        new Balance.Builder()
                            .available(balance.getAvailable())
                            .total(balance.getTotal())
                            .currency(balance.getCurrencySymbol())
                            .timestamp(balance.getUpdatedAt())
                            .build()));

    return new SequencedBalances(
        bittrexBalances.getHeaders().get(BittrexConstants.SEQUENCE).get(0), balances);
  }

  public BittrexBalance getBittrexBalance(Currency currency) throws IOException {
    return bittrexAuthenticated.getBalance(
        apiKey,
        System.currentTimeMillis(),
        contentCreator,
        signatureCreator,
        currency.getCurrencyCode());
  }

  public List<BittrexAddress> getBittrexDepositAddresses(String currency) throws IOException {
    if (currency == null) {
      return bittrexAuthenticated.getAddresses(
          apiKey, System.currentTimeMillis(), contentCreator, signatureCreator);
    }
    return Arrays.asList(
        bittrexAuthenticated.getAddress(
            apiKey, System.currentTimeMillis(), contentCreator, signatureCreator, currency));
  }

  public BittrexAddress generateBittrexDepositAddress(String currency) throws IOException {
    return bittrexAuthenticated.generateAddress(
        apiKey,
        System.currentTimeMillis(),
        contentCreator,
        signatureCreator,
        new BittrexNewAddress(new Currency(currency)));
  }

  public BittrexAccountVolume getBittrexAccountVolume() throws IOException {
    return bittrexAuthenticated.getAccountVolume(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator);
  }

  public List<BittrexComissionRatesWithMarket> getTradingFees() throws IOException {
    return bittrexAuthenticated.getTradingFees(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator);
  }

  public BittrexOrder getBittrexOrder(String orderId) throws IOException {
    return bittrexAuthenticated.getOrder(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator, orderId);
  }

  public List<BittrexDepositHistory> getBittrexDepositsClosed(
      String currencySymbol, String nextPageToken, String previousPageToken, Integer pageSize)
      throws IOException {
    return bittrexAuthenticated.getDepositsClosed(
        apiKey,
        System.currentTimeMillis(),
        contentCreator,
        signatureCreator,
        currencySymbol,
        nextPageToken,
        previousPageToken,
        pageSize);
  }

  public List<BittrexWithdrawalHistory> getBittrexWithdrawalsClosed(
      String currencySymbol, String nextPageToken, String previousPageToken, Integer pageSize)
      throws IOException {
    return bittrexAuthenticated.getWithdrawalsClosed(
        apiKey,
        System.currentTimeMillis(),
        contentCreator,
        signatureCreator,
        currencySymbol,
        nextPageToken,
        previousPageToken,
        pageSize);
  }

  public BittrexWithdrawal createNewWithdrawal(Currency currency, BigDecimal amount, String address)
      throws IOException {
    BittrexNewWithdrawal newWithdrawal = new BittrexNewWithdrawal();
    newWithdrawal.setCurrencySymbol(currency.getCurrencyCode());
    newWithdrawal.setQuantity(amount);
    newWithdrawal.setCryptoAddress(address);

    return bittrexAuthenticated.createNewWithdrawal(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator, newWithdrawal);
  }

  @AllArgsConstructor
  @Getter
  public static class SequencedBalances {
    private final String sequence;
    private final Map<Currency, Balance> balances;
  }
}
