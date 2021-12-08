package org.knowm.xchange.raydium.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.makarid.solanaj.core.PublicKey;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
@Getter
public class LpListDto {

  private final String name;

  private final Date timestamp;

  private final JsonNode version;

  private final List<LpInfo> official;

  private final List<LpInfo> unOfficial;

  public LpListDto(
      @JsonProperty("name") String name,
      @JsonProperty("timestamp") Date timestamp,
      @JsonProperty("version") JsonNode version,
      @JsonProperty("official") List<LpInfo> official,
      @JsonProperty("unOfficial") List<LpInfo> unOfficial) {
    this.name = name;
    this.timestamp = timestamp;
    this.version = version;
    this.official = official;
    this.unOfficial = unOfficial;
  }

  @ToString
  @Getter
  public static class LpInfo {
    private final PublicKey id;

    private final PublicKey baseMint;

    private final PublicKey quoteMint;

    private final PublicKey lpMint;

    private final int version;

    private final PublicKey programId;

    private final PublicKey authority;

    private final PublicKey openOrders;

    private final PublicKey targetOrders;

    private final PublicKey baseVault;

    private final PublicKey quoteVault;

    private final PublicKey withdrawQueue;

    private final PublicKey tempLpVault;

    private final int marketVersion;

    private final PublicKey marketProgramId;

    private final PublicKey marketId;

    private final PublicKey marketVaultSigner;

    private final PublicKey marketBaseVault;

    private final PublicKey marketQuoteVault;

    private final PublicKey marketBids;

    private final PublicKey marketAsks;

    private final PublicKey marketEventQueue;

    public LpInfo(
        @JsonProperty("id") PublicKey id,
        @JsonProperty("baseMint") PublicKey baseMint,
        @JsonProperty("quoteMint") PublicKey quoteMint,
        @JsonProperty("lpMint") PublicKey lpMint,
        @JsonProperty("version") int version,
        @JsonProperty("programId") PublicKey programId,
        @JsonProperty("authority") PublicKey authority,
        @JsonProperty("openOrders") PublicKey openOrders,
        @JsonProperty("targetOrders") PublicKey targetOrders,
        @JsonProperty("baseVault") PublicKey baseVault,
        @JsonProperty("quoteVault") PublicKey quoteVault,
        @JsonProperty("withdrawQueue") PublicKey withdrawQueue,
        @JsonProperty("tempLpVault") PublicKey tempLpVault,
        @JsonProperty("marketVersion") int marketVersion,
        @JsonProperty("marketProgramId") PublicKey marketProgramId,
        @JsonProperty("marketId") PublicKey marketId,
        @JsonProperty("marketVaultSigner") PublicKey marketVaultSigner,
        @JsonProperty("marketBaseVault") PublicKey marketBaseVault,
        @JsonProperty("marketQuoteVault") PublicKey marketQuoteVault,
        @JsonProperty("marketBids") PublicKey marketBids,
        @JsonProperty("marketAsks") PublicKey marketAsks,
        @JsonProperty("marketEventQueue") PublicKey marketEventQueue) {
      this.id = id;
      this.baseMint = baseMint;
      this.quoteMint = quoteMint;
      this.lpMint = lpMint;
      this.version = version;
      this.programId = programId;
      this.authority = authority;
      this.openOrders = openOrders;
      this.targetOrders = targetOrders;
      this.baseVault = baseVault;
      this.quoteVault = quoteVault;
      this.withdrawQueue = withdrawQueue;
      this.tempLpVault = tempLpVault;
      this.marketVersion = marketVersion;
      this.marketProgramId = marketProgramId;
      this.marketId = marketId;
      this.marketVaultSigner = marketVaultSigner;
      this.marketBaseVault = marketBaseVault;
      this.marketQuoteVault = marketQuoteVault;
      this.marketBids = marketBids;
      this.marketAsks = marketAsks;
      this.marketEventQueue = marketEventQueue;
    }
  }
}
