package info.bitrich.xchangestream.bankera;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bankera.BankeraExchange;

public class BankeraStreamingExchange extends BankeraExchange implements StreamingExchange {

	private static final String WS_URI = "wss://api-dev.bankera.com/ws";
	private BankeraStreamingService streamingService;
	private BankeraStreamingMarketDataService streamingMarketDataService;

	public BankeraStreamingExchange() {}

	@Override
	protected void initServices() {
		super.initServices();
	}

	private BankeraStreamingService createStreamingService() {
		streamingService = new BankeraStreamingService(WS_URI);
		applyStreamingSpecification(getExchangeSpecification(), streamingService);
		return streamingService;
	}

	@Override
	public Completable connect(ProductSubscription... args) {
		return streamingService.connect();
	}

	@Override
	public Completable disconnect() {
		return streamingService.disconnect();
	}

	@Override
	public boolean isAlive() {
		return streamingService.isSocketOpen();
	}

	@Override
	public Observable<Throwable> reconnectFailure() {
		return streamingService.subscribeReconnectFailure();
	}

	@Override
	public Observable<Object> connectionSuccess() {
		return streamingService.subscribeConnectionSuccess();
	}

	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {
		ExchangeSpecification spec = super.getDefaultExchangeSpecification();
		spec.setShouldLoadRemoteMetaData(false);

		return spec;
	}

	@Override
	public StreamingMarketDataService getStreamingMarketDataService() {
		return streamingMarketDataService;
	}

	@Override
	public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }

}
