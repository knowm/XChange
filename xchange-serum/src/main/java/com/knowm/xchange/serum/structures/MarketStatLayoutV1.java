package com.knowm.xchange.serum.structures;

import com.igormaznitsa.jbbp.JBBPParser;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;
import com.knowm.xchange.serum.dto.PublicKey;

public class MarketStatLayoutV1 extends MarketLayout {

  @Bin(order = 1, name = "blob5", type = BinType.BYTE_ARRAY)
  public byte[] blob5;

  @Bin(order = 2, name = "accountFlags", type = BinType.STRUCT)
  public AccountFlagsLayout accountFlags;

  @Bin(order = 3, name = "ownAddress", type = BinType.STRUCT)
  public PublicKeyLayout ownAddress;

  @Bin(order = 4, name = "vaultSignerNonce", type = BinType.BYTE_ARRAY)
  public byte[] vaultSignerNonce;

  @Bin(order = 5, name = "baseMint", type = BinType.STRUCT)
  public PublicKeyLayout baseMint;

  @Bin(order = 6, name = "quoteMint", type = BinType.STRUCT)
  public PublicKeyLayout quoteMint;

  @Bin(order = 7, name = "baseVault", type = BinType.STRUCT)
  public PublicKeyLayout baseVault;

  @Bin(order = 8, name = "baseDepositsTotal", type = BinType.BYTE_ARRAY)
  public byte[] baseDepositsTotal;

  @Bin(order = 9, name = "baseFeesAccrued", type = BinType.BYTE_ARRAY)
  public byte[] baseFeesAccrued;

  @Bin(order = 10, name = "quoteVault", type = BinType.STRUCT)
  public PublicKeyLayout quoteVault;

  @Bin(order = 11, name = "quoteDepositsTotal", type = BinType.BYTE_ARRAY)
  public byte[] quoteDepositsTotal;

  @Bin(order = 12, name = "quoteFeesAccrued", type = BinType.BYTE_ARRAY)
  public byte[] quoteFeesAccrued;

  @Bin(order = 13, name = "quoteDustThreshold", type = BinType.BYTE_ARRAY)
  public byte[] quoteDustThreshold;

  @Bin(order = 14, name = "requestQueue", type = BinType.STRUCT)
  public PublicKeyLayout requestQueue;

  @Bin(order = 15, name = "eventQueue", type = BinType.STRUCT)
  public PublicKeyLayout eventQueue;

  @Bin(order = 16, name = "bids", type = BinType.STRUCT)
  public PublicKeyLayout bids;

  @Bin(order = 17, name = "asks", type = BinType.STRUCT)
  public PublicKeyLayout asks;

  @Bin(order = 18, name = "baseLotSize", type = BinType.BYTE_ARRAY)
  public byte[] baseLotSize;

  @Bin(order = 19, name = "quoteLotSize", type = BinType.BYTE_ARRAY)
  public byte[] quoteLotSize;

  @Bin(order = 20, name = "feeRateBps", type = BinType.BYTE_ARRAY)
  public byte[] feeRateBps;

  @Bin(order = 21, name = "blob7", type = BinType.BYTE_ARRAY)
  public byte[] blob7;

  public static final StructDecoder<MarketStat> DECODER =
      bytes -> {
        final MarketStatLayoutV1 deserialized =
            JBBPParser.prepare(
                    ""
                        + "byte[5] blob5; "
                        + "accountFlags {"
                        + " byte[8] bytes; "
                        + "} "
                        + "ownAddress {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "byte[8] vaultSignerNonce; "
                        + "baseMint {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "quoteMint {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "baseVault {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "byte[8] baseDepositsTotal; "
                        + "byte[8] baseFeesAccrued; "
                        + "quoteVault {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "byte[8] quoteDepositsTotal; "
                        + "byte[8] quoteFeesAccrued; "
                        + "byte[8] quoteDustThreshold; "
                        + "requestQueue {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "eventQueue {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "bids {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "asks {"
                        + " byte[32] publicKeyLayout; "
                        + "} "
                        + "byte[8] baseLotSize; "
                        + "byte[8] quoteLotSize; "
                        + "byte[8] feeRateBps; "
                        + "byte[7] blob7;")
                .parse((bytes))
                .mapTo(new MarketStatLayoutV1());
        return new MarketStatV1(
            deserialized.accountFlags.decode(),
            deserialized.ownAddress.getPublicKey(),
            decodeLong(deserialized.vaultSignerNonce),
            deserialized.baseMint.getPublicKey(),
            deserialized.quoteMint.getPublicKey(),
            deserialized.baseVault.getPublicKey(),
            decodeLong(deserialized.baseDepositsTotal),
            decodeLong(deserialized.baseFeesAccrued),
            deserialized.quoteVault.getPublicKey(),
            decodeLong(deserialized.quoteDepositsTotal),
            decodeLong(deserialized.quoteFeesAccrued),
            decodeLong(deserialized.quoteDustThreshold),
            deserialized.requestQueue.getPublicKey(),
            deserialized.eventQueue.getPublicKey(),
            deserialized.bids.getPublicKey(),
            deserialized.asks.getPublicKey(),
            decodeLong(deserialized.baseLotSize),
            decodeLong(deserialized.quoteLotSize),
            decodeLong(deserialized.feeRateBps));
      };

  public static class MarketStatV1 implements MarketStat {

    public AccountFlagsLayout.AccountFlags accountFlags;

    public PublicKey ownAddress;

    public long vaultSignerNonce;

    public PublicKey baseMint;
    public PublicKey quoteMint;

    public PublicKey baseVault;
    public long baseDepositsTotal;
    public long baseFeesAccrued;

    public PublicKey quoteVault;
    public long quoteDepositsTotal;
    public long quoteFeesAccrued;

    public long quoteDustThreshold;

    public PublicKey requestQueue;
    public PublicKey eventQueue;

    public PublicKey bids;
    public PublicKey asks;

    public long baseLotSize;
    public long quoteLotSize;

    public long feeRateBps;

    public MarketStatV1(
        AccountFlagsLayout.AccountFlags accountFlags,
        PublicKey ownAddress,
        long vaultSignerNonce,
        PublicKey baseMint,
        PublicKey quoteMint,
        PublicKey baseVault,
        long baseDepositsTotal,
        long baseFeesAccrued,
        PublicKey quoteVault,
        long quoteDepositsTotal,
        long quoteFeesAccrued,
        long quoteDustThreshold,
        PublicKey requestQueue,
        PublicKey eventQueue,
        PublicKey bids,
        PublicKey asks,
        long baseLotSize,
        long quoteLotSize,
        long feeRateBps) {
      this.accountFlags = accountFlags;
      this.ownAddress = ownAddress;
      this.vaultSignerNonce = vaultSignerNonce;
      this.baseMint = baseMint;
      this.quoteMint = quoteMint;
      this.baseVault = baseVault;
      this.baseDepositsTotal = baseDepositsTotal;
      this.baseFeesAccrued = baseFeesAccrued;
      this.quoteVault = quoteVault;
      this.quoteDepositsTotal = quoteDepositsTotal;
      this.quoteFeesAccrued = quoteFeesAccrued;
      this.quoteDustThreshold = quoteDustThreshold;
      this.requestQueue = requestQueue;
      this.eventQueue = eventQueue;
      this.bids = bids;
      this.asks = asks;
      this.baseLotSize = baseLotSize;
      this.quoteLotSize = quoteLotSize;
      this.feeRateBps = feeRateBps;
    }

    @Override
    public AccountFlagsLayout.AccountFlags getAccountFlags() {
      return accountFlags;
    }

    @Override
    public PublicKey getOwnAddress() {
      return ownAddress;
    }

    @Override
    public PublicKey baseMint() {
      return baseMint;
    }

    @Override
    public PublicKey quoteMint() {
      return quoteMint;
    }

    @Override
    public PublicKey getBids() {
      return bids;
    }

    @Override
    public PublicKey getAsks() {
      return asks;
    }

    @Override
    public PublicKey getEventQueue() {
      return eventQueue;
    }

    @Override
    public PublicKey getRequestQueue() {
      return requestQueue;
    }

    @Override
    public long getBaseLotSize() {
      return baseLotSize;
    }

    @Override
    public long getQuoteLotSize() {
      return quoteLotSize;
    }
  }
}
