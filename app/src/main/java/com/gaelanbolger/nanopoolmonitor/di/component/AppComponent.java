package com.gaelanbolger.nanopoolmonitor.di.component;

import android.app.Application;

import com.gaelanbolger.nanopoolmonitor.NanopoolMonitorApplication;
import com.gaelanbolger.nanopoolmonitor.di.module.AppModule;
import com.gaelanbolger.nanopoolmonitor.di.module.NetModule;
import com.gaelanbolger.nanopoolmonitor.di.module.ServiceBindingModule;
import com.gaelanbolger.nanopoolmonitor.di.module.ActivityBindingModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        NetModule.class,
        ActivityBindingModule.class,
        ServiceBindingModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder baseUrl(@Named("baseUrl") String baseUrl);

        AppComponent build();
    }

    void inject(NanopoolMonitorApplication nanopoolMonitorApplication);
}
