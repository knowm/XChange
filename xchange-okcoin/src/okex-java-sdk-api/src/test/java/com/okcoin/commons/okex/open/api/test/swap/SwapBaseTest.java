package com.okcoin.commons.okex.open.api.test.swap;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;

public class SwapBaseTest {
    public APIConfiguration config;

    public APIConfiguration config() {
        APIConfiguration config = new APIConfiguration();

        config.setEndpoint("http:");
        config.setApiKey("");
        config.setSecretKey("");

        config.setPassphrase("");
        config.setPrint(true);
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);

        return config;
    }

    int from = 0;
    int to = 0;
    int limit = 20;

    String instrument_id = "BTC-USD-SWAP";
}
