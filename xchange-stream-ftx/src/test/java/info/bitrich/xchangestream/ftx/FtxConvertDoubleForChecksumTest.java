package info.bitrich.xchangestream.ftx;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.junit.Test;

public class FtxConvertDoubleForChecksumTest {

  private static final DecimalFormat df = new DecimalFormat("0.0####");

  @Test
  public void testValues() {
    assertThat(df.format(BigDecimal.valueOf(35862))).isEqualTo("35862.0");
    assertThat(df.format(BigDecimal.valueOf(0.0001))).isEqualTo("0.0001");
    assertThat(df.format(BigDecimal.valueOf(35860))).isEqualTo("35860.0");
    assertThat(df.format(BigDecimal.valueOf(35860.1))).isEqualTo("35860.1");
  }
}
