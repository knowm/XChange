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
import org.knowm.xchange.kraken.dto.account.results.KrakenWebsocketTokenResult;

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
    Assert.assertEquals(3, krakenBalance.getResult().size());
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
    KrakenLedger deposit = ledgerInfo.get("LQY6IE-WNT47-JRBOJV");

    assertThat(deposit.getAsset()).isEqualTo("XXBT");
    assertThat(deposit.getAssetClass()).isEqualTo("currency");
    assertThat(deposit.getBalance()).isEqualTo("0.1000000000");
    assertThat(deposit.getFee()).isEqualTo("0.0000000000");
    assertThat(deposit.getTransactionAmount()).isEqualTo("0.1000000000");
    assertThat(deposit.getLedgerType()).isEqualTo(LedgerType.DEPOSIT);
    assertThat(deposit.getRefId()).isEqualTo("QGBJIZV-4F6SPK-ZCBT5O");
    assertThat(deposit.getUnixTime()).isEqualTo(1391400160.0679);

    KrakenLedger settled = ledgerInfo.get("L23XKW-ZZHEP-FJINZ3");

    assertThat(settled.getAsset()).isEqualTo("XETH");
    assertThat(settled.getAssetClass()).isEqualTo("currency");
    assertThat(settled.getBalance()).isEqualTo("0.5000000000");
    assertThat(settled.getFee()).isEqualTo("0.0000000000");
    assertThat(settled.getTransactionAmount()).isEqualTo("0.5000000000");
    assertThat(settled.getLedgerType()).isEqualTo(LedgerType.SETTLED);
    assertThat(settled.getRefId()).isEqualTo("TPYCPK-GLBCV-NGPDDI");
    assertThat(settled.getUnixTime()).isEqualTo(1388739905.0371);
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

  @Test
  public void testKrakenWebsocketTokenUnmarshal() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        KrakenAccountJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/account/example-krakenWebsocketToken-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenWebsocketTokenResult krakenResult =
        mapper.readValue(is, KrakenWebsocketTokenResult.class);
    KrakenWebsocketToken tokenResult = krakenResult.getResult();

    assertThat(tokenResult.getToken()).isEqualTo("44444444444444444444444444444444");
    assertThat(tokenResult.getExpiresInSeconds()).isEqualTo(900);
  }
}
