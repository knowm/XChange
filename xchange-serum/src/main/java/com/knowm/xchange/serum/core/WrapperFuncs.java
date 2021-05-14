package com.knowm.xchange.serum.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrapperFuncs {

    protected static final Logger logger = LoggerFactory.getLogger(WrapperFuncs.class);

    /**
     * Method is a bit of a hack, we simply keep trying to process some function until we
     * succeed. Can optionally be configured to have a maximum number of retry attempts.
     *
     * @param callback to attempt
     * @param retryInterval how often we should retry
     *
     * @return a value if expected
     */
    public static  <T> T runUntilSuccess(final Callback<T> callback, int retryInterval, int retryMaximum) {
        int retries = 0;
        while (true) {
            try {
                return callback.callback();
            } catch (Exception e) {
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e1) {
                    logger.error("Interrupted exception: {}", e.getMessage());
                }
                logger.debug("Issue processing: {} exception {}", e.getMessage(), e.getClass());
                retries++;
                if (retries > retryMaximum) {
                    logger.warn("Exceeded maximum retry attempts");
                    logger.error("Issue processing: {} exception {}", e.getMessage(), e.getClass());
                    return null;
                }
            }
        }
    }


    public interface Callback<T> {

        public T callback() throws Exception;
    }
}
