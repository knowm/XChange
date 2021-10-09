package nostro.xchange.utils;

import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.PublishProcessor;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.UserTrade;

public class NostroStreamingPublisher implements StreamingTradeService, StreamingAccountService {

    private final PublishProcessor<Order> orderPublisher = PublishProcessor.create();
    private final PublishProcessor<UserTrade> tradePublisher = PublishProcessor.create();
    private final PublishProcessor<Balance> balancePublisher = PublishProcessor.create();
    
    public void publish(Order order) {
        orderPublisher.onNext(order);
    }

    public void publish(UserTrade trade) {
        tradePublisher.onNext(trade);
    }

    public void publish(Balance balance) {
        balancePublisher.onNext(balance);
    }

    @Override
    public Flowable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
        return orderPublisher;
    }

    @Override
    public Flowable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
        return tradePublisher;
    }

    @Override
    public Flowable<Balance> getBalanceChanges(Currency currency, Object... args) {
        return balancePublisher;
    }
}
