package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexConstants;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.dto.account.BittrexAccountVolume;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.dto.account.BittrexBalances;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

public class BittrexAccountServiceRaw extends BittrexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexAccountServiceRaw(BittrexExchange exchange) {
    super(exchange);
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

  public BittrexOrder getBittrexOrder(String orderId) throws IOException {
    return bittrexAuthenticated.getOrder(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator, orderId);
  }

  public BittrexAccountVolume getAccountVolume() throws IOException {
    return bittrexAuthenticated.getAccountVolume(
        apiKey, System.currentTimeMillis(), contentCreator, signatureCreator);
  }

  @AllArgsConstructor
  @Getter
  public static class SequencedBalances {
    private final String sequence;
    private final Map<Currency, Balance> balances;
  }
}
