package org.knowm.xchange.coinbasepro.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill.Liquidity;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill.Side;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class CoinbaseProFillTest {

  @Test
  public void unmarshalTest() throws IOException {

    JacksonObjectMapperFactory factory = new DefaultJacksonObjectMapperFactory();
    ObjectMapper mapper = factory.createObjectMapper();

    InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/coinbasepro/dto/trade/example-fills.json");
    CoinbaseProFill[] fills = mapper.readValue(is, CoinbaseProFill[].class);

    assertThat(fills).hasSize(1);

    CoinbaseProFill fill = fills[0];
    assertThat(fill.getTradeId()).isEqualTo("470768");
    assertThat(fill.getProductId()).isEqualTo("ETH-BTC");
    assertThat(fill.getPrice()).isEqualTo("0.05915000");
    assertThat(fill.getSize()).isEqualTo("0.01000000");
    assertThat(fill.getOrderId()).isEqualTo("b4b3bbb1-e0e3-4532-9413-23123448ce35");
    assertThat(fill.getCreatedAt()).isEqualTo("2017-05-01T07:31:50.243Z");
    assertThat(fill.getLiquidity()).isEqualTo(Liquidity.T);
    assertThat(fill.getFee()).isEqualTo("0.0000017745000000");
    assertThat(fill.isSettled()).isTrue();
    assertThat(fill.getSide()).isEqualTo(Side.buy);
  }
}
