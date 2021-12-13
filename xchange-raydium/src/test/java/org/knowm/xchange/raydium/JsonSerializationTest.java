package org.knowm.xchange.raydium;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.makarid.solanaj.core.PublicKey;
import org.junit.Test;
import org.knowm.xchange.raydium.dto.FarmListDto;
import org.knowm.xchange.raydium.dto.LpListDto;
import org.knowm.xchange.raydium.dto.TokenListDto;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JsonSerializationTest {

  @Test
  public void testTokenListJson() throws IOException {

    ObjectMapper mapper = new ObjectMapper();

    TokenListDto tokenListDTO =
        mapper.readValue(
            this.getClass().getResourceAsStream("/tokenList.json"), TokenListDto.class);
    FarmListDto farmListDto =
        mapper.readValue(this.getClass().getResourceAsStream("/farmList.json"), FarmListDto.class);
    LpListDto lpListDto =
        mapper.readValue(
            this.getClass().getResourceAsStream("/liquidityList.json"), LpListDto.class);

    assertThat(
            tokenListDTO
                .getSpl()
                .getSpls()
                .get(new PublicKey("9n4nbM75f5Ui33ZbPYXn59EwSgE8CGsHtAeTH5YFeJ9E"))
                .getName())
        .isEqualTo("Wrapped Bitcoin");
    assertThat(farmListDto.getOfficial().get(0).getId())
        .isEqualTo(new PublicKey("AvbVWpBi2e4C9HPmZgShGdPoNydG4Yw8GJvG9HUcLgce"));
    assertThat(lpListDto.getOfficial().get(0).getId())
        .isEqualTo(new PublicKey("2dRNngAm729NzLbb1pzgHtfHvPqR4XHFmFyYK78EfEeX"));
  }
}
