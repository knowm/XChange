package org.knowm.xchange.bitcoincore;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;

class BitcoinCoreAdaptersTest {

  @Test
  void adaptAccountInfoTest() throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    // available balance
    InputStream getBalance = getClass().getResourceAsStream("/account/example-getbalance.json");
    BitcoinCoreBalanceResponse available =
        mapper.readValue(getBalance, BitcoinCoreBalanceResponse.class);

    // unconfirmed balance
    InputStream getUnconfirmedBalance =
        getClass().getResourceAsStream("/account/example-getunconfirmedbalance.json");
    BitcoinCoreBalanceResponse unconfirmed =
        mapper.readValue(getUnconfirmedBalance, BitcoinCoreBalanceResponse.class);

    AccountInfo account = BitcoinCoreAdapters.adaptAccountInfo(available, unconfirmed);
    Balance btc = account.getWallet().getBalance(Currency.BTC);

    assertThat(btc.getAvailable()).isEqualTo(new BigDecimal("68480.47579046"));
    assertThat(btc.getFrozen()).isEqualTo(new BigDecimal("10.00000001"));
    assertThat(btc.getTotal()).isEqualTo(new BigDecimal("68490.47579047"));
  }
}
