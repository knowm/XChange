package com.xeiam.xchange.atlasats;

import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;

public class AtlasStreamingConfiguration implements
		ExchangeStreamingConfiguration {

	public static final AtlasStreamingConfiguration DEFAULT = new AtlasStreamingConfiguration(
			10, 10000, 60000, false, true);

	private int maxReconnectAttempts;
	private int reconnectWaitTimeInMs;
	private int timeoutInMs;

	private boolean encryptedChannel;

	private boolean keepAlive;

	public AtlasStreamingConfiguration(int maxReconnectAttempts,
			int reconnectWaitTimeInMs, int timeoutInMs,
			boolean encryptedChannel, boolean keepAlive) {
		super();
		this.maxReconnectAttempts = maxReconnectAttempts;
		this.reconnectWaitTimeInMs = reconnectWaitTimeInMs;
		this.timeoutInMs = timeoutInMs;
		this.encryptedChannel = encryptedChannel;
		this.keepAlive = keepAlive;
	}

	@Override
	public int getMaxReconnectAttempts() {
		return maxReconnectAttempts;
	}

	@Override
	public int getReconnectWaitTimeInMs() {
		return reconnectWaitTimeInMs;
	}

	@Override
	public int getTimeoutInMs() {
		return timeoutInMs;
	}

	@Override
	public boolean isEncryptedChannel() {
		return encryptedChannel;
	}

	@Override
	public boolean keepAlive() {
		return keepAlive;
	}

}
