package nostro.xchange.binance;

import nostro.xchange.binance.utils.NostroBinanceFuturesDtoUtils;
import nostro.xchange.persistence.BalanceEntity;
import nostro.xchange.utils.NostroUtils;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesPosition;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.OpenPosition;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class NostroBinanceUtilsTest {
    @Test
    public void testIsZeroPosition() {
        OpenPosition position = new OpenPosition.Builder().price(new BigDecimal(0)).size(new BigDecimal(0)).build();
        assertThat(NostroBinanceUtils.isZeroPosition(position)).isTrue();
    }

    @Test
    public void testIsZeroPosition2() {
        OpenPosition position = new OpenPosition.Builder().price(new BigDecimal(0)).size(new BigDecimal(10)).build();
        assertThat(NostroBinanceUtils.isZeroPosition(position)).isFalse();
    }

    @Test
    public void testIsZeroPosition3() {
        OpenPosition position = new OpenPosition.Builder().price(new BigDecimal(10)).size(new BigDecimal(0)).build();
        assertThat(NostroBinanceUtils.isZeroPosition(position)).isFalse();
    }

    @Test
    public void testIsZeroPosition4() {
        OpenPosition position = new OpenPosition.Builder().price(new BigDecimal(10)).size(new BigDecimal(10)).build();
        assertThat(NostroBinanceUtils.isZeroPosition(position)).isFalse();
    }

    @Test
    public void testPositionUpdateRequired() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BinanceFuturesPosition fp1 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime(), PositionSide.LONG);
        OpenPosition p1 = BinanceFuturesAdapter.adaptPosition(fp1);
        BalanceEntity e1 = NostroBinanceUtils.toEntity(p1);

        BinanceFuturesPosition fp2 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime(), PositionSide.LONG);
        OpenPosition p2 = BinanceFuturesAdapter.adaptPosition(fp2);
        assertThat(NostroBinanceUtils.updateRequired(e1, p2)).isFalse(); // positions have equal timestamp
    }

    @Test
    public void testPositionUpdateRequired1() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BinanceFuturesPosition fp1 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime(), PositionSide.LONG);
        OpenPosition p1 = BinanceFuturesAdapter.adaptPosition(fp1);
        BalanceEntity e1 = NostroBinanceUtils.toEntity(p1);

        BinanceFuturesPosition fp2 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime() - 10, PositionSide.LONG);
        OpenPosition p2 = BinanceFuturesAdapter.adaptPosition(fp2);
        assertThat(NostroBinanceUtils.updateRequired(e1, p2)).isFalse(); // position is older
    }

    @Test
    public void testPositionUpdateRequired2() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BinanceFuturesPosition fp1 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime(), PositionSide.LONG);
        OpenPosition p1 = BinanceFuturesAdapter.adaptPosition(fp1);
        BalanceEntity e1 = NostroBinanceUtils.toEntity(p1);

        BinanceFuturesPosition fp2 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime() + 10, PositionSide.LONG);
        OpenPosition p2 = BinanceFuturesAdapter.adaptPosition(fp2);
        assertThat(NostroBinanceUtils.updateRequired(e1, p2)).isFalse(); // position 2 is newer but control values are equal
    }

    @Test
    public void testPositionUpdateRequired3() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BinanceFuturesPosition fp1 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime(), PositionSide.LONG);
        OpenPosition p1 = BinanceFuturesAdapter.adaptPosition(fp1);
        BalanceEntity e1 = NostroBinanceUtils.toEntity(p1);

        BinanceFuturesPosition fp2 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(15), new BigDecimal(1), updateTime.getTime() + 10, PositionSide.LONG);
        OpenPosition p2 = BinanceFuturesAdapter.adaptPosition(fp2);
        assertThat(NostroBinanceUtils.updateRequired(e1, p2)).isTrue(); // entry price not equal and should be updated
    }

    @Test
    public void testPositionUpdateRequired4() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BinanceFuturesPosition fp1 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime(), PositionSide.LONG);
        OpenPosition p1 = BinanceFuturesAdapter.adaptPosition(fp1);
        BalanceEntity e1 = NostroBinanceUtils.toEntity(p1);

        BinanceFuturesPosition fp2 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal("1.5"), updateTime.getTime() + 10, PositionSide.LONG);
        OpenPosition p2 = BinanceFuturesAdapter.adaptPosition(fp2);
        assertThat(NostroBinanceUtils.updateRequired(e1, p2)).isTrue(); // position amount not equal and should be updated
    }

    @Test
    public void testPositionUpdateRequired5() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BinanceFuturesPosition fp1 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime(), PositionSide.LONG);
        OpenPosition p1 = BinanceFuturesAdapter.adaptPosition(fp1);
        BalanceEntity e1 = NostroBinanceUtils.toEntity(p1);

        BinanceFuturesPosition fp2 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(0), new BigDecimal("0.0"), updateTime.getTime() + 10, PositionSide.SHORT);
        OpenPosition p2 = BinanceFuturesAdapter.adaptPosition(fp2);
        assertThat(NostroBinanceUtils.updateRequired(e1, p2)).isFalse(); // despite size/price difference - position types is not equal
    }

    @Test
    public void testPositionToBalanceEntity() throws Exception {
        Timestamp updateTime = new Timestamp(new Date().getTime());
        BinanceFuturesPosition fp1 = NostroBinanceFuturesDtoUtils.generatePosition(BinanceAdapters.toSymbol(CurrencyPair.BTC_USDT), 1, new BigDecimal(10), new BigDecimal(1), updateTime.getTime(), PositionSide.LONG);
        OpenPosition p1 = BinanceFuturesAdapter.adaptPosition(fp1);
        BalanceEntity e1 = NostroBinanceUtils.toEntity(p1);

        assertThat(e1.getTimestamp()).isEqualTo(updateTime);
        assertThat(e1.isZero()).isFalse();
        assertThat(e1.getAsset()).isEqualTo("BTC/USDT/perpetual");

        OpenPosition positionFromDocument = NostroUtils.readPositionDocument(e1.getDocument());
        assertThat(positionFromDocument).isEqualTo(p1);
    }
}