/**
 * Copyright (C) 2012 - 2013, Enno Boland
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
   * @param text the text to be sent
   * @throws IOException Signals that an I/O exception has occurred.
   */
  void send(String text) throws Exception;

  /**
   * return true if the IOTransport prefers to send multiple messages at a time.
   * 
   * @return true, if successful
   */
  boolean canSendBulk();

  /**
   * Instructs the IOTransport to send multiple messages. This is only called when canSendBulk returns true.
   * 
   * @param texts the texts
   * @throws IOException Signals that an I/O exception has occurred.
   */
  void sendBulk(String[] texts) throws IOException;

  /**
   * Instructs the IOTransport to invalidate. DO NOT DISCONNECT from the server. just make sure, that events are not populated to the {@link IOConnection}
   */
  void invalidate();

  String getName();
}
