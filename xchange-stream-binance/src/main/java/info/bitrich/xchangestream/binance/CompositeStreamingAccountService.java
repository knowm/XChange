package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.core.StreamingAccountService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositeStreamingAccountService implements StreamingAccountService {
    private final List<StreamingAccountService> accountServices;

    public CompositeStreamingAccountService(StreamingAccountService... accountServices) {
        this.accountServices = Stream.of(accountServices).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
        return Observable.merge(accountServices.stream().map(s -> s.getBalanceChanges(currency, args))
                .collect(Collectors.toList()));
    }
}
