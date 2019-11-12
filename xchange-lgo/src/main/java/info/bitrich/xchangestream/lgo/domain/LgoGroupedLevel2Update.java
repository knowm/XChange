package info.bitrich.xchangestream.lgo.domain;

import info.bitrich.xchangestream.lgo.dto.LgoLevel2Data;

import java.math.BigDecimal;
import java.util.*;

public class LgoGroupedLevel2Update {

    private final SortedMap<BigDecimal, BigDecimal> bidSide = new TreeMap<>(Collections.reverseOrder());

    private final SortedMap<BigDecimal, BigDecimal> askSide = new TreeMap<>();
    private long lastBatchId;

    public void applySnapshot(long batchId, LgoLevel2Data data) {
        bidSide.clear();
        askSide.clear();
        applyUpdate(batchId, data);
    }

    public void applyUpdate(long batchId, LgoLevel2Data data) {
        lastBatchId = batchId;
        updateL2Book(data);
    }

    private void updateL2Book(LgoLevel2Data data) {
        data.getAsks().forEach(ask -> updateSide(askSide, ask));
        data.getBids().forEach(bid -> updateSide(bidSide, bid));
    }

    private void updateSide(SortedMap<BigDecimal, BigDecimal> side, List<String> value) {
        BigDecimal price = new BigDecimal(value.get(0));
        String quantity = value.get(1);
        if (isZero(quantity)) {
            side.remove(price);
            return;
        }
        BigDecimal qtt = new BigDecimal(quantity);
        side.put(price, side.getOrDefault(price, BigDecimal.ZERO).add(qtt));
    }

    private boolean isZero(String quantity) {
        return quantity.replaceAll("[\\.0]", "").isEmpty();
    }

    public long getLastBatchId() {
        return lastBatchId;
    }

    public SortedMap<BigDecimal, BigDecimal> getAskSide() {
        return askSide;
    }

    public SortedMap<BigDecimal, BigDecimal> getBidSide() {
        return bidSide;
    }
}
