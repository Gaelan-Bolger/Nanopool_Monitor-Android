package com.gaelanbolger.nanopoolmonitor.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gaelanbolger.nanopoolmonitor.db.AccountDao;
import com.gaelanbolger.nanopoolmonitor.db.NanopoolMonitorDatabase;
import com.gaelanbolger.nanopoolmonitor.db.PaymentDao;
import com.gaelanbolger.nanopoolmonitor.db.ShareDao;
import com.gaelanbolger.nanopoolmonitor.db.UserDao;
import com.gaelanbolger.nanopoolmonitor.db.WorkerDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
class AppModule {

    public AppModule() {
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    NanopoolMonitorDatabase provideDb(Application application) {
        return Room.databaseBuilder(
                application, NanopoolMonitorDatabase.class, "nanopool_monitor.db"
        ).build();
    }

    @Provides
    @Singleton
    AccountDao provideAccountDao(NanopoolMonitorDatabase database) {
        return database.accountDao();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(NanopoolMonitorDatabase database) {
        return database.userDao();
    }

    @Provides
    @Singleton
    WorkerDao provideWorkerDao(NanopoolMonitorDatabase database) {
        return database.workerDao();
    }

    @Provides
    @Singleton
    PaymentDao providePaymentDao(NanopoolMonitorDatabase database) {
        return database.paymentDao();
    }

    @Provides
    @Singleton
    ShareDao provideShareDao(NanopoolMonitorDatabase database) {
        return database.shareDao();
    }
}
