package com.gaelanbolger.nanopoolmonitor.di;

import android.app.Application;

import com.gaelanbolger.nanopoolmonitor.NanopoolMonitorApplication;

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
        ActivityBindingModule.class
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
