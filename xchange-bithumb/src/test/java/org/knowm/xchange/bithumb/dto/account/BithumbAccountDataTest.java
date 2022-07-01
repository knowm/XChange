package org.knowm.xchange.bithumb.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.dto.BithumbResponse;

public class BithumbAccountDataTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testUnmarshallBalance() throws IOException {

    final InputStream is =
        BithumbAccountDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/account/example-balance.json");
    final BithumbBalance bithumbBalance = mapper.readValue(is, BithumbBalance.class);

    assertThat(bithumbBalance.getTotalKrw()).isEqualTo(BigDecimal.valueOf(1));
    assertThat(bithumbBalance.getInUseKrw()).isEqualTo(BigDecimal.valueOf(0));
    assertThat(bithumbBalance.getAvailableKrw()).isEqualTo(BigDecimal.valueOf(1));
  }

  @Test
  public void testUnmarshallOrder() throws IOException {

    final InputStream is =
        BithumbAccountDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/account/example-order.json");
    final BithumbResponse<List<BithumbOrder>> bithumbOrderResponse =
        mapper.readValue(is, new TypeReference<BithumbResponse<List<BithumbOrder>>>() {});

    assertThat(bithumbOrderResponse.getStatus()).isEqualTo("0000");

    BithumbOrder order = bithumbOrderResponse.getData().stream().findFirst().get();
    assertThat(order.getOrderId()).isEqualTo("C0101000007408440032");
    assertThat(order.getOrderDate()).isEqualTo(1571728739360570L);
    assertThat(order.getOrderCurrency()).isEqualTo("BTC");
    assertThat(order.getPaymentCurrency()).isEqualTo("KRW");
    assertThat(order.getType()).isEqualTo(BithumbAdapters.OrderType.bid);
    assertThat(order.getUnits()).isEqualTo(BigDecimal.valueOf(5.0));
    assertThat(order.getUnitsRemaining()).isEqualTo(BigDecimal.valueOf(5.0));
    assertThat(order.getPrice()).isEqualTo(BigDecimal.valueOf(501000));
  }

  @Test
  public void testUnmarshallOrderDetail() throws IOException {

    final InputStream is =
        BithumbAccountDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/account/example-order-detail.json");
    final BithumbResponse<List<BithumbOrderDetail>> bithumbOrderDetailResponse =
        mapper.readValue(is, new TypeReference<BithumbResponse<List<BithumbOrderDetail>>>() {});

    assertThat(bithumbOrderDetailResponse.getStatus().equals("0000"));
    BithumbOrderDetail detail = bithumbOrderDetailResponse.getData().get(0);

    assertThat(detail.getOrderDate()).isEqualTo(1572497603668315L);
    assertThat(detail.getType()).isEqualTo(BithumbAdapters.OrderType.bid);
    assertThat(detail.getOrderStatus()).isEqualTo("Completed");
    assertThat(detail.getOrderCurrency()).isEqualTo("BTC");
    assertThat(detail.getPaymentCurrency()).isEqualTo("KRW");
    assertThat(detail.getOrderPrice()).isEqualTo(BigDecimal.valueOf(8601000));
    assertThat(detail.getOrderQty()).isEqualTo(BigDecimal.valueOf(0.007));

    assertThat(detail.getContract().size() == 2);
    BithumbOrderDetail.Contract contract = detail.getContract().get(0);

    assertThat(contract.getTransactionDate()).isEqualTo(1572497603902030L);
    assertThat(contract.getPrice()).isEqualTo(BigDecimal.valueOf(8601000));
    assertThat(contract.getUnits()).isEqualTo(BigDecimal.valueOf(0.005));
    assertThat(contract.getFeeCurrency()).isEqualTo("KRW");
    assertThat(contract.getFee()).isEqualTo(BigDecimal.valueOf(107.51));
    assertThat(contract.getTotal()).isEqualTo(BigDecimal.valueOf(43005));
  }

  @Test
  public void testUnmarshallAccount() throws IOException {

    final InputStream is =
        BithumbAccountDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/account/example-account.json");
    final BithumbAccount bithumbTransaction = mapper.readValue(is, BithumbAccount.class);

    assertThat(bithumbTransaction.getCreated()).isEqualTo(1489545326000L);
    assertThat(bithumbTransaction.getAccountId()).isEqualTo("ABCDE");
    assertThat(bithumbTransaction.getTradeFee()).isEqualTo(BigDecimal.valueOf(0.0015));
    assertThat(bithumbTransaction.getBalance()).isEqualTo(BigDecimal.valueOf(0.00001971));
  }

  @Test
  public void testUnmarshallWalletAddress() throws IOException {

    final InputStream is =
        BithumbAccountDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/account/example-wallet-address.json");
    final BithumbWalletAddress bithumbWalletAddress =
        mapper.readValue(is, BithumbWalletAddress.class);

    assertThat(bithumbWalletAddress.getCurrency()).isEqualTo("BTC");
    assertThat(bithumbWalletAddress.getWalletAddress())
        .isEqualTo("1H7WL8Lb8mxCTwpL1RN8yckL2gcPLgqtqD");
  }
}
