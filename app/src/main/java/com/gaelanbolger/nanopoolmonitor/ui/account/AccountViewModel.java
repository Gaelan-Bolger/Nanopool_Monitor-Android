package com.gaelanbolger.nanopoolmonitor.ui.account;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.gaelanbolger.nanopoolmonitor.repository.AccountRepository;
import com.gaelanbolger.nanopoolmonitor.vo.Account;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel which saves user created Account objects
 */
public class AccountViewModel extends ViewModel {

    private AccountRepository accountRepository;

    @Inject
    public AccountViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public LiveData<List<Account>> getAccounts() {
        return accountRepository.loadAccounts();
    }

    public void insertAccount(Account account) {
        accountRepository.insertAccount(account);
    }

    public void deleteAccount(Account account) {
        accountRepository.deleteAccount(account);
    }
}
