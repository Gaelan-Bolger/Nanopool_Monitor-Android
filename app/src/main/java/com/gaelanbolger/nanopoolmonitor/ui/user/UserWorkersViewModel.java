package com.gaelanbolger.nanopoolmonitor.ui.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.gaelanbolger.nanopoolmonitor.repository.WorkerRepository;
import com.gaelanbolger.nanopoolmonitor.util.AbsentLiveData;
import com.gaelanbolger.nanopoolmonitor.vo.Resource;
import com.gaelanbolger.nanopoolmonitor.vo.Worker;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class UserWorkersViewModel extends ViewModel {

    private MutableLiveData<String> address = new MutableLiveData<>();

    private final LiveData<Resource<List<Worker>>> workers;

    @Inject
    public UserWorkersViewModel(WorkerRepository workerRepository) {
        workers = Transformations.switchMap(address, address -> {
            if (address == null) {
                return AbsentLiveData.create();
            } else {
                return workerRepository.findByAddress(address);
            }
        });
    }

    public void setAddress(String address) {
        if (Objects.equals(this.address.getValue(), address)) {
            return;
        }
        this.address.setValue(address);
    }

    public LiveData<Resource<List<Worker>>> getWorkers() {
        return workers;
    }

    public void retry() {
        if (this.address.getValue() != null) {
            this.address.setValue(this.address.getValue());
        }
    }
}