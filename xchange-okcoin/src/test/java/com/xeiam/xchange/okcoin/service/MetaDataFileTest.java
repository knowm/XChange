package com.xeiam.xchange.okcoin.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.okcoin.OkCoinExchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.utils.Assert;

public class MetaDataFileTest {
  @Test
  public void metaDataFileNameTest() {

    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    String metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    Assert.isTrue("okcoin_china".equals(metaDataFileName), "the meta data file name not equal \"okcoin_china\" ???");
    System.out.println("metaDataFileName=" + metaDataFileName);

    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    Assert.isTrue("okcoin_intl".equals(metaDataFileName), "the meta data file name not equal \"okcoin_intl\" ???");
    System.out.println("metaDataFileName=" + metaDataFileName);

    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    exSpec.setExchangeSpecificParametersItem("Use_Futures", true);
    metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    Assert.isTrue("okcoin_futures".equals(metaDataFileName), "the meta data file name not equal \"okcoin_futures\" ???");
    System.out.println("metaDataFileName=" + metaDataFileName);
  }

  @Test
  public void loadMetaDataFileNameForChinaTest() {
    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    String metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    loadMetaDataFileContents(metaDataFileName);

    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    loadMetaDataFileContents(metaDataFileName);

    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    exSpec.setExchangeSpecificParametersItem("Use_Futures", true);
    metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    loadMetaDataFileContents(metaDataFileName);
  }

  private void loadMetaDataFileContents(String metaDataFileName) {
    InputStream inputStream = BaseExchangeService.class.getClassLoader().getResourceAsStream(metaDataFileName + ".json");
    byte[] contents = new byte[2048];
    try {
      IOUtils.read(inputStream, contents);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(inputStream);
    }

    System.out.println(new String(contents));
  }
}
