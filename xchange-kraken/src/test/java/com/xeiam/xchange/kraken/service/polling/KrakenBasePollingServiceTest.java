package com.xeiam.xchange.kraken.service.polling;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.Order.IOrderFlags;
import com.xeiam.xchange.kraken.KrakenExchange;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderFlags;

public class KrakenBasePollingServiceTest {

  private enum OtherExchangeFlags implements IOrderFlags {
    OTHER;
  };

  @Test
  public void testDelimitSetOrderFlags() {
    ExchangeSpecification specification = new ExchangeSpecification(KrakenExchange.class);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
    KrakenBasePollingService service = new KrakenBasePollingService(exchange);

    assertThat(service.delimitSet(null)).isNull();

    Set<IOrderFlags> flags = new HashSet<IOrderFlags>();
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

}
