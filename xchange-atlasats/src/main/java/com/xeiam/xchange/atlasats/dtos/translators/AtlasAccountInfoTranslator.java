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
