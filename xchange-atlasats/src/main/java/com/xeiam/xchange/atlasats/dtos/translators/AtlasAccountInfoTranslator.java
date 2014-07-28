/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.atlasats.dtos.translators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.atlasats.dtos.AtlasAccountInfo;
import com.xeiam.xchange.atlasats.dtos.AtlasPosition;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;

public class AtlasAccountInfoTranslator implements
		AtlasTranslator<AtlasAccountInfo, AccountInfo> {

	private AtlasPositionToWalletTranslator positionToWalletTranslator;

	@Override
	public AccountInfo translate(AtlasAccountInfo sourceObject) {
		String username;
		BigDecimal tradingFee;
		List<Wallet> wallets = new ArrayList<Wallet>();
		username = sourceObject.getAccountNumber();
		tradingFee = sourceObject.getCommission();
		List<AtlasPosition> positions = sourceObject.getPositions();
		for (AtlasPosition position : positions) {
			wallets.add(positionToWalletTranslator.translate(position));
		}
		AccountInfo accountInfo = new AccountInfo(username, tradingFee, wallets);
		return accountInfo;
	}

}
