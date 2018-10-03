package org.knowm.xchange.coinfloor.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

public class CoinfloorUserTransactionTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/coinfloor/dto/trade/example-user-transactions.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorUserTransaction[] transactions =
        mapper.readValue(is, CoinfloorUserTransaction[].class);

    assertThat(transactions).hasSize(4);

    CoinfloorUserTransaction btcDeposit = transactions[0];
    assertThat(btcDeposit.getDateTime()).isEqualTo("2017-04-05 19:46:49");
    assertThat(btcDeposit.getType()).isEqualTo(CoinfloorUserTransaction.TransactionType.DEPOSIT);
    assertThat(btcDeposit.isDeposit()).isTrue();
    assertThat(btcDeposit.isWithdrawal()).isFalse();
    assertThat(btcDeposit.isTrade()).isFalse();
    assertThat(btcDeposit.getId()).isEqualTo(58295);
    assertThat(btcDeposit.getCurrency()).isEqualTo(Currency.BTC);
    assertThat(btcDeposit.getCurrencyPair()).isNull();
    assertThat(btcDeposit.getSide()).isNull();
    assertThat(btcDeposit.getAmount()).isEqualTo("0.3500");
    assertThat(btcDeposit.getPrice()).isZero();
    assertThat(btcDeposit.getFee()).isZero();
    assertThat(btcDeposit.getOrderId()).isEqualTo(0);

    CoinfloorUserTransaction gbpWithdrawal = transactions[1];
    assertThat(gbpWithdrawal.getDateTime()).isEqualTo("2017-04-04 20:21:42");
    assertThat(gbpWithdrawal.getType())
        .isEqualTo(CoinfloorUserTransaction.TransactionType.WITHDRAWAL);
    assertThat(gbpWithdrawal.isDeposit()).isFalse();
    assertThat(gbpWithdrawal.isWithdrawal()).isTrue();
    assertThat(gbpWithdrawal.isTrade()).isFalse();
    assertThat(gbpWithdrawal.getId()).isEqualTo(57656);
    assertThat(gbpWithdrawal.getCurrency()).isEqualTo(Currency.GBP);
    assertThat(gbpWithdrawal.getCurrencyPair()).isNull();
    assertThat(gbpWithdrawal.getSide()).isNull();
    assertThat(gbpWithdrawal.getAmount()).isEqualTo("-104.35");
    assertThat(gbpWithdrawal.getPrice()).isZero();
    assertThat(gbpWithdrawal.getFee()).isZero();
    assertThat(gbpWithdrawal.getOrderId()).isEqualTo(0);

    CoinfloorUserTransaction tradeSell = transactions[2];
    assertThat(tradeSell.getDateTime()).isEqualTo("2017-04-03 02:02:52");
    assertThat(tradeSell.getType()).isEqualTo(CoinfloorUserTransaction.TransactionType.TRADE);
    assertThat(tradeSell.isDeposit()).isFalse();
    assertThat(tradeSell.isWithdrawal()).isFalse();
    assertThat(tradeSell.isTrade()).isTrue();
    assertThat(tradeSell.getId()).isEqualTo(2489586572518770L);
    assertThat(tradeSell.getCurrency()).isNull();
    assertThat(tradeSell.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_GBP);
    assertThat(tradeSell.getSide()).isEqualTo(OrderType.ASK);
    assertThat(tradeSell.getAmount()).isEqualTo("-1.8340");
    assertThat(tradeSell.getPrice()).isEqualTo("1027.00");
    assertThat(tradeSell.getFee()).isZero();
    assertThat(tradeSell.getOrderId()).isEqualTo(66564380);

    CoinfloorUserTransaction tradeBuy = transactions[3];
    assertThat(tradeBuy.getDateTime()).isEqualTo("2017-03-12 02:02:52");
    assertThat(tradeBuy.getType()).isEqualTo(CoinfloorUserTransaction.TransactionType.TRADE);
    assertThat(tradeBuy.isDeposit()).isFalse();
    assertThat(tradeBuy.isWithdrawal()).isFalse();
    assertThat(tradeBuy.isTrade()).isTrue();
    assertThat(tradeBuy.getId()).isEqualTo(2489586572518170L);
    assertThat(tradeBuy.getCurrency()).isNull();
    assertThat(tradeBuy.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_GBP);
    assertThat(tradeBuy.getSide()).isEqualTo(OrderType.BID);
    assertThat(tradeBuy.getAmount()).isEqualTo("0.1660");
    assertThat(tradeBuy.getPrice()).isEqualTo("1020.00");
    assertThat(tradeBuy.getFee()).isEqualTo("1.22");
    assertThat(tradeBuy.getOrderId()).isEqualTo(66574450);
  }
}
