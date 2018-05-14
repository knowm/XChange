package info.bitrich.xchangestream.bitmex;

public class BitmexTestStreamingExchange extends BitmexStreamingExchange {

    private static final String API_URI = "wss://testnet.bitmex.com/realtime";

    public BitmexTestStreamingExchange() {
        super(new BitmexStreamingService(API_URI));
    }
}
