package info.bitrich.xchangestream.serum.datamapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.knowm.xchange.serum.core.Market;
import com.knowm.xchange.serum.structures.EventQueueLayout.Header;
import com.knowm.xchange.serum.structures.EventQueueLayout.EventNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.knowm.xchange.serum.structures.EventQueueLayout.HeaderLayout;
import static com.knowm.xchange.serum.structures.EventQueueLayout.NodeLayout;
import static com.knowm.xchange.serum.structures.EventQueueLayout.HEADER_LEN;
import static com.knowm.xchange.serum.structures.EventQueueLayout.NODE_LEN;

public class EventQueueDataMapper extends DataMapper {

    private long lastSeqNum = -1;

    public EventQueueDataMapper(final String symbol,
                                final Market market,
                                int priceDecimalPlaces,
                                int sizeDecimalPlaces) {
        super(symbol, market, priceDecimalPlaces, sizeDecimalPlaces);
    }

    /**
     * We're interested only in newly added events since last update each account update
     * publishes 'snapshot' not 'delta' so we need to figure the delta
     */
    @Override
    public Stream<JsonNode> map(byte[] bytes, long slot, long timestamp) throws IOException {
        try {
            final Stream<EventNode> events = decodeEventQueue(bytes);
            if (events == null) {
                throw new NullPointerException("Issue with events");
            }
            final List<String> fillIds = new ArrayList<>();
            return events
                    .map(e -> normalizeData(e, timestamp, slot, fillIds))
                    .filter(Objects::nonNull)
                    .peek(m -> {
                        if (m.has("type") && "fill".equals(m.get("type").asText()))
                            fillIds.add(m.get("orderId").asText());
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BigDecimal getFillPrice(final EventNode event, int decimalPlaces) {
        final BigDecimal nativeQuantity = new BigDecimal(String.valueOf(
                event.eventFlags.bid
                        ? event.nativeQuantityPaid
                        : event.nativeQuantityReleased));

        final BigDecimal nativeFeeOrRebate = new BigDecimal(String.valueOf(event.nativeFeeOrRebate));
        final BigDecimal priceBeforeFees = event.eventFlags.maker
                ? nativeQuantity.subtract(nativeFeeOrRebate)
                : nativeQuantity.add(nativeFeeOrRebate);

        return new BigDecimal(String.valueOf(this.market.divideBNToNumber(
                priceBeforeFees.multiply(this.market.baseSplTokenMultiplier()),
                this.market.baseSplTokenMultiplier().multiply(new BigDecimal(String.valueOf(
                        event.eventFlags.bid
                                ? event.nativeQuantityReleased
                                : event.nativeQuantityPaid))))))
                .setScale(decimalPlaces, RoundingMode.HALF_UP);
    }

    public BigDecimal getFillSize(final EventNode event, int decimalPlaces) {
        return new BigDecimal(String.valueOf(this.market.divideBNToNumber(
                new BigDecimal(String.valueOf(event.eventFlags.bid ? event.nativeQuantityReleased : event.nativeQuantityPaid)),
                this.market.baseSplTokenMultiplier())))
                .setScale(decimalPlaces, RoundingMode.HALF_UP);
    }

    public JsonNode normalizeData(final EventNode event,
                                  final long timestamp,
                                  final long slot,
                                  final List<String> fillIds) {
        final String side = event.eventFlags.bid ? "buy" : "sell";
        if (event.eventFlags.fill) {
            final ObjectNode fill = JsonNodeFactory.instance.objectNode();
            fill.put("type", "fill");
            fill.put("symbol", symbol);
            fill.put("market", this.market.decoded.getOwnAddress().getKeyString());
            fill.put("timestamp", timestamp);
            fill.put("slot", slot);
            fill.put("orderId", event.orderId);
            fill.put("clientId", event.clientOrderId);
            fill.put("side", side);
            fill.put("price", getFillPrice(event, priceDecimalPlaces).toPlainString());
            fill.put("size", getFillSize(event, sizeDecimalPlaces).toPlainString());
            fill.put("maker", event.eventFlags.maker);
            fill.put("feeCost", this.market.quoteSplTokenMultiplier().toPlainString());
            fill.put("openOrders", event.openOrders.getKeyString());
            fill.put("openOrdersSlot", event.openOrdersSlot);
            fill.put("feeTier", event.feeTier);
            return fill;
        } else if (Double.compare(event.nativeQuantityPaid, 0.0) == 0) {
            // we can use nativeQuantityStillLocked == 0 to detect if order is 'done'
            // this is what the dex uses at event processing time to decide if it can
            // release the slot in an OpenOrders account.
            //
            // done means that there won't be any more messages for the order
            // (is no longer in the order book or never was - cancelled, ioc)

            final ObjectNode done = JsonNodeFactory.instance.objectNode();
            done.put("type", "done");
            done.put("symbol", symbol);
            done.put("market", this.market.decoded.getOwnAddress().getKeyString());
            done.put("timestamp", timestamp);
            done.put("slot", slot);
            done.put("orderId", event.orderId);
            done.put("clientId", event.clientOrderId);
            done.put("side", side);
            done.put("reason", fillIds.contains(event.orderId) ? "filled" : "cancelled");
            done.put("maker", event.eventFlags.maker);
            done.put("openOrders", event.openOrders.getKeyString());
            done.put("openOrdersSlot", event.openOrdersSlot);
            done.put("feeTier", event.feeTier);
            return done;
        }
        return null;
    }


    /**
     *  Modified version of https://github.com/project-serum/serum-js/blob/master/src/queue.ts#L87
     *  which allows for lazy iteration of data and exiting if we do not need to process it all
     * */
    private Stream<EventNode> decodeEventQueue(final byte[] bytes) throws IOException {
        Stream<EventNode> stream = Stream.empty();
        try {
            final Header header = HeaderLayout.DECODER.decode(
                    Arrays.copyOfRange(bytes, 0, HEADER_LEN));

            if (this.lastSeqNum != -1) {
                long newEventsCount = header.seqNum - this.lastSeqNum;
                stream = IntStream.range(0, (int)newEventsCount).mapToObj(i -> {
                    int startIndex = HEADER_LEN + i*NODE_LEN;
                    try {
                        return NodeLayout.DECODER.decode(
                                Arrays.copyOfRange(bytes, startIndex, startIndex + NODE_LEN));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull);
            }
            this.lastSeqNum = header.seqNum;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }
}
