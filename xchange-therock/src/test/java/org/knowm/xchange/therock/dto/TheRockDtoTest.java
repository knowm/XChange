package org.knowm.xchange.therock.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.dto.account.TheRockBalance;
import org.knowm.xchange.therock.dto.account.TheRockBalances;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawalResponse;
import org.knowm.xchange.therock.dto.marketdata.TheRockTicker;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class TheRockDtoTest {

  private static ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testTicker() throws Exception {
    final TheRockTicker json = parse(TheRockTicker.class);
    assertThat(json.getVolume()).isEqualTo(new BigDecimal("25726.86"));
    assertThat(json.getDate()).isEqualTo(getDate("2015-06-13T17:17:45.847+00:00"));
    assertThat(json.getFundId()).isEqualTo(CurrencyPair.BTC_EUR);
  }

  @Test
  public void testBalance() throws Exception {
    final TheRockBalance json = parse(TheRockBalance.class);
    assertThat(json.getCurrency()).isEqualTo("LTC");
    assertThat(json.getBalance()).isEqualTo(new BigDecimal("6.50884835"));
    assertThat(json.getTradingBalance()).isEqualTo(new BigDecimal("2.30884835"));
  }

  @Test
  public void testBalances() throws Exception {
    final TheRockBalances json = parse(TheRockBalances.class);
    assertThat(json.getBalances()).hasSize(2);
    final TheRockBalance balance = json.getBalances().get(0);
    assertThat(balance.getCurrency()).isEqualTo("LTC");
    assertThat(balance.getBalance()).isEqualTo(new BigDecimal("6.50884835"));
    assertThat(balance.getTradingBalance()).isEqualTo(new BigDecimal("2.30884835"));
  }

  @Test
  public void testOrder() throws Exception {
    final TheRockOrder json = parse(TheRockOrder.class);
    assertThat(json.getId()).isEqualTo(4325578);
    assertThat(json.getPrice()).isEqualTo(new BigDecimal("0.0102"));
    assertThat(json.getFundId()).isEqualTo(new TheRock.Pair(CurrencyPair.BTC_EUR));
  }

  @Test
  public void testException() throws Exception {
    final TheRockException json = parse(TheRockException.class);
    assertThat(json.getMessage()).contains("CNYUSD is not a valid value for param fund_id");
    assertThat(json.getErrors()).hasSize(1);
    assertThat(json.getErrors().get(0).getMessage()).isEqualTo("CNYUSD is not a valid value for param fund_id");
    assertThat(json.getErrors().get(0).getCode()).isEqualTo(11);
  }

  @Test
  public void testWithdrawalResponse() throws Exception {
    final TheRockWithdrawalResponse json = parse(TheRockWithdrawalResponse.class);
    assertThat(json.getTransactionId()).isEqualTo(65088485);
  }

  private Date getDate(String dateStr) throws ParseException {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").parse(dateStr);
  }

  private static <T> T parse(Class<T> theClass) throws IOException {
    return parse(theClass.getSimpleName() + ".json", theClass);
  }

  private static <E> E parse(String filename, Class<E> type) throws java.io.IOException {
    InputStream is = TheRockDtoTest.class.getResourceAsStream("/" + filename);
    return mapper.readValue(is, type);
  }
}