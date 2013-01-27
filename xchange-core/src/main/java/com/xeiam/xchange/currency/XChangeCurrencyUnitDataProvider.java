/**
 *  Copyright 2009-2013 Stephen Colebourne
 *  Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.xeiam.xchange.currency;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.money.CurrencyUnitDataProvider;

/**
 * Provider for available currencies using a file.
 * <p>
 * This reads the first resource named {@code /com/xeiam/xchange/XChangeCurrencies.csv} on the classpath.
 */
public class XChangeCurrencyUnitDataProvider extends CurrencyUnitDataProvider {

  /** Regex format for the csv line. */
  private static final Pattern REGEX_LINE = Pattern.compile("([A-Z]{3}),(-1|[0-9]{1,3}),(-1|0|1|2|3),([A-Z]*)#?.*");

  /**
   * Registers all the currencies known by this provider.
   * <p>
   * This reads the first resource named '/com/xeiam/xchange/XChangeCurrencies.csv' on the classpath.
   * 
   * @throws Exception if an error occurs
   */
  @Override
  protected void registerCurrencies() throws Exception {

    InputStream in = getClass().getResourceAsStream("/com/xeiam/xchange/XChangeCurrencies.csv");
    if (in == null) {
      throw new FileNotFoundException("Data file /com/xeiam/xchange/XChangeCurrencies.csv not found");
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
    String line;
    while ((line = reader.readLine()) != null) {
      Matcher matcher = REGEX_LINE.matcher(line);
      if (matcher.matches()) {
        List<String> countryCodes = new ArrayList<String>();
        String codeStr = matcher.group(4);
        String currencyCode = matcher.group(1);
        if (codeStr.length() % 2 == 1) {
          continue; // invalid line
        }
        for (int i = 0; i < codeStr.length(); i += 2) {
          countryCodes.add(codeStr.substring(i, i + 2));
        }
        int numericCode = Integer.parseInt(matcher.group(2));
        int digits = Integer.parseInt(matcher.group(3));
        registerCurrency(currencyCode, numericCode, digits, countryCodes);
      }
    }
  }

}
