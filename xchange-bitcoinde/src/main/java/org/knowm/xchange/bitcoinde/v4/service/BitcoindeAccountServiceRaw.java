package org.knowm.xchange.bitcoinde.v4.service;

import static org.knowm.xchange.bitcoinde.BitcoindeUtils.rfc3339Timestamp;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeAccountLedgerType;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedgerWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.currency.Currency;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoindeAccountServiceRaw extends BitcoindeBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  protected BitcoindeAccountServiceRaw(BitcoindeExchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BitcoindeAccountWrapper getBitcoindeAccount() throws IOException {
    try {
      return bitcoinde.getAccount(apiKey, nonceFactory, signatureCreator);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  /**
   * Calls the API function Bitcoinde.getAccountLedger().
   *
   * @param currency mandatory
   * @param type optional (default: all)
   * @param start optional (default: 10 days ago)
   * @param end optional (default: yesterday)
   * @param page optional (default: 1)
   * @return BitcoindeAccountLedgerWrapper
   * @throws IOException
   */
  public BitcoindeAccountLedgerWrapper getAccountLedger(
      Currency currency, BitcoindeAccountLedgerType type, Date start, Date end, Integer page)
      throws IOException {

    String typeAsString = type != null ? type.getValue() : null;
    String startAsString = start != null ? rfc3339Timestamp(start) : null;
    String endAsString = end != null ? rfc3339Timestamp(end) : null;

    try {
      return bitcoinde.getAccountLedger(
          apiKey,
          nonceFactory,
          signatureCreator,
          BitcoindeUtils.createBitcoindeCurrency(currency),
          typeAsString,
          startAsString,
          endAsString,
          page);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }
}
