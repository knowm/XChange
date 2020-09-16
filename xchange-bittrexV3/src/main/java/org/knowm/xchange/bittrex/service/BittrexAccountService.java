package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import org.knowm.xchange.bittrex.BittrexAdapters;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.dto.account.BittrexAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsZero;

public class BittrexAccountService extends BittrexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexAccountService(BittrexExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(BittrexAdapters.adaptWallet(getBittrexBalances()));
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return getBittrexDepositAddresses(currency.getCurrencyCode()).get(0).getCryptoAddress();
  }

  @Override
  public AddressWithTag requestDepositAddressData(Currency currency, String... args)
      throws IOException {
    BittrexAddress address = getBittrexDepositAddresses(currency.getCurrencyCode()).get(0);
    return new AddressWithTag(address.getCryptoAddress(), address.getCryptoAddressTag());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return TradeHistoryParamsZero.PARAMS_ZERO;
  }
}
