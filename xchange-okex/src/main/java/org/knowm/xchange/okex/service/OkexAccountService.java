package org.knowm.xchange.okex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.okex.OkexAdapters;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.account.OkexAssetBalance;
import org.knowm.xchange.okex.dto.account.OkexWalletBalance;
import org.knowm.xchange.service.account.AccountService;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAccountService extends OkexAccountServiceRaw implements AccountService {
  public OkexAccountService(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public AccountInfo getAccountInfo() throws IOException {
    // null to get assets (with non-zero balance), remaining balance, and available amount in the
    // account.
    OkexResponse<List<OkexWalletBalance>> tradingBalances = getWalletBalances(null);
    OkexResponse<List<OkexAssetBalance>> assetBalances = getAssetBalances(null);
    return new AccountInfo(
        OkexAdapters.adaptOkexBalances(tradingBalances.getData()),
        OkexAdapters.adaptOkexAssetBalances(assetBalances.getData()));
  }
}
