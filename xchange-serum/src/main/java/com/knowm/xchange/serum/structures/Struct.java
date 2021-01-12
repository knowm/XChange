package com.knowm.xchange.serum.structures;

import static com.knowm.xchange.serum.core.PublicKeys.DEPRECATED_PROGRAM_ID_1;
import static com.knowm.xchange.serum.core.PublicKeys.DEPRECATED_PROGRAM_ID_2;

import com.knowm.xchange.serum.dto.PublicKey;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Struct {

  // borrowing this logic from https://github.com/pabigot/buffer-layout/blob/master/lib/Layout.js
  public int valueMask;

  public Struct() {
    this(1);
  }

  public Struct(int bits) {
    this.valueMask = (1 << bits) - 1;
    if (32 == bits) { // shifted value out of range
      this.valueMask = 0xFFFFFFFF;
    }
  }

  public int fixBitwiseResult(int v) {
    if (0 > v) {
      v += 0x100000000L;
    }
    return v;
  }

  public boolean[] booleanFlagsDecoder(byte[] bytes, int width) {
    boolean[] results = new boolean[width];

    int i = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    for (int idx = 0; idx < width; idx++) {
      int wordMask = fixBitwiseResult(this.valueMask << idx);
      int wordValue = fixBitwiseResult(i & wordMask);
      byte value = (byte) (wordValue >>> idx);
      results[idx] = value != 0;
    }
    return results;
  }

  public static long decodeLong(byte[] bytes) {
    return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getLong();
  }

  /**
   * Method was taken from
   * https://github.com/project-serum/serum-ts/blob/master/packages/serum/src/tokens_and_markets.ts
   */
  static int getLayoutVersion(final PublicKey programId) {
    if (programId.equals(DEPRECATED_PROGRAM_ID_1) || programId.equals(DEPRECATED_PROGRAM_ID_2)) {
      return 1;
    }
    return 2;
  }
}
