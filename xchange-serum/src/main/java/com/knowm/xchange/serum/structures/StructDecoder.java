package com.knowm.xchange.serum.structures;

import java.io.IOException;
import java.util.Base64;

public interface StructDecoder<T> {

  T decode(final byte[] bytes) throws IOException;

  default T decode(final String data) throws IOException {
    return decode(Base64.getDecoder().decode(data));
  }
}
