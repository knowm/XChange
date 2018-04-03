package org.knowm.xchange.therock.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.dto.account.TheRockBalance;
import org.knowm.xchange.therock.dto.account.TheRockBalances;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawalResponse;
import org.knowm.xchange.therock.dto.marketdata.TheRockTicker;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
import org.knowm.xchange.therock.dto.trade.TheRockOrders;

public class TheRockDtoTest {

  private static ObjectMapper mapper = new ObjectMapper();

  private static <T> T parse(Class<T> theClass) throws IOException {
    LinkedList<String> linkedList =
        new LinkedList<>(
            Arrays.asList(new Object() {}.getClass().getPackage().getName().split("\\.")));
    linkedList.add(theClass.getSimpleName() + ".json");
    String filename =
        Paths.get(linkedList.removeFirst(), linkedList.toArray(new String[0])).toString();
    return parse(filename, theClass);
  }

  private static <E> E parse(String filename, Class<E> type) throws java.io.IOException {
    InputStream is = TheRockDtoTest.class.getResourceAsStream("/" + filename);
    return mapper.readValue(is, type);
  }

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
    assertThat(json.getFundId()).isEqualTo(new TheRock.Pair(CurrencyPair.BTC_EUR));
    assertThat(json.getPrice()).isEqualTo(new BigDecimal("0.0102"));
  }

  @Test
  public void testOrders() throws Exception {
    final TheRockOrders json = parse(TheRockOrders.class);
    TheRockOrder order1 = json.getOrders()[0];
    assertThat(order1.getId()).isEqualTo(54000000);
    assertThat(order1.getFundId()).isEqualTo(new TheRock.Pair(CurrencyPair.BTC_EUR));
    assertThat(order1.getPrice()).isEqualTo(new BigDecimal("506.46"));
    assertThat(order1.getAmount()).isEqualTo(new BigDecimal("0.624"));
    assertThat(order1.getAmountUnfilled()).isEqualTo(new BigDecimal("0.624"));

    final TheRockOrder order2 = json.getOrders()[1];
    assertThat(order2.getId()).isEqualTo(54000001);
    assertThat(order2.getFundId()).isEqualTo(new TheRock.Pair(CurrencyPair.BTC_EUR));
    assertThat(order2.getPrice()).isEqualTo(new BigDecimal("504.11"));
    assertThat(order2.getAmount()).isEqualTo(new BigDecimal("0.399"));
    assertThat(order2.getAmountUnfilled()).isEqualTo(new BigDecimal("0.399"));
  }

  @Test
  public void testException() throws Exception {
    final TheRockException json = parse(TheRockException.class);
    assertThat(json.getMessage()).contains("CNYUSD is not a valid value for param fund_id");
    assertThat(json.getErrors()).hasSize(1);
    assertThat(json.getErrors().get(0).getMessage())
        .isEqualTo("CNYUSD is not a valid value for param fund_id");
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
}
