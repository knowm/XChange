package info.bitrich.xchangestream.bitmex.dto;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.junit.Test;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;

/** @author Nikita Belenkiy on 05/06/2018. */
public class BitmexExecutionTest {

  @Test
  public void testDesialization() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    InputStream resourceAsStream =
        getClass()
            .getClassLoader()
            .getResourceAsStream("info/bitrich/xchangestream/bitmex/dto/execution.json");
    BitmexExecution bitmexExecution =
        objectMapper.readValue(resourceAsStream, BitmexExecution.class);

    assertEquals("b47dfbd1-3b88-5678-f6d6-b9314a96c3b8", bitmexExecution.execID);
    assertEquals("5f6c16df-4706-4548-f47d-25f2f915f149", bitmexExecution.orderID);
    assertEquals("1528259635504", bitmexExecution.clOrdID);
    assertEquals("clOrdLinkIDclOrdLinkID", bitmexExecution.clOrdLinkID);
    assertEquals(75430, bitmexExecution.account);
    assertEquals("XBTUSD", bitmexExecution.symbol);
    assertEquals(BitmexSide.SELL, bitmexExecution.side);
    assertEquals(new Long(30), bitmexExecution.lastQty);
    assertEquals(BigDecimal.valueOf(7622.5), bitmexExecution.lastPx);
    assertEquals(null, bitmexExecution.underlyingLastPx);
    assertEquals("XBME", bitmexExecution.lastMkt);
    assertEquals("AddedLiquidity", bitmexExecution.lastLiquidityInd);
    assertEquals(BigDecimal.valueOf(3030), bitmexExecution.simpleOrderQty);
    assertEquals(new Long(30), bitmexExecution.orderQty);
    assertEquals(BigDecimal.valueOf(7622.5), bitmexExecution.price);
    assertEquals(new Long(2), bitmexExecution.displayQty);
    assertEquals(new BigDecimal("7622.1"), bitmexExecution.stopPx);
    assertEquals(null, bitmexExecution.pegOffsetValue);
    assertEquals("", bitmexExecution.pegPriceType);
    assertEquals("USD", bitmexExecution.currency);
    assertEquals("XBt", bitmexExecution.settlCurrency);
    assertEquals("Trade", bitmexExecution.execType);
    assertEquals("Limit", bitmexExecution.ordType);
    assertEquals("GoodTillCancel", bitmexExecution.timeInForce);
    assertEquals("", bitmexExecution.execInst);
    assertEquals("", bitmexExecution.contingencyType);
    assertEquals("XBME", bitmexExecution.exDestination);
    assertEquals(BitmexPrivateOrder.OrderStatus.Filled, bitmexExecution.ordStatus);
    assertEquals("", bitmexExecution.triggered);
    assertEquals(false, bitmexExecution.workingIndicator);
    assertEquals("", bitmexExecution.ordRejReason);
    assertEquals(BigDecimal.valueOf(10), bitmexExecution.simpleLeavesQty);
    assertEquals(new Long(11), bitmexExecution.leavesQty);
    assertEquals(BigDecimal.valueOf(0.0039357), bitmexExecution.simpleCumQty);
    assertEquals(BigDecimal.valueOf(30), bitmexExecution.cumQty);
    assertEquals(BigDecimal.valueOf(7622.5), bitmexExecution.avgPx);
    assertEquals(BigDecimal.valueOf(-0.00025), bitmexExecution.commission);
    assertEquals("PublishTrade", bitmexExecution.tradePublishIndicator);
    assertEquals("SingleSecurity", bitmexExecution.multiLegReportingType);
    assertEquals("Submitted via API.", bitmexExecution.text);
    assertEquals("11bae57a-3a11-83bc-3b71-0e472b89156f", bitmexExecution.trdMatchID);
    assertEquals(new Long(393570), bitmexExecution.execCost);
    assertEquals(new Long(-98), bitmexExecution.execComm);
    assertEquals(BigDecimal.valueOf(-0.0039357), bitmexExecution.homeNotional);
    assertEquals(BigDecimal.valueOf(30), bitmexExecution.foreignNotional);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    assertEquals("2018-06-06T04:35:04.763Z", format.format(bitmexExecution.transactTime));
    assertEquals("2018-06-06T04:35:04.763Z", format.format(bitmexExecution.timestamp));
  }
}
