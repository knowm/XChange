package nostro.xchange.binance;

import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceCancelService {
    private static final Logger LOG = LoggerFactory.getLogger(BinanceCancelService.class);
    
    private static final String TYPE_CANCEL = "CANCEL";
    private static final String DOCUMENT_EMPTY = "{}";

    private final TransactionFactory txFactory;
    private final BinanceTradeService tradeService;

    public BinanceCancelService(TransactionFactory txFactory, BinanceTradeService tradeService) {
        this.txFactory = txFactory;
        this.tradeService = tradeService;
    }

    public boolean cancelOrder(String id) {
        try {
            return cancelTx(id);
        } catch (Throwable th) {
            if (shouldRetry(th)) {
                LOG.error("Error while cancelling order, submit cancel task", th);
                submitCancelTask(id);
                return true;
            } else {
                LOG.error("Error while cancelling order, no retry, marking rejected", th);
                //TODO: markRejected(id);
                return false;
            }
        }
    }
    
    public void markRejected(String id) {
        txFactory.execute(tx -> {
            OrderEntity e = tx.getOrderRepository().lockById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order with id=\"" + id + "\" not found"));
            
            Order order = NostroUtils.readOrderDocument(e.getDocument());
            order.setOrderStatus(Order.OrderStatus.REJECTED);
            String document = NostroUtils.writeOrderDocument(order);

            tx.getOrderRepository().updateById(id, document, true, e.getUpdated());
        });
    }

    private boolean shouldRetry(Throwable th) {
        // TODO: implement when retry is moved here
        return false;
    }

    private void submitCancelTask(String id) {
        LOG.info("Adding task to cancel order(id={})", id);
        txFactory.execute(tx -> tx.getOrderTaskRepository().insert(id, "CANCEL", false, DOCUMENT_EMPTY));
    }

    private boolean cancelTx(String id) {
        return txFactory.executeAndGet(tx -> {
            OrderEntity e = tx.getOrderRepository().lockById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order with id=\"" + id + "\" not found"));
            if (e.isTerminal()) {
                LOG.warn("Attempt to cancel order(id={}) in terminal state", e.getId());
                return false;
            }

            tradeService.cancelOrder(new CurrencyPair(e.getInstrument()), null, id, null);

            Order order = NostroUtils.readOrderDocument(e.getDocument());
            order.setOrderStatus(Order.OrderStatus.PENDING_CANCEL);
            String document = NostroUtils.writeOrderDocument(order);

            tx.getOrderRepository().updateById(id, document, false, e.getUpdated());

            tx.getOrderTaskRepository().update(id, TYPE_CANCEL, true, DOCUMENT_EMPTY);
            return true;
        });
    }
}
