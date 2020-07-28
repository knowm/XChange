package org.knowm.xchange.bitcoinde.v4.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeAccountLedgerType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public class BitcoindeAccountLedgerWrapperTest {

  @Test
  public void testBitcoindeAccountLedgerWrapper() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAccountLedgerWrapperTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/account_ledger.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeAccountLedgerWrapper accountLedgerWrapper =
        mapper.readValue(is, BitcoindeAccountLedgerWrapper.class);

    // Make sure balance values are correct
    assertThat(accountLedgerWrapper.getAccountLedgers()).isNotEmpty();
    assertThat(accountLedgerWrapper.getAccountLedgers().size()).isEqualTo(3);

    BitcoindeAccountLedger trade = accountLedgerWrapper.getAccountLedgers().get(0);
    assertThat(trade.getDate()).isEqualTo("2015-08-13T10:20:27+02:00");
    assertThat(trade.getType()).isEqualByComparingTo(BitcoindeAccountLedgerType.SELL);
    assertThat(trade.getReference()).isEqualTo("NVP39U");
    assertThat(trade.getTrade()).isNotNull();
    assertThat(trade.getTrade().getTradeId()).isEqualTo("NVP39U");
    assertThat(trade.getTrade().getTradingPair()).isEqualByComparingTo(CurrencyPair.BTC_EUR);
    assertThat(trade.getTrade().getPrice()).isEqualByComparingTo("243.77");
    assertThat(trade.getTrade().getCurrencyToTrade()).isNotNull();
    assertThat(trade.getTrade().getCurrencyToTrade().getCurrency())
        .isEqualByComparingTo(Currency.BTC);
    assertThat(trade.getTrade().getCurrencyToTrade().getBeforeFee())
        .isEqualByComparingTo("1.71600000");
    assertThat(trade.getTrade().getCurrencyToTrade().getAfterFee())
        .isEqualByComparingTo("1.69884000");
    assertThat(trade.getTrade().getCurrencyToPay().getCurrency())
        .isEqualByComparingTo(Currency.EUR);
    assertThat(trade.getTrade().getCurrencyToPay().getBeforeFee()).isEqualByComparingTo("418.31");
    assertThat(trade.getTrade().getCurrencyToPay().getAfterFee()).isEqualByComparingTo("414.13");
    assertThat(trade.getCashflow()).isEqualByComparingTo("-1.71600000");
    assertThat(trade.getBalance()).isEqualByComparingTo("3.00019794");

    BitcoindeAccountLedger payout1 = accountLedgerWrapper.getAccountLedgers().get(1);
    assertThat(payout1.getDate()).isEqualTo("2015-08-12T13:05:02+02:00");
    assertThat(payout1.getType()).isEqualByComparingTo(BitcoindeAccountLedgerType.PAYOUT);
    assertThat(payout1.getTrade()).isNull();
    assertThat(payout1.getReference())
        .isEqualTo("dqwdqwdwqwq4dqw4d5qd45qd45qwd4qw5df45g4r5g4trh4r5j5j4tz5j4tbc");
    assertThat(payout1.getCashflow()).isEqualByComparingTo("-0.10000000");
    assertThat(payout1.getBalance()).isEqualByComparingTo("4.71619794");

    BitcoindeAccountLedger payout2 = accountLedgerWrapper.getAccountLedgers().get(2);
    assertThat(payout2.getDate()).isEqualTo("2015-08-12T12:30:01+02:00");
    assertThat(payout2.getType()).isEqualByComparingTo(BitcoindeAccountLedgerType.PAYOUT);
    assertThat(payout2.getTrade()).isNull();
    assertThat(payout2.getReference())
        .isEqualTo("bdgwflwguwgr884t34g4g555h4zr5j4fh5j48rg4s5bx2nt4jr5jr45j4r5j4");
    assertThat(payout2.getCashflow()).isEqualByComparingTo("-1.91894200");
    assertThat(payout2.getBalance()).isEqualByComparingTo("4.81619794");
  }
}
