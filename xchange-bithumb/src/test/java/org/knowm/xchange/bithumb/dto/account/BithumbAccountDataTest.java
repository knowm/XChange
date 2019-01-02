package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BithumbAccountDataTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUnmarshallBalance() throws IOException {

        final InputStream is = BithumbAccountDataTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/account/example-balance.json");
        final BithumbBalance bithumbBalance = mapper.readValue(is, BithumbBalance.class);

        System.out.println(bithumbBalance);
        assertThat(bithumbBalance.getTotalKrw()).isEqualTo(BigDecimal.valueOf(1));
        assertThat(bithumbBalance.getInUseKrw()).isEqualTo(BigDecimal.valueOf(0));
        assertThat(bithumbBalance.getAvailableKrw()).isEqualTo(BigDecimal.valueOf(1));
    }

    @Test
    public void testUnmarshallOrder() throws IOException {

        final InputStream is = BithumbAccountDataTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/account/example-order.json");
        final BithumbOrder bithumbOrder = mapper.readValue(is, BithumbOrder.class);

        System.out.println(bithumbOrder);
        assertThat(bithumbOrder.getOrderId()).isEqualTo(1412562509982L);
        assertThat(bithumbOrder.getOrderDate()).isEqualTo(1412562509982L);
        assertThat(bithumbOrder.getOrderCurrency()).isEqualTo("BTC");
        assertThat(bithumbOrder.getPaymentCurrency()).isEqualTo("KRW");
        assertThat(bithumbOrder.getType()).isEqualTo("bid");
        assertThat(bithumbOrder.getStatus()).isEqualTo("placed");
        assertThat(bithumbOrder.getUnits()).isEqualTo(BigDecimal.valueOf(5.0));
        assertThat(bithumbOrder.getUnitsRemaining()).isEqualTo(BigDecimal.valueOf(123));
        assertThat(bithumbOrder.getPrice()).isEqualTo(BigDecimal.valueOf(501000));
        assertThat(bithumbOrder.getFee()).isEqualTo(BigDecimal.valueOf(456));
        assertThat(bithumbOrder.getTotal()).isEqualTo(BigDecimal.valueOf(123));
        assertThat(bithumbOrder.getDateCompleted()).isEqualTo(1412562509982L);
    }

    @Test
    public void testUnmarshallOrderDetail() throws IOException {

        final InputStream is = BithumbAccountDataTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/account/example-order-detail.json");
        final BithumbOrderDetail bithumbOrderDetail = mapper.readValue(is, BithumbOrderDetail.class);

        System.out.println(bithumbOrderDetail);
        assertThat(bithumbOrderDetail.getTransactionDate()).isEqualTo(1428024598967L);
        assertThat(bithumbOrderDetail.getType()).isEqualTo("ask");
        assertThat(bithumbOrderDetail.getOrderCurrency()).isEqualTo("BTC");
        assertThat(bithumbOrderDetail.getPaymentCurrency()).isEqualTo("KRW");
        assertThat(bithumbOrderDetail.getUnitsTraded()).isEqualTo(BigDecimal.valueOf(0.0017D));
        assertThat(bithumbOrderDetail.getPrice()).isEqualTo(BigDecimal.valueOf(264000L));
        assertThat(bithumbOrderDetail.getFee()).isEqualTo(BigDecimal.valueOf(0.0000017D));
        assertThat(bithumbOrderDetail.getTotal()).isEqualTo(BigDecimal.valueOf(449L));
    }

    @Test
    public void testUnmarshallTransaction() throws IOException {

        final InputStream is = BithumbAccountDataTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/account/example-transaction.json");
        final BithumbTransaction bithumbTransaction = mapper.readValue(is, BithumbTransaction.class);

        System.out.println(bithumbTransaction);
        assertThat(bithumbTransaction.getSearch()).isEqualTo("2");
        assertThat(bithumbTransaction.getTransferDate()).isEqualTo(1417138805912L);
        assertThat(bithumbTransaction.getUnits()).isEqualTo("-0.1");
        assertThat(bithumbTransaction.getPrice()).isEqualTo(BigDecimal.valueOf(51600));
        assertThat(bithumbTransaction.getFee()).isEqualTo(BigDecimal.valueOf(0.24));
        assertThat(bithumbTransaction.getKrwRemain()).isEqualTo(BigDecimal.valueOf(305455680));
    }

    @Test
    public void testUnmarshallAccount() throws IOException {

        final InputStream is = BithumbAccountDataTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/account/example-account.json");
        final BithumbAccount bithumbTransaction = mapper.readValue(is, BithumbAccount.class);

        System.out.println(bithumbTransaction);
        assertThat(bithumbTransaction.getCreated()).isEqualTo(1489545326000L);
        assertThat(bithumbTransaction.getAccountId()).isEqualTo("ABCDE");
        assertThat(bithumbTransaction.getTradeFee()).isEqualTo(BigDecimal.valueOf(0.0015));
        assertThat(bithumbTransaction.getBalance()).isEqualTo(BigDecimal.valueOf(0.00001971));
    }
}
