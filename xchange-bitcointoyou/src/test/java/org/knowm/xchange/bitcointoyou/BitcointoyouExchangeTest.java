package org.knowm.xchange.bitcointoyou;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.util.Map;

/**
 * Testes the {@link BitcointoyouExchange} class
 *
 * @author Danilo Guimaraes
 */
public class BitcointoyouExchangeTest {

  private Exchange sut;

  @Before
  public void setUp() throws Exception {
    sut = ExchangeFactory.INSTANCE.createExchange(BitcointoyouExchange.class);
  }

  @Test
  public void testNonceFactory() throws Exception {

    final SoftAssertions softly = new SoftAssertions();
    SynchronizedValueFactory<Long> nonceFactory = sut.getNonceFactory();

    softly.assertThat(nonceFactory).isNotNull();
    softly.assertThat(nonceFactory).isInstanceOf(AtomicLongIncrementalTime2013NonceFactory.class);

    softly.assertAll();
  }

  @Test
  public void testExchangeMetaData() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    ExchangeMetaData exchangeMetaData = sut.getExchangeMetaData();

    softly.assertThat(exchangeMetaData).isNotNull();
    softly.assertThat(exchangeMetaData.isShareRateLimits()).isTrue();

    softly.assertThat(exchangeMetaData.getCurrencies()).size().isEqualTo(1);

    Map<Instrument, InstrumentMetaData> currencyPairs = exchangeMetaData.getInstruments();
    softly.assertThat(currencyPairs).size().isEqualTo(1);
    softly.assertThat(currencyPairs).size().isEqualTo(1);

    softly.assertThat(exchangeMetaData.isShareRateLimits()).isTrue();

    RateLimit rateLimit = exchangeMetaData.getPrivateRateLimits()[0];
    softly.assertThat(rateLimit.getPollDelayMillis()).isEqualTo(10000);

    softly.assertAll();
  }
}
