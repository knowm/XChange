/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

import com.xeiam.xchange.streaming.websocket.CloseFrame;
import com.xeiam.xchange.streaming.websocket.exceptions.InvalidDataException;

public class CharsetUtils {

  public static CodingErrorAction codingErrorAction = CodingErrorAction.REPORT;

  /**
   * @param input The string to be converted
   * @return UTF-8 encoding in bytes
   */
  public static byte[] toByteArrayUtf8(String input) {
    try {
      return input.getBytes("UTF8");
    } catch (UnsupportedEncodingException e) {
      // Recast as Runtime exception since it should never occur
      throw new RuntimeException(e);
    }
  }

  /**
   * @param input The string to be converted
   * @return ASCII encoding in bytes
   */
  public static byte[] toByteArrayAscii(String input) {
    try {
      return input.getBytes("ASCII");
    } catch (UnsupportedEncodingException e) {
      // Recast as Runtime exception since it should never occur
      throw new RuntimeException(e);
    }
  }

  /**
   * @param bytes The byte array
   * @return An ASCII encoded version of the byte array
   */
  public static String toStringAscii(byte[] bytes) {
    return toStringAscii(bytes, 0, bytes.length);
  }

  /**
   * <p>
   * Constructs a new String by decoding the specified subarray of bytes using the specified charset. The length of the new String is a function of the charset, and hence may not be equal to the length of the subarray
   * </p>
   * 
   * @param bytes The bytes to be decoded into characters
   * @param offset The index of the first byte to decode
   * @param length The number of bytes to decode
   * @return The encoded String
   */
  public static String toStringAscii(byte[] bytes, int offset, int length) {
    try {
      return new String(bytes, offset, length, "ASCII");
    } catch (UnsupportedEncodingException e) {
      // Recast as Runtime exception since it should never occur
      throw new RuntimeException(e);
    }
  }

  public static String toStringUtf8(byte[] bytes) throws InvalidDataException {
    return toStringUtf8(bytes, 0, bytes.length);
  }

  /**
   * <p>
   * Constructs a new String by decoding the specified subarray of bytes using the specified charset. The length of the new String is a function of the charset, and hence may not be equal to the length of the subarray
   * </p>
   * 
   * @param bytes The bytes to be decoded into characters
   * @param offset The index of the first byte to decode
   * @param length The number of bytes to decode
   * @return The encoded String
   * @throws com.xeiam.xchange.streaming.websocket.exceptions.InvalidDataException If something goes wrong
   */
  public static String toStringUtf8(byte[] bytes, int offset, int length) throws InvalidDataException {
    CharsetDecoder decode = Charset.forName("UTF8").newDecoder();
    decode.onMalformedInput(codingErrorAction);
    decode.onUnmappableCharacter(codingErrorAction);
    String s;
    try {
      s = decode.decode(ByteBuffer.wrap(bytes, offset, length)).toString();
    } catch (CharacterCodingException e) {
      // Recast as Runtime exception since it should never occur
      throw new InvalidDataException(CloseFrame.NO_UTF8, e);
    }
    return s;
  }
}
