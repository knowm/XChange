package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.util.LocalExchangeConfig;
import info.bitrich.xchangestream.util.PropsLoader;
import info.bitrich.xchangestream.util.ProxyUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Foat Akhmadeev 18/06/2018 */
public class BitmexWithProxyIT {
  private static final Logger LOG = LoggerFactory.getLogger(BitmexWithProxyIT.class);

  private StreamingExchange exchange;
  private ProxyUtil proxyUtil;

  @Before
  public void setup() throws Exception {
    String execLine = PropsLoader.proxyExecLine();
    LOG.info("Running proxy with \"{}\" command", execLine);
    proxyUtil = new ProxyUtil(execLine, 5000);
    proxyUtil.startProxy();
    LocalExchangeConfig localConfig =
        PropsLoader.loadKeys("bitmex.secret.keys", "bitmex.secret.keys.origin", "bitmex");
    exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(BitmexStreamingExchange.class.getName());

    ExchangeSpecification exchangeSpecification =
        BitmexTestsCommons.getExchangeSpecification(
            localConfig, exchange.getDefaultExchangeSpecification());
    exchangeSpecification.setExchangeSpecificParametersItem(
        StreamingExchange.SOCKS_PROXY_HOST, "localhost");
    exchangeSpecification.setExchangeSpecificParametersItem(
        StreamingExchange.SOCKS_PROXY_PORT, 8889);

    exchange.applySpecification(exchangeSpecification);
    exchange.connect().blockingAwait();
  }

  @After
  public void tearDown() {
    exchange.disconnect().blockingAwait();
    proxyUtil.shutdown();
  }

  @Test
  public void shouldReconnectOnFailure() throws Exception {
    Assert.assertTrue(exchange.isAlive());
    exchange
        .reconnectFailure()
        .subscribe(
            e -> {
              LOG.info("reconnection issue", e);
            });

    proxyUtil.stopProxy();

    Thread.sleep(5000);
    Assert.assertFalse(exchange.isAlive());
    proxyUtil.startProxy();

    Thread.sleep(15000);
    Assert.assertTrue(exchange.isAlive());
  }
}
