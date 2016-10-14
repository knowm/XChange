package org.knowm.xchange.coinbase.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfer.CoinbaseTransferStatus;
import org.knowm.xchange.utils.DateUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jamespedwards42
 */
public class CoinbaseTradeJsonTest {

  @Test
  public void testDeserializeTransfers() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinbaseTradeJsonTest.class.getResourceAsStream("/trade/example-transfers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbaseTransfers transfers = mapper.readValue(is, CoinbaseTransfers.class);

    List<CoinbaseTransfer> transferList = transfers.getTransfers();
    assertThat(transferList.size()).isEqualTo(1);

    CoinbaseTransfer transfer = transferList.get(0);
    assertThat(transfer.getId()).isEqualTo("52f4411667c71baf9000003c");
    assertThat(transfer.getCreatedAt()).isEqualTo(DateUtils.fromISO8601DateString("2014-02-06T18:12:38-08:00"));
    assertThat(transfer.getCoinbaseFee()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("9.05")));
    assertThat(transfer.getBankFee()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal(".15")));
    assertThat(transfer.getPayoutDate()).isEqualTo(DateUtils.fromISO8601DateString("2014-02-06T18:12:37-08:00"));
    assertThat(transfer.getTransactionId()).isEqualTo("52f4411767c71baf9000003f");
    assertThat(transfer.getFundingType()).isEqualTo("AchDebit");
    assertThat(transfer.getCode()).isEqualTo("52f4411667c71baf9000003c");
    assertThat(transfer.getType()).isEqualTo(CoinbaseTransferType.BUY);
    assertThat(transfer.getStatus()).isEqualTo(CoinbaseTransferStatus.COMPLETED);
    assertThat(transfer.getBtcAmount()).isEqualsToByComparingFields(new CoinbaseMoney("BTC", new BigDecimal("1.20000000")));
    assertThat(transfer.getSubtotal()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("905.10")));
    assertThat(transfer.getTotal()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("914.30")));
    assertThat(transfer.getDescription())
        .isEqualTo("Bought 1.20 BTC for $914.30.  \n\nPaid for with Bank ****. Your bitcoin will arrive by the end of day on Thursday Feb  6, 2014.");

  }
}
