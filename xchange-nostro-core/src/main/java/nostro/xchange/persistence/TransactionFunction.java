package nostro.xchange.persistence;

@FunctionalInterface
public interface TransactionFunction<R> {
    R apply(Transaction tx) throws Exception;
}
