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

    BiboxSingleResponse<BiboxOrders> response =
        BiboxTestUtils.getResponse(new TypeReference<BiboxSingleResponse<BiboxOrders>>() {},
            "/trade/example-open-orders.json");
    assertThat(response.get().getCmd()).isEqualTo("orderpending/orderPendingList");

    BiboxOrders orders = response.get().getResult();
    assertThat(orders.getItems()).hasSize(2);

    BiboxOrder first = orders.getItems().get(0);
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

    BiboxOrder second = orders.getItems().get(1);
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

  @Test
  public void testTradeHistoryUnmarshal() throws IOException {

    BiboxSingleResponse<BiboxOrders> response =
        BiboxTestUtils.getResponse(new TypeReference<BiboxSingleResponse<BiboxOrders>>() {},
            "/trade/example-trade-history.json");
    assertThat(response.get().getCmd()).isEqualTo("orderpending/orderHistoryList");

    BiboxOrders orders = response.get().getResult();
    assertThat(orders.getItems()).hasSize(2);

    BiboxOrder first = orders.getItems().get(0);
    assertThat(first.getId()).isEqualTo(255925252L);
    assertThat(first.getCreatedAt()).isEqualTo(1518127133000L);
    assertThat(first.getAccountType()).isEqualTo(BiboxAccountType.REGULAR);
    assertThat(first.getCoinSymbol()).isEqualTo("ETC");
    assertThat(first.getCurrencySymbol()).isEqualTo("ETH");
    assertThat(first.getOrderSide()).isEqualTo(BiboxOrderSide.ASK);
    assertThat(first.getOrderType()).isEqualTo(BiboxOrderType.LIMIT_ORDER);
    assertThat(first.getPrice()).isEqualTo(new BigDecimal("0.02641939"));
    assertThat(first.getAmount()).isEqualTo(new BigDecimal("0.0474"));
    assertThat(first.getMoney()).isEqualTo(new BigDecimal("0.00125227"));
    assertThat(first.getFee()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(first.getFeeSymbol()).isEqualTo("BIX");

    BiboxOrder second = orders.getItems().get(1);
    assertThat(second.getId()).isEqualTo(255924172L);
    assertThat(second.getCreatedAt()).isEqualTo(1518127118000L);
    assertThat(second.getAccountType()).isEqualTo(BiboxAccountType.REGULAR);
    assertThat(second.getCoinSymbol()).isEqualTo("ETC");
    assertThat(second.getCurrencySymbol()).isEqualTo("ETH");
    assertThat(second.getOrderSide()).isEqualTo(BiboxOrderSide.ASK);
    assertThat(second.getOrderType()).isEqualTo(BiboxOrderType.LIMIT_ORDER);
    assertThat(second.getPrice()).isEqualTo(new BigDecimal("0.02691475"));
    assertThat(second.getAmount()).isEqualTo(new BigDecimal("0.0542"));
    assertThat(second.getMoney()).isEqualTo(new BigDecimal("0.00145877"));
    assertThat(second.getFee()).isEqualTo(new BigDecimal("0.00000146"));
    assertThat(second.getFeeSymbol()).isEqualTo("ETH");
  }
}
