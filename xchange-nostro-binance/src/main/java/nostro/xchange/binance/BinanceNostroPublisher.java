package nostro.xchange.binance;

import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.PublishProcessor;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

public class BinanceNostroPublisher implements StreamingTradeService {

    private final PublishProcessor<Order> orderPublisher = PublishProcessor.create();
    private final PublishProcessor<UserTrade> tradePublisher = PublishProcessor.create();
    
    public void publish(Order order) {
        orderPublisher.onNext(order);
    }

    public void publish(UserTrade trade) {
        tradePublisher.onNext(trade);
    }

    @Override
    public Flowable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        return orderPublisher;
    }

    @Override
    public Flowable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        return tradePublisher;
    }
}
