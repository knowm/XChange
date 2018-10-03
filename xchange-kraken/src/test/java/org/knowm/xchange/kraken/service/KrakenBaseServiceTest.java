package org.knowm.xchange.kraken.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderFlags;

public class KrakenBaseServiceTest {

  @Test
  public void testDelimitSetOrderFlags() {

    ExchangeSpecification specification = new ExchangeSpecification(KrakenExchange.class);
    specification.setShouldLoadRemoteMetaData(false);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
    KrakenBaseService service = new KrakenBaseService(exchange);

    assertThat(service.delimitSet(null)).isNull();

    Set<IOrderFlags> flags = new HashSet<>();
    assertThat(service.delimitSet(flags)).isNull();

    flags.add(KrakenOrderFlags.NOMPP);
    assertThat(service.delimitSet(flags)).isEqualTo(KrakenOrderFlags.NOMPP.toString());

    flags.add(OtherExchangeFlags.OTHER); // this flag should not be added to the string
    assertThat(service.delimitSet(flags)).isEqualTo(KrakenOrderFlags.NOMPP.toString());
    flags.remove(OtherExchangeFlags.OTHER);

    flags.add(KrakenOrderFlags.VIQC);
    flags.add(KrakenOrderFlags.FCIQ);
    // flags should now contain NOMPP, VIQC and FCIQ
    Collection<String> flagsAsStrings = Arrays.asList(service.delimitSet(flags).split(","));
    assertThat(flagsAsStrings.size()).isEqualTo(3);
    assertThat(flagsAsStrings.size()).isEqualTo(flags.size());
    for (IOrderFlags flag : flags) {
      assertThat(flagsAsStrings).contains(flag.toString());
    }
  }

  private enum OtherExchangeFlags implements IOrderFlags {
    OTHER
  }
}
