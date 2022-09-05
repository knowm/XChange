package org.knowm.xchange.okex.v5.service;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.okex.v5.OkexAdapters;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.account.OkexAssetBalance;
import org.knowm.xchange.okex.v5.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.v5.dto.account.WithdrawalInfo;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.util.List;

/**
 * Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021
 */
public class OkexAccountService extends OkexAccountServiceRaw implements AccountService {

    private static final String COLON = ":";

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


    @Override
    public String withdrawFunds(WithdrawFundsParams params) throws IOException {
        DefaultWithdrawFundsParams defaultParams;
        if (params instanceof DefaultWithdrawFundsParams) {
            defaultParams = (DefaultWithdrawFundsParams) params;
        } else {
            throw new IllegalStateException("DefaultWithdrawFundsParams must be provided.");
        }

        String addressFromParams = defaultParams.getAddress();
        String addressTagFromParams = defaultParams.getAddressTag();

        String address = addressTagFromParams != null ? addressFromParams + COLON + addressTagFromParams : addressFromParams;

        OkexResponse<List<WithdrawalInfo>> withdraw = withdraw(
                defaultParams.getCurrency().getCurrencyCode(),
                defaultParams.getAmount(),
                address,
                defaultParams.getCommission(),
                defaultParams.getChain(),
                null);

        return withdraw.getData().get(0).getWithdrawalId();
    }
}
