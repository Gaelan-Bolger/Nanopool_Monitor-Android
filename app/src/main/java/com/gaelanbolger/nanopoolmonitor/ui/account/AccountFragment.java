package com.gaelanbolger.nanopoolmonitor.ui.account;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.api.ApiResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolResponse;
import com.gaelanbolger.nanopoolmonitor.api.NanopoolService;
import com.gaelanbolger.nanopoolmonitor.binding.FragmentDataBindingComponent;
import com.gaelanbolger.nanopoolmonitor.databinding.AccountFragmentBinding;
import com.gaelanbolger.nanopoolmonitor.di.Injectable;
import com.gaelanbolger.nanopoolmonitor.ui.common.NavigationController;
import com.gaelanbolger.nanopoolmonitor.util.AndroidUtils;
import com.gaelanbolger.nanopoolmonitor.util.AutoClearedValue;
import com.gaelanbolger.nanopoolmonitor.viewmodel.NanopoolViewModelFactory;
import com.gaelanbolger.nanopoolmonitor.vo.Account;

import javax.inject.Inject;

import timber.log.Timber;

public class AccountFragment extends Fragment implements Injectable {

    private static final String KEY_ADDRESS = "address";

    @Inject
    NanopoolViewModelFactory viewModelFactory;
    @Inject
    NavigationController navigationController;
    @Inject
    NanopoolService nanopoolService;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private AutoClearedValue<AccountFragmentBinding> binding;
    private AutoClearedValue<AccountAdapter> adapter;
    private AccountViewModel accountViewModel;

    public static AccountFragment create(String address) {
        AccountFragment fragment = new AccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ADDRESS, address);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AccountFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.account_fragment,
                container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AccountAdapter accountAdapter = new AccountAdapter(dataBindingComponent,
                account -> navigationController.navigateToUser(account.getAddress()));
        adapter = new AutoClearedValue<>(this, accountAdapter);
        accountViewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel.class);
        accountViewModel.getAccounts().observe(this, items -> adapter.get().replace(items));
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Account account = adapter.get().getItem(viewHolder.getAdapterPosition());
                accountViewModel.deleteAccount(account);
                Snackbar.make(binding.get().getRoot(), "Account deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", (v) -> {
                            accountViewModel.insertAccount(account);
                        }).show();
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(binding.get().accountList);
        binding.get().accountList.setAdapter(accountAdapter);
        binding.get().accountList.addItemDecoration(
                new DividerItemDecoration(binding.get().accountList.getContext(), DividerItemDecoration.VERTICAL)
        );
        binding.get().newAccount.setOnClickListener(this::onNewAccount);
        binding.get().submit.setOnClickListener(this::onAddAccount);
        if (getArguments() != null && getArguments().containsKey(KEY_ADDRESS)) {
            String address = getArguments().getString(KEY_ADDRESS, "");
            binding.get().input.setText(address);
            binding.get().input.setSelection(address.length());
        }
        binding.get().input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onAddAccount(v);
                return true;
            }
            return false;
        });
        binding.get().input.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                onAddAccount(v);
                return true;
            }
            return false;
        });
    }

    private void onNewAccount(View v) {
        Timber.d("onNewAccount: ");
        AndroidUtils.hideKeyboard(v);
        navigationController.navigateToNewAccount();
    }

    private void onAddAccount(View v) {
        Timber.d("onAddAccount: ");
        String address = binding.get().input.getText().toString();
        if (TextUtils.isEmpty(address)) {
            binding.get().input.setError("Address is required");
            return;
        }
        binding.get().submit.setEnabled(false);
        LiveData<ApiResponse<NanopoolResponse<Boolean>>> checkUser = nanopoolService.accountExist(address);
        checkUser.observe(this, new Observer<ApiResponse<NanopoolResponse<Boolean>>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<NanopoolResponse<Boolean>> apiResponse) {
                checkUser.removeObserver(this);
                binding.get().submit.setEnabled(true);
                if (apiResponse != null) {
                    if (apiResponse.body.isStatus()) {
                        accountViewModel.insertAccount(new Account(address));
                        binding.get().input.setError(null);
                        binding.get().input.setText(null);
                        AndroidUtils.hideKeyboard(v);
                        navigationController.navigateToUser(address);
                    } else {
                        Toast.makeText(getActivity(), apiResponse.body.getError(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
