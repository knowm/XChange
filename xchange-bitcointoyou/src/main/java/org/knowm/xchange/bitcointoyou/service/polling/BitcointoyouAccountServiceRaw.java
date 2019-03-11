package org.knowm.xchange.bitcointoyou.service.polling;

import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcointoyou.BitcointoyouAdapters;
import org.knowm.xchange.bitcointoyou.BitcointoyouException;
import org.knowm.xchange.bitcointoyou.dto.account.BitcointoyouBalance;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * AccountService raw implementation for Bitcointoyou Exchange.
 *
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
class BitcointoyouAccountServiceRaw extends BitcointoyouBasePollingService {

  /**
   * Constructor
   *
   * @param exchange the Bitcointoyou Exchange
   */
  BitcointoyouAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  List<Balance> getWallets() {

    try {
      BitcointoyouBalance response =
          bitcointoyouAuthenticated.returnBalances(
              apiKey, exchange.getNonceFactory(), signatureCreator);
      return BitcointoyouAdapters.adaptBitcointoyouBalances(response);
    } catch (BitcointoyouException e) {
      throw new ExchangeException(e.getError());
    }
  }
}
