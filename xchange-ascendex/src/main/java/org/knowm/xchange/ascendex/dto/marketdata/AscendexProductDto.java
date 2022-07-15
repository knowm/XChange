package org.knowm.xchange.ascendex.dto.marketdata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexProductDto extends AscendexProductBaseDto {

  private String baseAsset;

  private String quoteAsset;

  private AscendexAssetDto.AscendexAssetStatus status;

  private boolean marginTradable;

  public enum AscendexProductCommissionType {
    Base,
    Quote,
    Received
  }
}
