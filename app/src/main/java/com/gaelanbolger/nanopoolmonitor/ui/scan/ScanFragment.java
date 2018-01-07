package com.gaelanbolger.nanopoolmonitor.ui.scan;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.binding.FragmentDataBindingComponent;
import com.gaelanbolger.nanopoolmonitor.databinding.ScanFragmentBinding;
import com.gaelanbolger.nanopoolmonitor.di.Injectable;
import com.gaelanbolger.nanopoolmonitor.ui.common.NavigationController;
import com.gaelanbolger.nanopoolmonitor.util.AndroidUtils;
import com.gaelanbolger.nanopoolmonitor.util.AutoClearedValue;
import com.gaelanbolger.nanopoolmonitor.viewmodel.NanopoolViewModelFactory;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class ScanFragment extends Fragment implements Injectable {

    @Inject
    NanopoolViewModelFactory viewModelFactory;
    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private AutoClearedValue<ScanFragmentBinding> binding;
    private AutoClearedValue<AccountAdapter> adapter;
    private ScanViewModel scanViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ScanFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.scan_fragment,
                container, false, dataBindingComponent);
        this.binding = new AutoClearedValue<>(this, dataBinding);

        AccountAdapter adapter = new AccountAdapter(dataBindingComponent,
                account -> navigationController.navigateToUser(account.getAddress()));
        this.adapter = new AutoClearedValue<>(this, adapter);
        this.binding.get().accountList.setAdapter(adapter);
        this.binding.get().submit.setOnClickListener(this::showUser);
        this.binding.get().input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                showUser(v);
                return true;
            }
            return false;
        });
        this.binding.get().input.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && keyCode == KeyEvent.KEYCODE_ENTER) {
                showUser(v);
                return true;
            }
            return false;
        });

        BarcodeCapture barcodeCapture = (BarcodeCapture) getChildFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(new BarcodeRetriever() {
            @Override
            public void onRetrieved(Barcode barcode) {
                Timber.d("onRetrieved: " + barcode);
                AndroidUtils.runOnUiThread(() -> {
                    binding.get().input.setText(barcode.displayValue.contains(":")
                            ? barcode.displayValue.split(":")[1] : barcode.displayValue);
                });
            }

            @Override
            public void onRetrievedFailed(String reason) {
                Timber.e("onRetrievedFailed: " + reason);
            }

            @Override
            public void onPermissionRequestDenied() {
                Timber.w("onPermissionRequestDenied: ");
            }

            @Override
            public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {
            }

            @Override
            public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
            }
        });
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scanViewModel = ViewModelProviders.of(this, viewModelFactory).get(ScanViewModel.class);
        scanViewModel.getAccounts().observe(this, accounts -> adapter.get().setItems(accounts));
    }

    private void showUser(View v) {
        String address = binding.get().input.getText().toString();
        if (TextUtils.isEmpty(address)) {
            binding.get().input.setError("Address is required");
            return;
        }
        binding.get().setAddress(address);
        scanViewModel.insertAccount(address);
        navigationController.navigateToUser(address);
        AndroidUtils.hideKeyboard(v);
    }
}
