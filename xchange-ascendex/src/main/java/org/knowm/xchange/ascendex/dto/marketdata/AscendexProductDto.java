package org.knowm.xchange.ascendex.dto.marketdata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.knowm.xchange.ascendex.dto.enums.AscendexAssetStatus;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexProductDto extends AscendexProductBaseDto {

  private String baseAsset;

  private String quoteAsset;

  private AscendexAssetStatus status;

  private boolean marginTradable;


}
