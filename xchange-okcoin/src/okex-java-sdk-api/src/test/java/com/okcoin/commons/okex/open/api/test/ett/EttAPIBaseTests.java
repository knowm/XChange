package com.okcoin.commons.okex.open.api.test.ett;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.test.BaseTests;

public class EttAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        final APIConfiguration config = new APIConfiguration();
        config.setEndpoint("https://www.okex.com/");
        config.setApiKey("");
        config.setSecretKey("");

        config.setPassphrase("");
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);
        config.setPrint(true);

        return config;
    }

}
