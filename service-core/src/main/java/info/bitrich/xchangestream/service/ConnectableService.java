package info.bitrich.xchangestream.service;

import io.reactivex.Completable;

/**
 * Base class of streaming services, declares connect() method including before connection logic
 */
public abstract class ConnectableService {

    /**
     * Exchange specific parameter is used for providing {@link Runnable} action which is caused before setup new connection.
     * For example adding throttle control for limiting too often opening connections:
     * <pre>
     * {@code
     * static final TimedSemaphore limiter = new TimedSemaphore(1, MINUTES, 15);
     * ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
     * spec.setExchangeSpecificParameters(ImmutableMap.of(
     *   {@link ConnectableService#BEFORE_CONNECTION_HANDLER}, () -> limiter.acquire()
     * ));
     * }
     * </pre>
     *
     * @see #BEFORE_API_CALL_HANDLER for a similar hook for stream-initiated API calls.
     */
    public static final String BEFORE_CONNECTION_HANDLER = "Before_Connection_Event_Handler";

    /**
     * Exchange-specific parameter used for providing a {@link Runnable} action to be run
     * prior to any API calls initiated during the course of maintaining a streamed connection.
     *
     * <p>While some exchanges allow bidirectional streamed communication, such that a socket
     * can be opened and then authenticated, others perform authentication by means of a
     * separate API call which can count towards API rate limits.  In addition, many exchanges
     * require API calls to obtain initial snapshots for streamed data such as order books
     * or account updates. XChange requires that the developer manage rate limits themselves,
     * but this is not possible when xchange-stream has to initiate these API calls
     * automatically. This parameter provides a means for the developer to get a callback
     * prior to these API calls, principally to apply such rate limiting globally.  However,
     * in principle there are wider potential uses.</p>
     *
     * @see #BEFORE_CONNECTION_HANDLER which provides the same sort of hook for socket
     * reconnections. This also includes example usage.
     */
    public static final String BEFORE_API_CALL_HANDLER = "Before_API_Call_Event_Handler";

    /**
     * {@link Runnable} handler is called before opening new socket connection.
     */
    private Runnable beforeConnectionHandler = () -> {
    };

    public void setBeforeConnectionHandler(Runnable beforeConnectionHandler) {
        if (beforeConnectionHandler != null) {
            this.beforeConnectionHandler = beforeConnectionHandler;
        }
    }

    protected abstract Completable openConnection();

    public Completable connect() {
        beforeConnectionHandler.run();
        return openConnection();
    }


}
