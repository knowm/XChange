package org.knowm.xchange.bl3p;

import org.knowm.xchange.bl3p.dto.Bl3pAmountObj;
import org.knowm.xchange.bl3p.dto.account.Bl3pAccountInfo;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bl3pAdapters {

    private Bl3pAdapters() {}

    public static Wallet adaptBalances(Map<String, Bl3pAccountInfo.Bl3pAccountInfoWallet> bl3pBalances) {
        List<Balance> balances = new ArrayList<>(bl3pBalances.size());

        for (Bl3pAccountInfo.Bl3pAccountInfoWallet bl3pWallet : bl3pBalances.values()) {
            balances.add(new Balance(
                    new Currency(bl3pWallet.getAvailable().currency),
                    bl3pWallet.getBalance().value,
                    bl3pWallet.getAvailable().value
            ));
        }

        return new Wallet(balances);
    }

}
