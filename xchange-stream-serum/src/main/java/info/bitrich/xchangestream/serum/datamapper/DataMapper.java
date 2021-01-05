package info.bitrich.xchangestream.serum.datamapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.knowm.xchange.serum.core.Market;

import java.io.IOException;
import java.util.stream.Stream;

public abstract class DataMapper {

    protected final String symbol;
    protected final Market market;
    protected final int priceDecimalPlaces;
    protected final int sizeDecimalPlaces;

    public DataMapper(final String symbol,
                      final Market market,
                      int priceDecimalPlaces,
                      int sizeDecimalPlaces) {
        this.symbol = symbol;
        this.market = market;
        this.priceDecimalPlaces = priceDecimalPlaces;
        this.sizeDecimalPlaces = sizeDecimalPlaces;
    }

    abstract Stream<JsonNode> map(final byte[] bytes, final long slot, final long timestamp) throws IOException;
}
