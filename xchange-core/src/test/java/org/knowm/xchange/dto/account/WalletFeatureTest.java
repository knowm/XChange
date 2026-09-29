package org.knowm.xchange.dto.account;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WalletFeatureTest {

  static Set<Wallet.WalletFeature> walletFeatureSet;

  @BeforeAll
  static void setUp() {
    walletFeatureSet = new HashSet<>();
    walletFeatureSet.add(Wallet.WalletFeature.TRADING);
    walletFeatureSet.add(Wallet.WalletFeature.FUNDING);
  }

  @Test
  void whenNoWalletFeatureExistsThenReturnDefault() {
    Wallet wallet = Wallet.Builder.from(new ArrayList<>()).build();
    AccountInfo accountInfo = new AccountInfo(wallet);

    assertThat(accountInfo.getWallet(Wallet.WalletFeature.TRADING)).isNotNull();
    assertThat(accountInfo.getWallet(Wallet.WalletFeature.TRADING).getFeatures()).isNotNull();
    assertThat(accountInfo.getWallet(Wallet.WalletFeature.TRADING).getFeatures().size())
        .isEqualTo(2);
    assertThat(
            accountInfo
                .getWallet(Wallet.WalletFeature.TRADING)
                .getFeatures()
                .contains(Wallet.WalletFeature.TRADING))
        .isTrue();
    assertThat(
            accountInfo
                .getWallet(Wallet.WalletFeature.TRADING)
                .getFeatures()
                .contains(Wallet.WalletFeature.FUNDING))
        .isTrue();
  }

  @Test
  void whenWalletWithSpecificFeatureExistsThenReturnWallet() {
    Wallet wallet = Wallet.Builder.from(new ArrayList<>()).features(walletFeatureSet).build();
    AccountInfo accountInfo = new AccountInfo(wallet);

    assertThat(accountInfo.getWallet(Wallet.WalletFeature.TRADING)).isEqualTo(wallet);
  }

  @Test
  void whenMoreThanOneWalletWithSpecificFeatureExistThenThrowUnsupportedOperationExeption() {
    Wallet wallet1 =
        Wallet.Builder.from(new ArrayList<>()).id("id1").features(walletFeatureSet).build();
    Wallet wallet2 =
        Wallet.Builder.from(new ArrayList<>()).id("id2").features(walletFeatureSet).build();
    AccountInfo accountInfo = new AccountInfo(wallet1, wallet2);
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> accountInfo.getWallet(Wallet.WalletFeature.TRADING));
  }
}
