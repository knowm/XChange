package com.knowm.xchange.serum.structures;

import com.igormaznitsa.jbbp.JBBPParser;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

public class MintLayout extends Struct {

  @Bin(order = 1, name = "blob44", type = BinType.BYTE_ARRAY)
  public byte[] blob44;

  @Bin(order = 2, name = "decimals", type = BinType.BYTE)
  public byte decimals;

  @Bin(order = 3, name = "blob37", type = BinType.BYTE_ARRAY)
  public byte[] blob37;

  public static final StructDecoder<MintLayout> DECODER =
      bytes ->
          JBBPParser.prepare("" + "byte[44] blob44; " + "byte decimals; " + "byte[37] blob37;")
              .parse(bytes)
              .mapTo(new MintLayout());
}
