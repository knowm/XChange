package org.knowm.xchange.bybit;

import org.knowm.xchange.bybit.dto.account.BybitBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BybitAdapters {

    public static Wallet adaptBybitBalances(List<BybitBalance> bybitBalances){
        List<Balance> balances = new ArrayList<>(bybitBalances.size());
        for (BybitBalance bybitBalance : bybitBalances) {
            balances.add(new Balance(new Currency(bybitBalance.getCoin()),
                    new BigDecimal(bybitBalance.getTotal()),
                    new BigDecimal(bybitBalance.getFree())
            ));
        }
        return Wallet.Builder.from(balances).build();
    }

}
