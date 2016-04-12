package org.knowm.xchange.btcchina.service.fix;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.btcchina.service.fix.field.Amount;
import org.knowm.xchange.btcchina.service.fix.field.Balance;
import org.knowm.xchange.btcchina.service.fix.fix44.AccountInfoResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.InvalidMessage;
import quickfix.fix44.MarketDataIncrementalRefresh;
import quickfix.fix44.MarketDataSnapshotFullRefresh;

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

  @Test
  public void testAdaptAccountInfo() throws IOException, InvalidMessage, FieldNotFound {

    String messageData = getMessageData("U1001.txt");

    AccountInfoResponse message = new AccountInfoResponse();
    message.fromString(messageData, dataDictionary, true);

    log.debug(message.toXML(dataDictionary));

    assertEquals("dd6a228e-c2a5-4915-bfa3-78ba1fac91c3", message.getAccReqID().getValue());
    Balance balance = message.getBalance();
    assertEquals(3, balance.getValue());

    List<Group> groups = message.getGroups(Balance.FIELD);
    assertEquals(3, groups.size());

    assertEquals("BTC", groups.get(0).getField(new quickfix.field.Currency()).getValue());
    assertEquals(new BigDecimal("0.001"), groups.get(0).getField(new Amount()).getValue());

    assertEquals("LTC", groups.get(1).getField(new quickfix.field.Currency()).getValue());
    assertEquals(new BigDecimal("0"), groups.get(1).getField(new Amount()).getValue());

    assertEquals("CNY", groups.get(2).getField(new quickfix.field.Currency()).getValue());
    assertEquals(new BigDecimal("0"), groups.get(2).getField(new Amount()).getValue());

    Wallet wallet = BTCChinaFIXAdapters.adaptWallet(message);
    assertEquals(new BigDecimal("0.001"), wallet.getBalance(Currency.BTC).getTotal());
    assertEquals(new BigDecimal("0"), wallet.getBalance(Currency.LTC).getTotal());
    assertEquals(new BigDecimal("0"), wallet.getBalance(Currency.CNY).getTotal());
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

    InputStream inputStream = BTCChinaFIXAdaptersTest.class.getResourceAsStream("/org/knowm/xchange/btcchina/service/fix/fix44/FIX44.xml");
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
