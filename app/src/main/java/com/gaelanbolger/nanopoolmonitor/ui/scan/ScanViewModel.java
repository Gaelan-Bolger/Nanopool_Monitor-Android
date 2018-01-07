package com.gaelanbolger.nanopoolmonitor.ui.scan;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.gaelanbolger.nanopoolmonitor.repository.AccountRepository;
import com.gaelanbolger.nanopoolmonitor.vo.Account;

import java.util.List;

import javax.inject.Inject;

public class ScanViewModel extends ViewModel {

    private AccountRepository accountRepository;

    @Inject
    public ScanViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public LiveData<List<Account>> getAccounts() {
        return accountRepository.loadAccounts();
    }

    public void insertAccount(String address) {
        accountRepository.insertAccount(address);
    }
}
