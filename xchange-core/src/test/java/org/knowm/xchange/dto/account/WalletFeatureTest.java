package org.knowm.xchange.dto.account;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WalletFeatureTest {

    static Set<WalletFeature> walletFeatureSet;

    @BeforeClass
    public static void setUp(){
        walletFeatureSet = new HashSet<>();
        walletFeatureSet.add(WalletFeature.TRADING);
        walletFeatureSet.add(WalletFeature.FUNDING);
    }

    @Test
    public void whenNoWalletFeatureExistsThenReturnNull(){
        Wallet wallet = new Wallet("id","name",new ArrayList<Balance>(),null);
        AccountInfo accountInfo = new AccountInfo(wallet);

        assertThat(accountInfo.getWallet(WalletFeature.TRADING)).isNull();
    }

    @Test
    public void whenWalletWithSpecificFeatureExistsThenReturnWallet(){
        Wallet wallet = new Wallet("id","name",new ArrayList<Balance>(),walletFeatureSet);
        AccountInfo accountInfo = new AccountInfo(wallet);

        assertThat(accountInfo.getWallet(WalletFeature.TRADING)).isEqualTo(wallet);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenMoreThanOneWalletWithSpecificFeatureExistThenThrowUnsupportedOperationExeption(){
        Wallet wallet1 = new Wallet("id","name",new ArrayList<Balance>(),walletFeatureSet);
        Wallet wallet2 = new Wallet("id2","name2",new ArrayList<Balance>(),walletFeatureSet);

        AccountInfo accountInfo = new AccountInfo(wallet1,wallet2);

        accountInfo.getWallet(WalletFeature.TRADING);
    }
}
