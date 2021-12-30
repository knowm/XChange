package org.knowm.xchange.binance.futures;

import org.junit.Test;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;
import org.knowm.xchange.dto.account.OpenPosition;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.binance.futures.BinanceFuturesAdapter.adaptPositionType;

public class BinanceFuturesAdapterTest {

    @Test
    public void testAdaptPositionType1() {
        assertThat(adaptPositionType(PositionSide.LONG, BigDecimal.ONE)).isEqualTo(OpenPosition.Type.LONG);
    }
    @Test
    public void testAdaptPositionType2() {
        assertThat(adaptPositionType(PositionSide.SHORT, BigDecimal.ONE)).isEqualTo(OpenPosition.Type.SHORT);
    }
    @Test
    public void testAdaptPositionType3() {
        assertThat(adaptPositionType(PositionSide.BOTH, BigDecimal.ONE)).isEqualTo(OpenPosition.Type.LONG);
    }
    @Test
    public void testAdaptPositionType4() {
        assertThat(adaptPositionType(PositionSide.BOTH, BigDecimal.ONE.negate())).isEqualTo(OpenPosition.Type.SHORT);
    }
    @Test
    public void testAdaptPositionType5() {
        //noinspection ConstantConditions
        assertThat(adaptPositionType(null, null)).isNull();
    }
}