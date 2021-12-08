package org.knowm.xchange.raydium.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.makarid.solanaj.core.PublicKey;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.Date;
import java.util.HashMap;

@ToString
@Getter
public class TokenListDto {

  private final String name;

  private final Date timeStamp;

  private final JsonNode version;

  private final SplDto spl;

  private final LpDto lp;

  public TokenListDto(
      @JsonProperty("name") String name,
      @JsonProperty("timestamp") Date timeStamp,
      @JsonProperty("version") JsonNode version,
      @JsonProperty("spl") SplDto spl,
      @JsonProperty("lp") LpDto lp) {
    this.name = name;
    this.timeStamp = timeStamp;
    this.version = version;
    this.spl = spl;
    this.lp = lp;
  }

  @ToString
  @Getter
  public static class SplDto {

    private final HashMap<PublicKey, SplDto.SplDetails> spls = new HashMap<>();

    @JsonAnySetter
    public void setSplDetails(String name, SplDto.SplDetails splDetails) {
      this.spls.put(splDetails.getMintAddress(), splDetails);
    }

    @ToString
    @Getter
    public static class SplDetails {
      private final Currency symbol;

      private final String name;

      private final PublicKey mintAddress;

      private final int decimals;

      private final JsonNode extensions;

      public SplDetails(
          @JsonProperty("symbol") Currency symbol,
          @JsonProperty("name") String name,
          @JsonProperty("mint") PublicKey mintAddress,
          @JsonProperty("decimals") int decimals,
          @JsonProperty("extensions") JsonNode extensions) {
        this.symbol = symbol;
        this.name = name;
        this.mintAddress = mintAddress;
        this.decimals = decimals;
        this.extensions = extensions;
      }
    }
  }

  @ToString
  @Getter
  public static class LpDto {

    private final HashMap<String, LpDto.LpDetails> lps = new HashMap<>();

    @JsonAnySetter
    public void setLpDetails(String name, LpDto.LpDetails lpDetails) {
      this.lps.put(name, lpDetails);
    }

    @ToString
    @Getter
    public static class LpDetails {
      private final CurrencyPair symbol;

      private final String name;

      private final PublicKey mintAddress;

      private final PublicKey baseAddress;

      private final PublicKey quoteAddress;

      private final int decimals;

      private final int version;

      public LpDetails(
          @JsonProperty("symbol") CurrencyPair symbol,
          @JsonProperty("name") String name,
          @JsonProperty("mint") PublicKey mintAddress,
          @JsonProperty("base") PublicKey baseAddress,
          @JsonProperty("quote") PublicKey quoteAddress,
          @JsonProperty("decimals") int decimals,
          @JsonProperty("version") int version) {
        this.symbol = symbol;
        this.name = name;
        this.mintAddress = mintAddress;
        this.baseAddress = baseAddress;
        this.quoteAddress = quoteAddress;
        this.decimals = decimals;
        this.version = version;
      }
    }
  }
}
