package org.knowm.xchange.raydium;

import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RaydiumExchangeTest {

  @Test
  public void initTest() {
    RaydiumExchange raydiumExchange =
        ExchangeFactory.INSTANCE.createExchange(RaydiumExchange.class);

    assertThat(raydiumExchange.getRaydiumProgram().getTokenList()).isNotNull();
    assertThat(raydiumExchange.getRaydiumProgram().getFarmList()).isNotNull();
    assertThat(raydiumExchange.getRaydiumProgram().getLpList()).isNotNull();
  }
}
