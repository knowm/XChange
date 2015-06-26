package com.xeiam.xchange.utils;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Retries {

	private static final Logger log = LoggerFactory.getLogger(Retries.class);

	/**
	 * Allows a client to attempt a call and retry a finite amount of times if
	 * the exception thrown is the right kind. The retries back off
	 * exponentially. For examples: @see
	 * {@link com.xeiam.xchange.examples.core.utils.RetriesDemo}
	 * @author Matija Mazi and Bryan Hernandez
	 * 
	 * @param nAttempts
	 *            Number of attempts before giving up
	 * @param initialRetrySec
	 *            Number of seconds to wait before trying again on the first
	 *            retry.
	 * @param action
	 *            A callable or lambda expression that contains the code that
	 *            will be tried and retried, if necessary.
	 * @param retryableException
	 *            A Predicate that will be used to check if the exception caught
	 *            is retryable, which can be any complex criteria that the user
	 *            defines.  public boolean test(Exception e) must be overriden.
	 * @return
	 * @throws Exception
	 *             If the exception isn't retryable, it's immediately thrown
	 *             again. If it is retryable, then a RunTimeException is thrown
	 *             after the allowed number of retries is exhausted.
	 */
	public static <V> V callWithRetries(int nAttempts, int initialRetrySec, Callable<V> action,
			Predicate<Exception> retryableException) throws Exception {
		int retryDelaySec = initialRetrySec;
		for (int attemptsLeftAfterThis = nAttempts - 1; attemptsLeftAfterThis >= 0; attemptsLeftAfterThis--) {
			try {
				return action.call();
			} catch (Exception e) {
				if (!retryableException.test(e)) {
					throw e;
				}
				if (attemptsLeftAfterThis <= 0) {
					throw new RuntimeException("Ultimately failed after " + nAttempts + " attempts.", e);
				}
				log.warn("Failed; {} attempts left: {}", e.toString(), attemptsLeftAfterThis);
			}
			retryDelaySec = pauseAndIncrease(retryDelaySec);
		}
		throw new RuntimeException("Failed; total attempts allowed: " + nAttempts);
	}

	private static int pauseAndIncrease(int retryDelaySec) {
		pause(retryDelaySec);
		retryDelaySec = Math.min(retryDelaySec * 2 - (int) (retryDelaySec * 0.75), 3600);
		return retryDelaySec;
	}

	public static void pause(int sec) {
		try {
			log.debug("Pausing for {} sec", sec);
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}