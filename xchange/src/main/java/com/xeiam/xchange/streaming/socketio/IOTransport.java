/*
 * socket.io-java-client IOTransport.java
 *
 * Copyright (c) 2012, Enno Boland
 * socket.io-java-client is a implementation of the socket.io protocol in Java.
 * 
 * See LICENSE file for more information
 */
package com.xeiam.xchange.streaming.socketio;

import java.io.IOException;

/**
 * The Interface IOTransport.
 */
interface IOTransport {

	/**
	 * Instructs the IOTransport to connect.
	 */
	void connect();

	/**
	 * Instructs the IOTransport to disconnect.
	 */
	void disconnect();

	/**
	 * Instructs the IOTransport to send a Message
	 * 
	 * @param text
	 *            the text to be sent
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void send(String text) throws Exception;

	/**
	 * return true if the IOTransport prefers to send multiple messages at a
	 * time.
	 * 
	 * @return true, if successful
	 */
	boolean canSendBulk();

	/**
	 * Instructs the IOTransport to send multiple messages. This is only called
	 * when canSendBulk returns true.
	 * 
	 * @param texts
	 *            the texts
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void sendBulk(String[] texts) throws IOException;

	/**
	 * Instructs the IOTransport to invalidate. DO NOT DISCONNECT from the
	 * server. just make sure, that events are not populated to the
	 * {@link IOConnection}
	 */
	void invalidate();
	
	String getName();
}
