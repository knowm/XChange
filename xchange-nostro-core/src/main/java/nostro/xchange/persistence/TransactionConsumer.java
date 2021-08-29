package nostro.xchange.persistence;

@FunctionalInterface
public interface TransactionConsumer {
    void accept(Transaction tx) throws Exception;
}
