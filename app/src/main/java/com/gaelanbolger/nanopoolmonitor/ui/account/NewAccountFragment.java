package com.gaelanbolger.nanopoolmonitor.ui.account;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaelanbolger.nanopoolmonitor.R;
import com.gaelanbolger.nanopoolmonitor.binding.FragmentDataBindingComponent;
import com.gaelanbolger.nanopoolmonitor.databinding.NewAccountFragmentBinding;
import com.gaelanbolger.nanopoolmonitor.di.Injectable;
import com.gaelanbolger.nanopoolmonitor.util.AndroidUtils;
import com.gaelanbolger.nanopoolmonitor.util.AutoClearedValue;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class NewAccountFragment extends Fragment implements Injectable {

    @Inject
    AccountNavigationController accountNavigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private AutoClearedValue<NewAccountFragmentBinding> binding;

    public static NewAccountFragment create() {
        return new NewAccountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NewAccountFragmentBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.new_account_fragment, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BarcodeCapture barcodeCapture = (BarcodeCapture) getChildFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(new BarcodeRetriever() {
            @Override
            public void onRetrieved(Barcode barcode) {
                Timber.d("onRetrieved: " + barcode);
                AndroidUtils.runOnUiThread(() -> {
                    accountNavigationController.popBackStack();
                    accountNavigationController.navigateToAccount(barcode.displayValue.contains(":")
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
    }
}
