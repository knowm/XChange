/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.vaultofsatoshi.service;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * @author veken0m
 */
public class VaultOfSatoshiBaseService extends BaseExchangeService {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  // BTC
      new CurrencyPair("BTC", "CAD"), // BTC/CAD
      new CurrencyPair("BTC", "USD"), // BTC/USD
      new CurrencyPair("BTC", "LTC"), // BTC/LTC
      new CurrencyPair("BTC", "PPC"), // BTC/PPC
      new CurrencyPair("BTC", "DOGE"), // BTC/DOGE
      new CurrencyPair("BTC", "FTC"), // BTC/FTC
      new CurrencyPair("BTC", "XPM"), // BTC/XPM
      new CurrencyPair("BTC", "QRK"), // BTC/QRK
      new CurrencyPair("BTC", "VTC"), // BTC/VTC
      new CurrencyPair("BTC", "AUR"), // BTC/AUR
      new CurrencyPair("BTC", "CGB"), // BTC/CGB
      new CurrencyPair("BTC", "DGC"), // BTC/DGC
      new CurrencyPair("BTC", "MINT"), // BTC/MINT
      new CurrencyPair("BTC", "DRK"), // BTC/DRK
      new CurrencyPair("BTC", "WDC"), // BTC/WDC
      new CurrencyPair("BTC", "BC"), // BTC/BC
      new CurrencyPair("BTC", "ZET"), // BTC/ZET
      new CurrencyPair("BTC", "FLT"), // BTC/FLT
      new CurrencyPair("BTC", "NVC"), // BTC/NVC
      new CurrencyPair("BTC", "EMC2"), // BTC/EMC2
      new CurrencyPair("BTC", "BIL"), // BTC/BIL
      new CurrencyPair("BTC", "NOBL"), // BTC/NOBL

      // LTC
      new CurrencyPair("LTC", "CAD"), // LTC/CAD
      new CurrencyPair("LTC", "USD"), // LTC/USD
      new CurrencyPair("LTC", "BTC"), // LTC/BTC
      new CurrencyPair("LTC", "PPC"), // LTC/PPC
      new CurrencyPair("LTC", "DOGE"), // LTC/DOGE
      new CurrencyPair("LTC", "FTC"), // LTC/FTC
      new CurrencyPair("LTC", "XPM"), // LTC/XPM
      new CurrencyPair("LTC", "QRK"), // LTC/QRK
      new CurrencyPair("LTC", "VTC"), // LTC/VTC
      new CurrencyPair("LTC", "AUR"), // LTC/AUR
      new CurrencyPair("LTC", "CGB"), // LTC/CGB
      new CurrencyPair("LTC", "DGC"), // LTC/DGC
      new CurrencyPair("LTC", "MINT"), // LTC/MINT
      new CurrencyPair("LTC", "DRK"), // LTC/DRK
      new CurrencyPair("LTC", "WDC"), // LTC/WDC
      new CurrencyPair("LTC", "BC"), // LTC/BC
      new CurrencyPair("LTC", "ZET"), // LTC/ZET
      new CurrencyPair("LTC", "FLT"), // LTC/FLT
      new CurrencyPair("LTC", "NVC"), // LTC/NVC
      new CurrencyPair("LTC", "EMC2"), // LTC/EMC2
      new CurrencyPair("LTC", "BIL"), // LTC/BIL
      new CurrencyPair("LTC", "NOBL"), // LTC/NOBL

      // DOGE
      new CurrencyPair("DOGE", "CAD"), // DOGE/CAD
      new CurrencyPair("DOGE", "USD"), // DOGE/USD
      new CurrencyPair("DOGE", "BTC"), // DOGE/BTC
      new CurrencyPair("DOGE", "LTC"), // DOGE/LTC
      new CurrencyPair("DOGE", "PPC"), // DOGE/PPC
      new CurrencyPair("DOGE", "FTC"), // DOGE/FTC
      new CurrencyPair("DOGE", "XPM"), // DOGE/XPM
      new CurrencyPair("DOGE", "QRK"), // DOGE/QRK
      new CurrencyPair("DOGE", "VTC"), // DOGE/VTC
      new CurrencyPair("DOGE", "AUR"), // DOGE/AUR
      new CurrencyPair("DOGE", "CGB"), // DOGE/CGB
      new CurrencyPair("DOGE", "DGC"), // DOGE/DGC
      new CurrencyPair("DOGE", "MINT"), // DOGE/MINT
      new CurrencyPair("DOGE", "DRK"), // DOGE/DRK
      new CurrencyPair("DOGE", "WDC"), // DOGE/WDC
      new CurrencyPair("DOGE", "BC"), // DOGE/BC
      new CurrencyPair("DOGE", "ZET"), // DOGE/ZET
      new CurrencyPair("DOGE", "FLT"), // DOGE/FLT
      new CurrencyPair("DOGE", "NVC"), // DOGE/NVC
      new CurrencyPair("DOGE", "EMC2"), // DOGE/EMC2
      new CurrencyPair("DOGE", "BIL"), // DOGE/BIL
      new CurrencyPair("DOGE", "NOBL"), // DOGE/NOBL

      // PPC
      new CurrencyPair("PPC", "CAD"), // PPC/CAD
      new CurrencyPair("PPC", "USD"), // PPC/USD
      new CurrencyPair("PPC", "BTC"), // PPC/BTC
      new CurrencyPair("PPC", "LTC"), // PPC/LTC
      new CurrencyPair("PPC", "DOGE"), // PPC/DOGE
      new CurrencyPair("PPC", "FTC"), // PPC/FTC
      new CurrencyPair("PPC", "XPM"), // PPC/XPM
      new CurrencyPair("PPC", "QRK"), // PPC/QRK
      new CurrencyPair("PPC", "VTC"), // PPC/VTC
      new CurrencyPair("PPC", "AUR"), // PPC/AUR
      new CurrencyPair("PPC", "CGB"), // PPC/CGB
      new CurrencyPair("PPC", "DGC"), // PPC/DGC
      new CurrencyPair("PPC", "MINT"), // PPC/MINT
      new CurrencyPair("PPC", "DRK"), // PPC/DRK
      new CurrencyPair("PPC", "WDC"), // PPC/WDC
      new CurrencyPair("PPC", "BC"), // PPC/BC
      new CurrencyPair("PPC", "ZET"), // PPC/ZET
      new CurrencyPair("PPC", "FLT"), // PPC/FLT
      new CurrencyPair("PPC", "NVC"), // PPC/NVC
      new CurrencyPair("PPC", "EMC2"), // PPC/EMC2
      new CurrencyPair("PPC", "BIL"), // PPC/BIL
      new CurrencyPair("PPC", "NOBL"), // PPC/NOBL

      // FTC
      new CurrencyPair("FTC", "CAD"), // FTC/CAD
      new CurrencyPair("FTC", "USD"), // FTC/USD
      new CurrencyPair("FTC", "BTC"), // FTC/BTC
      new CurrencyPair("FTC", "LTC"), // FTC/LTC
      new CurrencyPair("FTC", "PPC"), // FTC/PPC
      new CurrencyPair("FTC", "DOGE"), // FTC/DOGE
      new CurrencyPair("FTC", "XPM"), // FTC/XPM
      new CurrencyPair("FTC", "QRK"), // FTC/QRK
      new CurrencyPair("FTC", "VTC"), // FTC/VTC
      new CurrencyPair("FTC", "AUR"), // FTC/AUR
      new CurrencyPair("FTC", "CGB"), // FTC/CGB
      new CurrencyPair("FTC", "DGC"), // FTC/DGC
      new CurrencyPair("FTC", "MINT"), // FTC/MINT
      new CurrencyPair("FTC", "DRK"), // FTC/DRK
      new CurrencyPair("FTC", "WDC"), // FTC/WDC
      new CurrencyPair("FTC", "BC"), // FTC/BC
      new CurrencyPair("FTC", "ZET"), // FTC/ZET
      new CurrencyPair("FTC", "FLT"), // FTC/FLT
      new CurrencyPair("FTC", "NVC"), // FTC/NVC
      new CurrencyPair("FTC", "EMC2"), // FTC/EMC2
      new CurrencyPair("FTC", "BIL"), // FTC/BIL
      new CurrencyPair("FTC", "NOBL"), // FTC/NOBL

      // XPM
      new CurrencyPair("XPM", "CAD"), // XPM/CAD
      new CurrencyPair("XPM", "USD"), // XPM/USD
      new CurrencyPair("XPM", "BTC"), // XPM/BTC
      new CurrencyPair("XPM", "LTC"), // XPM/LTC
      new CurrencyPair("XPM", "PPC"), // XPM/PPC
      new CurrencyPair("XPM", "DOGE"), // XPM/DOGE
      new CurrencyPair("XPM", "FTC"), // XPM/FTC
      new CurrencyPair("XPM", "QRK"), // XPM/QRK
      new CurrencyPair("XPM", "VTC"), // XPM/VTC
      new CurrencyPair("XPM", "AUR"), // XPM/AUR
      new CurrencyPair("XPM", "CGB"), // XPM/CGB
      new CurrencyPair("XPM", "DGC"), // XPM/DGC
      new CurrencyPair("XPM", "MINT"), // XPM/MINT
      new CurrencyPair("XPM", "DRK"), // XPM/DRK
      new CurrencyPair("XPM", "WDC"), // XPM/WDC
      new CurrencyPair("XPM", "BC"), // XPM/BC
      new CurrencyPair("XPM", "ZET"), // XPM/ZET
      new CurrencyPair("XPM", "FLT"), // XPM/FLT
      new CurrencyPair("XPM", "NVC"), // XPM/NVC
      new CurrencyPair("XPM", "EMC2"), // XPM/EMC2
      new CurrencyPair("XPM", "BIL"), // XPM/BIL
      new CurrencyPair("XPM", "NOBL"), // XPM/NOBL

      // QRK
      new CurrencyPair("QRK", "CAD"), // QRK/CAD
      new CurrencyPair("QRK", "USD"), // QRK/USD
      new CurrencyPair("QRK", "BTC"), // QRK/BTC
      new CurrencyPair("QRK", "LTC"), // QRK/LTC
      new CurrencyPair("QRK", "PPC"), // QRK/PPC
      new CurrencyPair("QRK", "DOGE"), // QRK/DOGE
      new CurrencyPair("QRK", "FTC"), // QRK/FTC
      new CurrencyPair("QRK", "XPM"), // QRK/XPM
      new CurrencyPair("QRK", "VTC"), // QRK/VTC
      new CurrencyPair("QRK", "AUR"), // QRK/AUR
      new CurrencyPair("QRK", "CGB"), // QRK/CGB
      new CurrencyPair("QRK", "DGC"), // QRK/DGC
      new CurrencyPair("QRK", "MINT"), // QRK/MINT
      new CurrencyPair("QRK", "DRK"), // QRK/DRK
      new CurrencyPair("QRK", "WDC"), // QRK/WDC
      new CurrencyPair("QRK", "BC"), // QRK/BC
      new CurrencyPair("QRK", "ZET"), // QRK/ZET
      new CurrencyPair("QRK", "FLT"), // QRK/FLT
      new CurrencyPair("QRK", "NVC"), // QRK/NVC
      new CurrencyPair("QRK", "EMC2"), // QRK/EMC2
      new CurrencyPair("QRK", "BIL"), // QRK/BIL
      new CurrencyPair("QRK", "NOBL"), // QRK/NOBL

      // VTC
      new CurrencyPair("VTC", "CAD"), // VTC/CAD
      new CurrencyPair("VTC", "USD"), // VTC/USD
      new CurrencyPair("VTC", "BTC"), // VTC/BTC
      new CurrencyPair("VTC", "LTC"), // VTC/LTC
      new CurrencyPair("VTC", "PPC"), // VTC/PPC
      new CurrencyPair("VTC", "DOGE"), // VTC/DOGE
      new CurrencyPair("VTC", "FTC"), // VTC/FTC
      new CurrencyPair("VTC", "XPM"), // VTC/XPM
      new CurrencyPair("VTC", "QRK"), // VTC/QRK
      new CurrencyPair("VTC", "AUR"), // VTC/AUR
      new CurrencyPair("VTC", "CGB"), // VTC/CGB
      new CurrencyPair("VTC", "DGC"), // VTC/DGC
      new CurrencyPair("VTC", "MINT"), // VTC/MINT
      new CurrencyPair("VTC", "DRK"), // VTC/DRK
      new CurrencyPair("VTC", "WDC"), // VTC/WDC
      new CurrencyPair("VTC", "BC"), // VTC/BC
      new CurrencyPair("VTC", "ZET"), // VTC/ZET
      new CurrencyPair("VTC", "FLT"), // VTC/FLT
      new CurrencyPair("VTC", "NVC"), // VTC/NVC
      new CurrencyPair("VTC", "EMC2"), // VTC/EMC2
      new CurrencyPair("VTC", "BIL"), // VTC/BIL
      new CurrencyPair("VTC", "NOBL"), // VTC/NOBL

      // AUR
      new CurrencyPair("AUR", "CAD"), // AUR/CAD
      new CurrencyPair("AUR", "USD"), // AUR/USD
      new CurrencyPair("AUR", "BTC"), // AUR/BTC
      new CurrencyPair("AUR", "LTC"), // AUR/LTC
      new CurrencyPair("AUR", "PPC"), // AUR/PPC
      new CurrencyPair("AUR", "DOGE"), // AUR/DOGE
      new CurrencyPair("AUR", "FTC"), // AUR/FTC
      new CurrencyPair("AUR", "XPM"), // AUR/XPM
      new CurrencyPair("AUR", "QRK"), // AUR/QRK
      new CurrencyPair("AUR", "VTC"), // AUR/VTC
      new CurrencyPair("AUR", "CGB"), // AUR/CGB
      new CurrencyPair("AUR", "DGC"), // AUR/DGC
      new CurrencyPair("AUR", "MINT"), // AUR/MINT
      new CurrencyPair("AUR", "DRK"), // AUR/DRK
      new CurrencyPair("AUR", "WDC"), // AUR/WDC
      new CurrencyPair("AUR", "BC"), // AUR/BC
      new CurrencyPair("AUR", "ZET"), // AUR/ZET
      new CurrencyPair("AUR", "FLT"), // AUR/FLT
      new CurrencyPair("AUR", "NVC"), // AUR/NVC
      new CurrencyPair("AUR", "EMC2"), // AUR/EMC2
      new CurrencyPair("AUR", "BIL"), // AUR/BIL
      new CurrencyPair("AUR", "NOBL"), // AUR/NOBL

      // CGB
      new CurrencyPair("CGB", "CAD"), // CGB/CAD
      new CurrencyPair("CGB", "USD"), // CGB/USD
      new CurrencyPair("CGB", "BTC"), // CGB/BTC
      new CurrencyPair("CGB", "LTC"), // CGB/LTC
      new CurrencyPair("CGB", "PPC"), // CGB/PPC
      new CurrencyPair("CGB", "DOGE"), // CGB/DOGE
      new CurrencyPair("CGB", "FTC"), // CGB/FTC
      new CurrencyPair("CGB", "XPM"), // CGB/XPM
      new CurrencyPair("CGB", "QRK"), // CGB/QRK
      new CurrencyPair("CGB", "VTC"), // CGB/VTC
      new CurrencyPair("CGB", "AUR"), // CGB/AUR
      new CurrencyPair("CGB", "DGC"), // CGB/DGC
      new CurrencyPair("CGB", "MINT"), // CGB/MINT
      new CurrencyPair("CGB", "DRK"), // CGB/DRK
      new CurrencyPair("CGB", "WDC"), // CGB/WDC
      new CurrencyPair("CGB", "BC"), // CGB/BC
      new CurrencyPair("CGB", "ZET"), // CGB/ZET
      new CurrencyPair("CGB", "FLT"), // CGB/FLT
      new CurrencyPair("CGB", "NVC"), // CGB/NVC
      new CurrencyPair("CGB", "EMC2"), // CGB/EMC2
      new CurrencyPair("CGB", "BIL"), // CGB/BIL
      new CurrencyPair("CGB", "NOBL"), // CGB/NOBL

      // DGC
      new CurrencyPair("DGC", "CAD"), // DGC/CAD
      new CurrencyPair("DGC", "USD"), // DGC/USD
      new CurrencyPair("DGC", "BTC"), // DGC/BTC
      new CurrencyPair("DGC", "LTC"), // DGC/LTC
      new CurrencyPair("DGC", "PPC"), // DGC/PPC
      new CurrencyPair("DGC", "DOGE"), // DGC/DOGE
      new CurrencyPair("DGC", "FTC"), // DGC/FTC
      new CurrencyPair("DGC", "XPM"), // DGC/XPM
      new CurrencyPair("DGC", "QRK"), // DGC/QRK
      new CurrencyPair("DGC", "VTC"), // DGC/VTC
      new CurrencyPair("DGC", "AUR"), // DGC/AUR
      new CurrencyPair("DGC", "CGB"), // DGC/CGB
      new CurrencyPair("DGC", "MINT"), // DGC/MINT
      new CurrencyPair("DGC", "DRK"), // DGC/DRK
      new CurrencyPair("DGC", "WDC"), // DGC/WDC
      new CurrencyPair("DGC", "BC"), // DGC/BC
      new CurrencyPair("DGC", "ZET"), // DGC/ZET
      new CurrencyPair("DGC", "FLT"), // DGC/FLT
      new CurrencyPair("DGC", "NVC"), // DGC/NVC
      new CurrencyPair("DGC", "EMC2"), // DGC/EMC2
      new CurrencyPair("DGC", "BIL"), // DGC/BIL
      new CurrencyPair("DGC", "NOBL"), // DGC/NOBL

      // MINT
      new CurrencyPair("MINT", "CAD"), // MINT/CAD
      new CurrencyPair("MINT", "USD"), // MINT/USD
      new CurrencyPair("MINT", "BTC"), // MINT/BTC
      new CurrencyPair("MINT", "LTC"), // MINT/LTC
      new CurrencyPair("MINT", "PPC"), // MINT/PPC
      new CurrencyPair("MINT", "DOGE"), // MINT/DOGE
      new CurrencyPair("MINT", "FTC"), // MINT/FTC
      new CurrencyPair("MINT", "XPM"), // MINT/XPM
      new CurrencyPair("MINT", "QRK"), // MINT/QRK
      new CurrencyPair("MINT", "VTC"), // MINT/VTC
      new CurrencyPair("MINT", "AUR"), // MINT/AUR
      new CurrencyPair("MINT", "CGB"), // MINT/CGB
      new CurrencyPair("MINT", "DGC"), // MINT/DGC
      new CurrencyPair("MINT", "DRK"), // MINT/DRK
      new CurrencyPair("MINT", "WDC"), // MINT/WDC
      new CurrencyPair("MINT", "BC"), // MINT/BC
      new CurrencyPair("MINT", "ZET"), // MINT/ZET
      new CurrencyPair("MINT", "FLT"), // MINT/FLT
      new CurrencyPair("MINT", "NVC"), // MINT/NVC
      new CurrencyPair("MINT", "EMC2"), // MINT/EMC2
      new CurrencyPair("MINT", "BIL"), // MINT/BIL
      new CurrencyPair("MINT", "NOBL"), // MINT/NOBL

      // DRK
      new CurrencyPair("DRK", "CAD"), // DRK/CAD
      new CurrencyPair("DRK", "USD"), // DRK/USD
      new CurrencyPair("DRK", "BTC"), // DRK/BTC
      new CurrencyPair("DRK", "LTC"), // DRK/LTC
      new CurrencyPair("DRK", "PPC"), // DRK/PPC
      new CurrencyPair("DRK", "DOGE"), // DRK/DOGE
      new CurrencyPair("DRK", "FTC"), // DRK/FTC
      new CurrencyPair("DRK", "XPM"), // DRK/XPM
      new CurrencyPair("DRK", "QRK"), // DRK/QRK
      new CurrencyPair("DRK", "VTC"), // DRK/VTC
      new CurrencyPair("DRK", "AUR"), // DRK/AUR
      new CurrencyPair("DRK", "CGB"), // DRK/CGB
      new CurrencyPair("DRK", "DGC"), // DRK/DGC
      new CurrencyPair("DRK", "MINT"), // DRK/MINT
      new CurrencyPair("DRK", "WDC"), // DRK/WDC
      new CurrencyPair("DRK", "BC"), // DRK/BC
      new CurrencyPair("DRK", "ZET"), // DRK/ZET
      new CurrencyPair("DRK", "FLT"), // DRK/FLT
      new CurrencyPair("DRK", "NVC"), // DRK/NVC
      new CurrencyPair("DRK", "EMC2"), // DRK/EMC2
      new CurrencyPair("DRK", "BIL"), // DRK/BIL
      new CurrencyPair("DRK", "NOBL"), // DRK/NOBL

      // WDC
      new CurrencyPair("WDC", "CAD"), // WDC/CAD
      new CurrencyPair("WDC", "USD"), // WDC/USD
      new CurrencyPair("WDC", "BTC"), // WDC/BTC
      new CurrencyPair("WDC", "LTC"), // WDC/LTC
      new CurrencyPair("WDC", "PPC"), // WDC/PPC
      new CurrencyPair("WDC", "DOGE"), // WDC/DOGE
      new CurrencyPair("WDC", "FTC"), // WDC/FTC
      new CurrencyPair("WDC", "XPM"), // WDC/XPM
      new CurrencyPair("WDC", "QRK"), // WDC/QRK
      new CurrencyPair("WDC", "VTC"), // WDC/VTC
      new CurrencyPair("WDC", "AUR"), // WDC/AUR
      new CurrencyPair("WDC", "CGB"), // WDC/CGB
      new CurrencyPair("WDC", "DGC"), // WDC/DGC
      new CurrencyPair("WDC", "MINT"), // WDC/MINT
      new CurrencyPair("WDC", "DRK"), // WDC/DRK
      new CurrencyPair("WDC", "BC"), // WDC/BC
      new CurrencyPair("WDC", "ZET"), // WDC/ZET
      new CurrencyPair("WDC", "FLT"), // WDC/FLT
      new CurrencyPair("WDC", "NVC"), // WDC/NVC
      new CurrencyPair("WDC", "EMC2"), // WDC/EMC2
      new CurrencyPair("WDC", "BIL"), // WDC/BIL
      new CurrencyPair("WDC", "NOBL"), // WDC/NOBL

      // BC
      new CurrencyPair("BC", "CAD"), // BC/CAD
      new CurrencyPair("BC", "USD"), // BC/USD
      new CurrencyPair("BC", "BTC"), // BC/BTC
      new CurrencyPair("BC", "LTC"), // BC/LTC
      new CurrencyPair("BC", "PPC"), // BC/PPC
      new CurrencyPair("BC", "DOGE"), // BC/DOGE
      new CurrencyPair("BC", "FTC"), // BC/FTC
      new CurrencyPair("BC", "XPM"), // BC/XPM
      new CurrencyPair("BC", "QRK"), // BC/QRK
      new CurrencyPair("BC", "VTC"), // BC/VTC
      new CurrencyPair("BC", "AUR"), // BC/AUR
      new CurrencyPair("BC", "CGB"), // BC/CGB
      new CurrencyPair("BC", "DGC"), // BC/DGC
      new CurrencyPair("BC", "MINT"), // BC/MINT
      new CurrencyPair("BC", "DRK"), // BC/DRK
      new CurrencyPair("BC", "WDC"), // BC/WDC
      new CurrencyPair("BC", "ZET"), // BC/ZET
      new CurrencyPair("BC", "FLT"), // BC/FLT
      new CurrencyPair("BC", "NVC"), // BC/NVC
      new CurrencyPair("BC", "EMC2"), // BC/EMC2
      new CurrencyPair("BC", "BIL"), // BC/BIL
      new CurrencyPair("BC", "NOBL"), // BC/NOBL

      // ZET
      new CurrencyPair("ZET", "CAD"), // ZET/CAD
      new CurrencyPair("ZET", "USD"), // ZET/USD
      new CurrencyPair("ZET", "BTC"), // ZET/BTC
      new CurrencyPair("ZET", "LTC"), // ZET/LTC
      new CurrencyPair("ZET", "PPC"), // ZET/PPC
      new CurrencyPair("ZET", "DOGE"), // ZET/DOGE
      new CurrencyPair("ZET", "FTC"), // ZET/FTC
      new CurrencyPair("ZET", "XPM"), // ZET/XPM
      new CurrencyPair("ZET", "QRK"), // ZET/QRK
      new CurrencyPair("ZET", "VTC"), // ZET/VTC
      new CurrencyPair("ZET", "AUR"), // ZET/AUR
      new CurrencyPair("ZET", "CGB"), // ZET/CGB
      new CurrencyPair("ZET", "DGC"), // ZET/DGC
      new CurrencyPair("ZET", "MINT"), // ZET/MINT
      new CurrencyPair("ZET", "DRK"), // ZET/DRK
      new CurrencyPair("ZET", "WDC"), // ZET/WDC
      new CurrencyPair("ZET", "BC"), // ZET/BC
      new CurrencyPair("ZET", "FLT"), // ZET/FLT
      new CurrencyPair("ZET", "NVC"), // ZET/NVC
      new CurrencyPair("ZET", "EMC2"), // ZET/EMC2
      new CurrencyPair("ZET", "BIL"), // ZET/BIL
      new CurrencyPair("ZET", "NOBL"), // ZET/NOBL

      // FLT
      new CurrencyPair("FLT", "CAD"), // FLT/CAD
      new CurrencyPair("FLT", "USD"), // FLT/USD
      new CurrencyPair("FLT", "BTC"), // FLT/BTC
      new CurrencyPair("FLT", "LTC"), // FLT/LTC
      new CurrencyPair("FLT", "PPC"), // FLT/PPC
      new CurrencyPair("FLT", "DOGE"), // FLT/DOGE
      new CurrencyPair("FLT", "FTC"), // FLT/FTC
      new CurrencyPair("FLT", "XPM"), // FLT/XPM
      new CurrencyPair("FLT", "QRK"), // FLT/QRK
      new CurrencyPair("FLT", "VTC"), // FLT/VTC
      new CurrencyPair("FLT", "AUR"), // FLT/AUR
      new CurrencyPair("FLT", "CGB"), // FLT/CGB
      new CurrencyPair("FLT", "DGC"), // FLT/DGC
      new CurrencyPair("FLT", "MINT"), // FLT/MINT
      new CurrencyPair("FLT", "DRK"), // FLT/DRK
      new CurrencyPair("FLT", "WDC"), // FLT/WDC
      new CurrencyPair("FLT", "BC"), // FLT/BC
      new CurrencyPair("FLT", "ZET"), // FLT/ZET
      new CurrencyPair("FLT", "NVC"), // FLT/NVC
      new CurrencyPair("FLT", "EMC2"), // FLT/EMC2
      new CurrencyPair("FLT", "BIL"), // FLT/BIL
      new CurrencyPair("FLT", "NOBL"), // FLT/NOBL

      // NVC
      new CurrencyPair("NVC", "CAD"), // NVC/CAD
      new CurrencyPair("NVC", "USD"), // NVC/USD
      new CurrencyPair("NVC", "BTC"), // NVC/BTC
      new CurrencyPair("NVC", "LTC"), // NVC/LTC
      new CurrencyPair("NVC", "PPC"), // NVC/PPC
      new CurrencyPair("NVC", "DOGE"), // NVC/DOGE
      new CurrencyPair("NVC", "FTC"), // NVC/FTC
      new CurrencyPair("NVC", "XPM"), // NVC/XPM
      new CurrencyPair("NVC", "QRK"), // NVC/QRK
      new CurrencyPair("NVC", "VTC"), // NVC/VTC
      new CurrencyPair("NVC", "AUR"), // NVC/AUR
      new CurrencyPair("NVC", "CGB"), // NVC/CGB
      new CurrencyPair("NVC", "DGC"), // NVC/DGC
      new CurrencyPair("NVC", "MINT"), // NVC/MINT
      new CurrencyPair("NVC", "DRK"), // NVC/DRK
      new CurrencyPair("NVC", "WDC"), // NVC/WDC
      new CurrencyPair("NVC", "BC"), // NVC/BC
      new CurrencyPair("NVC", "ZET"), // NVC/ZET
      new CurrencyPair("NVC", "FLT"), // NVC/FLT
      new CurrencyPair("NVC", "EMC2"), // NVC/EMC2
      new CurrencyPair("NVC", "BIL"), // NVC/BIL
      new CurrencyPair("NVC", "NOBL"), // NVC/NOBL

      // EMC2
      new CurrencyPair("EMC2", "CAD"), // EMC2/CAD
      new CurrencyPair("EMC2", "USD"), // EMC2/USD
      new CurrencyPair("EMC2", "BTC"), // EMC2/BTC
      new CurrencyPair("EMC2", "LTC"), // EMC2/LTC
      new CurrencyPair("EMC2", "PPC"), // EMC2/PPC
      new CurrencyPair("EMC2", "DOGE"), // EMC2/DOGE
      new CurrencyPair("EMC2", "FTC"), // EMC2/FTC
      new CurrencyPair("EMC2", "XPM"), // EMC2/XPM
      new CurrencyPair("EMC2", "QRK"), // EMC2/QRK
      new CurrencyPair("EMC2", "VTC"), // EMC2/VTC
      new CurrencyPair("EMC2", "AUR"), // EMC2/AUR
      new CurrencyPair("EMC2", "CGB"), // EMC2/CGB
      new CurrencyPair("EMC2", "DGC"), // EMC2/DGC
      new CurrencyPair("EMC2", "MINT"), // EMC2/MINT
      new CurrencyPair("EMC2", "DRK"), // EMC2/DRK
      new CurrencyPair("EMC2", "WDC"), // EMC2/WDC
      new CurrencyPair("EMC2", "BC"), // EMC2/BC
      new CurrencyPair("EMC2", "ZET"), // EMC2/ZET
      new CurrencyPair("EMC2", "FLT"), // EMC2/FLT
      new CurrencyPair("EMC2", "NVC"), // EMC2/NVC
      new CurrencyPair("EMC2", "BIL"), // EMC2/BIL
      new CurrencyPair("EMC2", "NOBL"), // EMC2/NOBL

      // BIL
      new CurrencyPair("BIL", "CAD"), // BIL/CAD
      new CurrencyPair("BIL", "USD"), // BIL/USD
      new CurrencyPair("BIL", "BTC"), // BIL/BTC
      new CurrencyPair("BIL", "LTC"), // BIL/LTC
      new CurrencyPair("BIL", "PPC"), // BIL/PPC
      new CurrencyPair("BIL", "DOGE"), // BIL/DOGE
      new CurrencyPair("BIL", "FTC"), // BIL/FTC
      new CurrencyPair("BIL", "XPM"), // BIL/XPM
      new CurrencyPair("BIL", "QRK"), // BIL/QRK
      new CurrencyPair("BIL", "VTC"), // BIL/VTC
      new CurrencyPair("BIL", "AUR"), // BIL/AUR
      new CurrencyPair("BIL", "CGB"), // BIL/CGB
      new CurrencyPair("BIL", "DGC"), // BIL/DGC
      new CurrencyPair("BIL", "MINT"), // BIL/MINT
      new CurrencyPair("BIL", "DRK"), // BIL/DRK
      new CurrencyPair("BIL", "WDC"), // BIL/WDC
      new CurrencyPair("BIL", "BC"), // BIL/BC
      new CurrencyPair("BIL", "ZET"), // BIL/ZET
      new CurrencyPair("BIL", "FLT"), // BIL/FLT
      new CurrencyPair("BIL", "NVC"), // BIL/NVC
      new CurrencyPair("BIL", "EMC2"), // BIL/EMC2
      new CurrencyPair("BIL", "NOBL"), // BIL/NOBL

      // NOBL
      new CurrencyPair("NOBL", "CAD"), // NOBL/CAD
      new CurrencyPair("NOBL", "USD"), // NOBL/USD
      new CurrencyPair("NOBL", "BTC"), // NOBL/BTC
      new CurrencyPair("NOBL", "LTC"), // NOBL/LTC
      new CurrencyPair("NOBL", "PPC"), // NOBL/PPC
      new CurrencyPair("NOBL", "DOGE"), // NOBL/DOGE
      new CurrencyPair("NOBL", "FTC"), // NOBL/FTC
      new CurrencyPair("NOBL", "XPM"), // NOBL/XPM
      new CurrencyPair("NOBL", "QRK"), // NOBL/QRK
      new CurrencyPair("NOBL", "VTC"), // NOBL/VTC
      new CurrencyPair("NOBL", "AUR"), // NOBL/AUR
      new CurrencyPair("NOBL", "CGB"), // NOBL/CGB
      new CurrencyPair("NOBL", "DGC"), // NOBL/DGC
      new CurrencyPair("NOBL", "MINT"), // NOBL/MINT
      new CurrencyPair("NOBL", "DRK"), // NOBL/DRK
      new CurrencyPair("NOBL", "WDC"), // NOBL/WDC
      new CurrencyPair("NOBL", "BC"), // NOBL/BC
      new CurrencyPair("NOBL", "ZET"), // NOBL/ZET
      new CurrencyPair("NOBL", "FLT"), // NOBL/FLT
      new CurrencyPair("NOBL", "NVC"), // NOBL/NVC
      new CurrencyPair("NOBL", "EMC2"), // NOBL/EMC2
      new CurrencyPair("NOBL", "BIL") // NOBL/BIL

      );

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VaultOfSatoshiBaseService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return CURRENCY_PAIRS;
  }
}
