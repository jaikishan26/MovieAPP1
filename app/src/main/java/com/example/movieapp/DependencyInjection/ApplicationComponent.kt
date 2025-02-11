package com.example.movieapp.DependencyInjection

import com.example.movieapp.MainActivity
import com.example.movieapp.MyApplication
import com.example.movieapp.ui.home.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, SubComponentModule::class, AppModule::class])
interface ApplicationComponent {
    fun homeComponent(): HomeComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: MyApplication): ApplicationComponent
    }

}

@Subcomponent
interface HomeComponent{
    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create():HomeComponent
    }
}

@Module(subcomponents = [HomeComponent::class])
class SubComponentModule {}
