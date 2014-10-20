package com.xeiam.xchange.btcchina.service.fix;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.FieldNotFound;
import quickfix.InvalidMessage;
import quickfix.fix44.MarketDataIncrementalRefresh;
import quickfix.fix44.MarketDataSnapshotFullRefresh;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;

public class BTCChinaFIXAdaptersTest {

  private final Logger log = LoggerFactory.getLogger(BTCChinaFIXAdaptersTest.class);

  private static DataDictionary dataDictionary;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

    dataDictionary = getDataDictionary();
  }

  @Test
  public void testAdaptTicker() throws IOException, InvalidMessage, FieldNotFound {

    Ticker ticker = getTicker();

    assertEquals(CurrencyPair.BTC_CNY, ticker.getCurrencyPair());
    assertEquals(1413472045000L, ticker.getTimestamp().getTime());
    assertEquals(new BigDecimal("2328.08"), ticker.getBid());
    assertEquals(new BigDecimal("2329.14"), ticker.getAsk());
    assertEquals(new BigDecimal("2328.09"), ticker.getLast());
    assertEquals(new BigDecimal("2447.87"), ticker.getHigh());
    assertEquals(new BigDecimal("2316"), ticker.getLow());
    assertEquals(new BigDecimal("56083.6739"), ticker.getVolume());
  }

  @Test
  public void testAdaptUpdate() throws IOException, InvalidMessage, FieldNotFound {

    Ticker oldTicker = getTicker();

    String messageData = getMessageData("X.txt");

    MarketDataIncrementalRefresh message = new MarketDataIncrementalRefresh();
    message.fromString(messageData, dataDictionary, true);

    log.debug(message.toXML(dataDictionary));

    Ticker ticker = BTCChinaFIXAdapters.adaptUpdate(oldTicker, message);

    assertEquals(CurrencyPair.BTC_CNY, ticker.getCurrencyPair());
    assertEquals(1413482716000L, ticker.getTimestamp().getTime());
    assertEquals(new BigDecimal("2328.08"), ticker.getBid());
    assertEquals(new BigDecimal("2325.53"), ticker.getAsk());
    assertEquals(new BigDecimal("2328.09"), ticker.getLast());
    assertEquals(new BigDecimal("2447.87"), ticker.getHigh());
    assertEquals(new BigDecimal("2316"), ticker.getLow());
    assertEquals(new BigDecimal("53826.1999"), ticker.getVolume());

  }

  private Ticker getTicker() throws IOException, InvalidMessage, FieldNotFound {

    String messageData = getMessageData("W.txt");

    MarketDataSnapshotFullRefresh message = new MarketDataSnapshotFullRefresh();
    message.fromString(messageData, dataDictionary, true);

    log.debug(message.toXML(dataDictionary));

    Ticker ticker = BTCChinaFIXAdapters.adaptTicker(message);
    return ticker;
  }

  private static DataDictionary getDataDictionary() throws IOException, ConfigError {

    InputStream inputStream = BTCChinaFIXAdaptersTest.class.getResourceAsStream("/FIX44.xml");
    DataDictionary dataDictionary = new DataDictionary(inputStream);
    inputStream.close();
    return dataDictionary;
  }

  private String getMessageData(String resource) throws IOException {

    String messageData = IOUtils.toString(getClass().getResource(resource), Charsets.UTF_8).trim();
    messageData = StringUtils.replace(messageData, "^A", "\1");
    return messageData;
  }

}
