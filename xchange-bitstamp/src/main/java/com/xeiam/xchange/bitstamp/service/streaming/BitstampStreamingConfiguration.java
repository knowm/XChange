/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 * Copyright (C) 2014 Ryan Sundberg <ryan.sundberg@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.bitstamp.service.streaming;

import java.util.Set;
import java.util.HashSet;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;

/**
 * <p>
 * Value object to provide the following
 * </p>
 * <ul>
 * <li>Access to streaming data configuration specific to MtGox exchange streaming API</li>
 * </ul>
 */
public class BitstampStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final int maxReconnectAttempts;
  private final int reconnectWaitTimeInMs;
  private final int timeoutInMs;
  private final boolean isEncryptedChannel;
  private final Set<String> channels;	
  private PusherOptions pusherOpts;

  /**
   * Constructor
   * 
   * @param maxReconnectAttempts
   * @param reconnectWaitTimeInMs
   * @param timeoutInMs
   * @param isEncryptedChannel - should it use an encrypted channel or not? (ws vs. wss protocol)
   * @param channel - the specific data channel you want to tap into (https://mtgox.com/api/2/stream/list_public), null if none
   */
  public BitstampStreamingConfiguration(PusherOptions pusherOptions,
		  int maxReconnectAttempts, int reconnectWaitTimeInMs, int timeoutInMs, boolean isEncryptedChannel, Set<String> channels) {
    this.maxReconnectAttempts = maxReconnectAttempts;
    this.reconnectWaitTimeInMs = reconnectWaitTimeInMs;
    this.timeoutInMs = timeoutInMs;
    this.isEncryptedChannel = isEncryptedChannel;
    this.channels = channels;
	this.pusherOpts = pusherOptions;
  }
  
  public BitstampStreamingConfiguration() {
    this.maxReconnectAttempts = 30; // 67 min
    this.reconnectWaitTimeInMs = 135000; // 2:15
    this.timeoutInMs = 120000; // 2:00
    this.isEncryptedChannel = false; // for perf
    this.channels = new HashSet<String>();
    this.channels.add("order_book"); 
	this.pusherOpts = new PusherOptions();
	this.pusherOpts.setEncrypted(isEncryptedChannel);
	this.pusherOpts.setActivityTimeout(4 * timeoutInMs); // Keep-alive interval
	this.pusherOpts.setPongTimeout(timeoutMs); // Response timeout
  }
  
  public final PusherOptions pusherOptions() {
	  return this.pusherOpts;
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

    return isEncryptedChannel;
  }

  public Set<String> getChannels() {

    return channels;
  }

}
