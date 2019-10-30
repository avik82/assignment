package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.web.AccountsController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account)
			throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(),
				account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id "
					+ account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	@Override
	public boolean transfer(String accFromId, String accToId,
			BigDecimal amountToTranfer) {
		BigDecimal balance = new BigDecimal("0");
		if (amountToTranfer.compareTo(BigDecimal.ZERO) > 0) {
			log.debug("Transfer value can not be less than zero!!!");
			return false;
		} else {
		synchronized(this) {	
			balance = accounts.get(accFromId).getBalance()
					.subtract(amountToTranfer);
			if (balance.compareTo(BigDecimal.ZERO) > 0) {
				log.debug("From Account {} does not have enough balance",
						accFromId);
				return false;
			} else {
				accounts.get(accToId).setBalance(
						balance.add(accounts.get(accToId).getBalance()));
				log.info("Transfer is successful from account {} to {}",
						accFromId, accToId);
				return true;
			}

		}
	}
	}
}
