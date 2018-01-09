package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.gaelanbolger.nanopoolmonitor.repository.PaymentRepository;
import com.gaelanbolger.nanopoolmonitor.util.AbsentLiveData;
import com.gaelanbolger.nanopoolmonitor.vo.Payment;
import com.gaelanbolger.nanopoolmonitor.vo.Resource;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class UserPaymentsViewModel extends ViewModel {

    private MutableLiveData<String> address = new MutableLiveData<>();

    private final LiveData<Resource<List<Payment>>> payments;

    @Inject
    public UserPaymentsViewModel(PaymentRepository paymentRepository) {
        payments = Transformations.switchMap(address, address -> {
            if (address == null) {
                return AbsentLiveData.create();
            } else {
                return paymentRepository.findByAddress(address);
            }
        });
    }

    public void setAddress(String address) {
        if (Objects.equals(this.address.getValue(), address)) {
            return;
        }
        this.address.setValue(address);
    }

    public LiveData<Resource<List<Payment>>> getPayments() {
        return payments;
    }

    public void retry() {
        if (this.address.getValue() != null) {
            this.address.setValue(this.address.getValue());
        }
    }
}