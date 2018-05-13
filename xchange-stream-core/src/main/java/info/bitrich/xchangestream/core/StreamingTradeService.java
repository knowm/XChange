package info.bitrich.xchangestream.core;

import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;

public interface StreamingTradeService {

    Observable<Order> getOxrders(CurrencyPair var1, Object... var2);

}
