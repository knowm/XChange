package info.bitrich.xchangestream.core;

import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;



public interface StreamingTradeService {

    default Observable<Order> getOrders(CurrencyPair var1, Object... var2) {
        throw new NotYetImplementedForExchangeException();
    }

    default void submitOrder(Order order, CurrencyPair var1, Object...var2) {
        throw new NotYetImplementedForExchangeException();
    }
}
