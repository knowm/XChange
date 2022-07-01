package org.knowm.xchange.okcoin.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.junit.Test;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.okcoin.OkCoinExchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.utils.Assert;

public class MetaDataFileTest {
  @Test
  public void metaDataFileNameTest() {

    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    String metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    Assert.isTrue(
        "okcoin_china".equals(metaDataFileName),
        "the meta data file name not equal \"okcoin_china\" ???");
    System.out.println("metaDataFileName=" + metaDataFileName);

    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    Assert.isTrue(
        "okcoin_intl".equals(metaDataFileName),
        "the meta data file name not equal \"okcoin_intl\" ???");
    System.out.println("metaDataFileName=" + metaDataFileName);

    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    exSpec.setExchangeSpecificParametersItem("Use_Futures", true);
    metaDataFileName = ((BaseExchange) exchange).getMetaDataFileName(exSpec);
    Assert.isTrue(
        "okcoin_futures".equals(metaDataFileName),
        "the meta data file name not equal \"okcoin_futures\" ???");
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
    InputStream inputStream =
        BaseExchangeService.class.getClassLoader().getResourceAsStream(metaDataFileName + ".json");

    String strContents =
        new BufferedReader(new InputStreamReader(inputStream))
            .lines()
            .collect(Collectors.joining("\n"));

    System.out.println(strContents);
  }
}
