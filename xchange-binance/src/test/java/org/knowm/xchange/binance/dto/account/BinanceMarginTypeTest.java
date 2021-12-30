package org.knowm.xchange.binance.dto.account;

import org.junit.Test;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BinanceMarginTypeTest {

    @Test
    public void createFromLowercase() throws IOException {
        String s = ObjectMapperHelper.toJSON(BinanceMarginType.ISOLATED);
        assertThat(ObjectMapperHelper.readValue(s.toLowerCase(), BinanceMarginType.class)).isEqualTo(BinanceMarginType.ISOLATED);
    }

    @Test
    public void createFromUppercase() throws IOException {
        assertThat(ObjectMapperHelper.viaJSON(BinanceMarginType.CROSSED)).isEqualTo(BinanceMarginType.CROSSED);
    }

    @Test
    public void createFromBinanceDevsCannotArrangeValueCase() {
        assertThat(BinanceMarginType.getBinanceMarginType("isolated")).isEqualTo(BinanceMarginType.ISOLATED);
    }

    @Test
    public void createFromBinanceDevsCannotArrangeNamingConventionsCase() {
        assertThat(BinanceMarginType.getBinanceMarginType   ("cross")).isEqualTo(BinanceMarginType.CROSSED);
    }
}