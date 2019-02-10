package org.knowm.xchange.kraken.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.kraken.dto.account.results.KrakenBalanceResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenLedgerResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenTradeBalanceInfoResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenTradeVolumeResult;

public class KrakenAccountJSONTest {

  @Before
  public void setUp() throws Exception {}

  @Test
  public void testBalanceUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenAccountJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/account/example-balance-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenBalanceResult krakenBalance = mapper.readValue(is, KrakenBalanceResult.class);
    Assert.assertEquals(2, krakenBalance.getResult().size());
    assertThat(krakenBalance.getResult().get("ZUSD")).isNull();
    assertThat(krakenBalance.getResult().get("ZEUR")).isEqualTo("1.0539");
  }

  @Test
  public void testBalanceInfoUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenAccountJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/account/example-tradebalanceinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradeBalanceInfoResult krakenResult =
        mapper.readValue(is, KrakenTradeBalanceInfoResult.class);
    KrakenTradeBalanceInfo tradeBalanceInfo = krakenResult.getResult();

    assertThat(tradeBalanceInfo.getTradeBalance()).isEqualTo("71.6310");
    assertThat(tradeBalanceInfo.getMargin()).isEqualTo("0.0000");
    assertThat(tradeBalanceInfo.getFreeMargin()).isEqualTo("71.6310");
    assertThat(tradeBalanceInfo.getCostBasis()).isEqualTo("0.0000");
    assertThat(tradeBalanceInfo.getEquity()).isEqualTo("71.6310");
    assertThat(tradeBalanceInfo.getFloatingValuation()).isEqualTo("0.0000");
    assertThat(tradeBalanceInfo.getUnrealizedGainsLosses()).isEqualTo("0.0000");
  }

  @Test
  public void testLedgerInfoUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenAccountJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/account/example-ledgerinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenLedgerResult krakenResult = mapper.readValue(is, KrakenLedgerResult.class);
    Map<String, KrakenLedger> ledgerInfo = krakenResult.getResult().getLedgerMap();
    KrakenLedger ledger = ledgerInfo.get("LQY6IE-WNT47-JRBOJV");

    assertThat(ledger.getAsset()).isEqualTo("XXBT");
    assertThat(ledger.getAssetClass()).isEqualTo("currency");
    assertThat(ledger.getBalance()).isEqualTo("0.1000000000");
    assertThat(ledger.getFee()).isEqualTo("0.0000000000");
    assertThat(ledger.getTransactionAmount()).isEqualTo("0.1000000000");
    assertThat(ledger.getLedgerType()).isEqualTo(LedgerType.DEPOSIT);
    assertThat(ledger.getRefId()).isEqualTo("QGBJIZV-4F6SPK-ZCBT5O");
    assertThat(ledger.getUnixTime()).isEqualTo(1391400160.0679);
  }

  @Test
  public void testTradeVolumeUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenAccountJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/account/example-tradevolume-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradeVolumeResult krakenResult = mapper.readValue(is, KrakenTradeVolumeResult.class);
    KrakenTradeVolume tradeVolume = krakenResult.getResult();

    assertThat(tradeVolume.getCurrency()).isEqualTo("ZUSD");
    assertThat(tradeVolume.getVolume()).isEqualTo("451.3040");
    Map<String, KrakenVolumeFee> fees = tradeVolume.getFees();
    KrakenVolumeFee fee = fees.get("XXBTZUSD");
    assertThat(fee.getFee()).isEqualTo("0.3000");
    assertThat(fee.getMinFee()).isEqualTo("0.0500");
    assertThat(fee.getMaxFee()).isEqualTo("0.3000");
    assertThat(fee.getNextFee()).isEqualTo("0.2900");
    assertThat(fee.getNextVolume()).isEqualTo("1000.0000");
    assertThat(fee.getTierVolume()).isEqualTo("0.0000");

    KrakenVolumeFee maker = tradeVolume.getFeesMaker().get("XXBTZUSD");
    assertThat(maker.getFee()).isEqualTo("0.1600");
    assertThat(maker.getMinFee()).isEqualTo("0.0000");
    assertThat(maker.getMaxFee()).isEqualTo("0.1600");
    assertThat(maker.getNextFee()).isEqualTo("0.1400");
    assertThat(maker.getNextVolume()).isEqualTo("1000.0000");
    assertThat(maker.getTierVolume()).isEqualTo("0.0000");
  }
}
