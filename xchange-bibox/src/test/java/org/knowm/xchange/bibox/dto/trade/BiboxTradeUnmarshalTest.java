package org.knowm.xchange.bibox.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.bibox.BiboxTestUtils;
import org.knowm.xchange.bibox.dto.BiboxSingleResponse;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Test Trade JSON parsing
 * 
 * @author odrotleff
 */
public class BiboxTradeUnmarshalTest {

  @Test
  public void testOpenOrdersUnmarshal() throws IOException {

    BiboxSingleResponse<BiboxOpenOrders> response =
        BiboxTestUtils.getResponse(new TypeReference<BiboxSingleResponse<BiboxOpenOrders>>() {},
            "/trade/example-open-orders.json");
    assertThat(response.get().getCmd()).isEqualTo("orderpending/orderPendingList");

    BiboxOpenOrders orders = response.get().getResult();
    assertThat(orders.getItems()).hasSize(2);

    BiboxOpenOrder first = orders.getItems().get(0);
    assertThat(first.getId()).isEqualTo(242640813L);
    assertThat(first.getCreatedAt()).isEqualTo(1518218031000L);
    assertThat(first.getAccountType()).isEqualTo(BiboxAccountType.REGULAR);
    assertThat(first.getCoinSymbol()).isEqualTo("ETC");
    assertThat(first.getCurrencySymbol()).isEqualTo("ETH");
    assertThat(first.getOrderSide()).isEqualTo(BiboxOrderSide.ASK);
    assertThat(first.getOrderType()).isEqualTo(BiboxOrderType.LIMIT_ORDER);
    assertThat(first.getPrice()).isEqualTo(new BigDecimal("1.00000000"));
    assertThat(first.getAmount()).isEqualTo(new BigDecimal("0.0400"));
    assertThat(first.getMoney()).isEqualTo(new BigDecimal("0.04000000"));
    assertThat(first.getDealAmount()).isEqualTo(new BigDecimal("0.0000"));
    assertThat(first.getUnexecuted()).isEqualTo(new BigDecimal("0.0400"));
    assertThat(first.getStatus()).isEqualTo(BiboxOrderStatus.PENDING);

    BiboxOpenOrder second = orders.getItems().get(1);
    assertThat(second.getId()).isEqualTo(242640017L);
    assertThat(second.getCreatedAt()).isEqualTo(1518218019000L);
    assertThat(second.getAccountType()).isEqualTo(BiboxAccountType.REGULAR);
    assertThat(second.getCoinSymbol()).isEqualTo("ETC");
    assertThat(second.getCurrencySymbol()).isEqualTo("ETH");
    assertThat(second.getOrderSide()).isEqualTo(BiboxOrderSide.ASK);
    assertThat(second.getOrderType()).isEqualTo(BiboxOrderType.LIMIT_ORDER);
    assertThat(second.getPrice()).isEqualTo(new BigDecimal("1.00000000"));
    assertThat(second.getAmount()).isEqualTo(new BigDecimal("0.1000"));
    assertThat(second.getMoney()).isEqualTo(new BigDecimal("0.10000000"));
    assertThat(second.getDealAmount()).isEqualTo(new BigDecimal("0.0000"));
    assertThat(second.getUnexecuted()).isEqualTo(new BigDecimal("0.1000"));
    assertThat(second.getStatus()).isEqualTo(BiboxOrderStatus.PENDING);
  }
}
