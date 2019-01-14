package info.bitrich.xchangestream.bitfinex;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Bitfinex does not allow opening more {@value BFX_CON_RATE_LIMIT_DEFAULT} socket connections per minute.
 * If limit reached then the following exception will be is raised: <br/>
 * <br/>WebSocket Client failed to connect. Invalid handshake response getStatus: 429 Too Many Requests
 * <br/><br/>This class implements throttle control for limiting too often opening socket connections
 */
public class BitfinexConnectionRateLimiter {

    private static final Logger LOG = LoggerFactory.getLogger(BitfinexConnectionRateLimiter.class);

    public static final String BFX_CON_RATE_LIMIT = "bfxConRateLimit";
    private static final Integer BFX_CON_RATE_LIMIT_DEFAULT = 15;

    private static final TimedSemaphore timedSemaphore = new TimedSemaphore(1, TimeUnit.MINUTES, readLimitBfxConectionProperty());

    /**
     * Return number of allowed connections per minute for Bitfinex Socket API.
     * Default value is {@value BFX_CON_RATE_LIMIT_DEFAULT}, but it can be overridden by system property {@value BFX_CON_RATE_LIMIT}
     * @return
     */
    private static int readLimitBfxConectionProperty() {

        String limitValue = System.getProperty(BFX_CON_RATE_LIMIT);
        if (limitValue != null) {
            if (StringUtils.isNumeric(limitValue)) {
                return Integer.parseInt(limitValue);
            } else {
                LOG.warn("Invalid value of '{}' property: {}. It is ignored. Default value is used: {}", BFX_CON_RATE_LIMIT, limitValue, BFX_CON_RATE_LIMIT_DEFAULT);
            }
        }

        return BFX_CON_RATE_LIMIT_DEFAULT;
    }

    public static void openConnectionRateLimit() {
        try {
            timedSemaphore.acquire();
        } catch (InterruptedException e) {
            // do nothing
        }
    }

}
