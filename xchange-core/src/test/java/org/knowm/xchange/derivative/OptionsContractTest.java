package org.knowm.xchange.derivative;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.utils.ObjectMapperHelper;

class OptionsContractTest {

  @Test
  void serializeDeserialize() throws Exception {
    OptionsContract contractCall = new OptionsContract("ETH/USD/210719/34000/C");
    OptionsContract jsonCopy2 = ObjectMapperHelper.viaJSON(contractCall);
    assertThat(jsonCopy2).isEqualTo(contractCall);

    OptionsContract contractPut = new OptionsContract("BTC/USDT/210709/34000/P");
    OptionsContract jsonCopy = ObjectMapperHelper.viaJSON(contractPut);
    assertThat(jsonCopy).isEqualTo(contractPut);
  }
}
