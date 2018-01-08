package com.gaelanbolger.nanopoolmonitor.repository;

import android.arch.lifecycle.LiveData;

import com.gaelanbolger.nanopoolmonitor.AppExecutors;
import com.gaelanbolger.nanopoolmonitor.db.AccountDao;
import com.gaelanbolger.nanopoolmonitor.vo.Account;

import java.util.List;

import javax.inject.Inject;

public class AccountRepository {

    private AppExecutors appExecutors;
    private AccountDao accountDao;

    @Inject
    public AccountRepository(AppExecutors appExecutors, AccountDao accountDao) {
        this.appExecutors = appExecutors;
        this.accountDao = accountDao;
    }

    public LiveData<List<Account>> loadAccounts() {
        return accountDao.getAll();
    }

    public void insertAccount(Account account) {
        appExecutors.diskIO().execute(() -> accountDao.insert(account));
    }

    public void deleteAccount(Account account) {
        appExecutors.diskIO().execute(() -> accountDao.delete(account));
    }
}
