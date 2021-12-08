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
public class FarmListDto {

  private final String name;

  private final Date timestamp;

  private final JsonNode version;

  private final List<FarmInfo> official;

  public FarmListDto(
      @JsonProperty("name") String name,
      @JsonProperty("timestamp") Date timestamp,
      @JsonProperty("version") JsonNode version,
      @JsonProperty("official") List<FarmInfo> official) {
    this.name = name;
    this.timestamp = timestamp;
    this.version = version;
    this.official = official;
  }

  @ToString
  @Getter
  public static class FarmInfo {
    private final PublicKey id;

    private final PublicKey lpMint;

    private final List<PublicKey> rewardMints;

    private final int version;

    private final PublicKey programId;

    private final PublicKey authority;

    private final PublicKey lpVault;

    private final List<PublicKey> rewardVaults;

    public FarmInfo(
        @JsonProperty("id") PublicKey id,
        @JsonProperty("lpMint") PublicKey lpMint,
        @JsonProperty("rewardMints") List<PublicKey> rewardMints,
        @JsonProperty("version") int version,
        @JsonProperty("programId") PublicKey programId,
        @JsonProperty("authority") PublicKey authority,
        @JsonProperty("lpVault") PublicKey lpVault,
        @JsonProperty("rewardVaults") List<PublicKey> rewardVaults) {
      this.id = id;
      this.lpMint = lpMint;
      this.rewardMints = rewardMints;
      this.version = version;
      this.programId = programId;
      this.authority = authority;
      this.lpVault = lpVault;
      this.rewardVaults = rewardVaults;
    }
  }
}
