package si.mazi.bitstampapi.model;

import java.util.List;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 6:51 PM
 */
public class OrderBook {
    private List<List<Double>> bids;
    private List<List<Double>> asks;

    /** (price, amount) */
    public List<List<Double>> getBids() {
        return bids;
    }

    public void setBids(List<List<Double>> bids) {
        this.bids = bids;
    }

    /** (price, amount) */
    public List<List<Double>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<Double>> asks) {
        this.asks = asks;
    }

    @Override
    public String toString() {
        return String.format("OrderBook{bids=%s, asks=%s}", bids, asks);
    }
}
