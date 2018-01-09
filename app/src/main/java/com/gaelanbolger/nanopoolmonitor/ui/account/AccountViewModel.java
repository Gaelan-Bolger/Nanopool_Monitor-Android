package com.gaelanbolger.nanopoolmonitor.ui.account;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.gaelanbolger.nanopoolmonitor.repository.AccountRepository;
import com.gaelanbolger.nanopoolmonitor.repository.PaymentRepository;
import com.gaelanbolger.nanopoolmonitor.repository.ShareRepository;
import com.gaelanbolger.nanopoolmonitor.repository.UserRepository;
import com.gaelanbolger.nanopoolmonitor.repository.WorkerRepository;
import com.gaelanbolger.nanopoolmonitor.vo.Account;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel which saves user created Account objects
 */
public class AccountViewModel extends ViewModel {

    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private WorkerRepository workerRepository;
    private PaymentRepository paymentRepository;
    private ShareRepository shareRepository;

    @Inject
    public AccountViewModel(
            AccountRepository accountRepository,
            UserRepository userRepository,
            WorkerRepository workerRepository,
            PaymentRepository paymentRepository,
            ShareRepository shareRepository
    ) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.workerRepository = workerRepository;
        this.paymentRepository = paymentRepository;
        this.shareRepository = shareRepository;
    }

    public LiveData<List<Account>> getAccounts() {
        return accountRepository.loadAccounts();
    }

    public void insertAccount(Account account) {
        accountRepository.insertAccount(account);
    }

    public void deleteAccount(Account account) {
        accountRepository.deleteAccount(account);
        userRepository.deleteUser(account.getAddress());
        workerRepository.deleteAll(account.getAddress());
        paymentRepository.deleteAll(account.getAddress());
        shareRepository.deleteAll(account.getAddress());
    }
}
