package com.knowm.xchange.serum.structures;

import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;
import com.knowm.xchange.serum.core.Base58;
import com.knowm.xchange.serum.dto.PublicKey;

public class PublicKeyLayout {

  @Bin(order = 1, name = "publicKeyLayout", type = BinType.BYTE_ARRAY)
  byte[] publicKeyLayout;

  public PublicKey getPublicKey() {
    return new PublicKey(Base58.encode(publicKeyLayout));
  }
}
