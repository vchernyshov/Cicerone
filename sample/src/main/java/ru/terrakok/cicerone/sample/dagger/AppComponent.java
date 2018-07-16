package ru.terrakok.cicerone.sample.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.terrakok.cicerone.sample.dagger.module.LocalNavigationModule;
import ru.terrakok.cicerone.sample.dagger.module.NavigationModule;
import ru.terrakok.cicerone.sample.kotlin.AndroidTabFragment;
import ru.terrakok.cicerone.sample.kotlin.BugTabFragment;
import ru.terrakok.cicerone.sample.kotlin.DogTabFragment;
import ru.terrakok.cicerone.sample.ui.animations.ProfileActivity;
import ru.terrakok.cicerone.sample.ui.animations.photos.SelectPhotoFragment;
import ru.terrakok.cicerone.sample.ui.animations.profile.ProfileFragment;
import ru.terrakok.cicerone.sample.ui.bottom.BottomNavigationActivity;
import ru.terrakok.cicerone.sample.ui.bottom.TabContainerFragment;
import ru.terrakok.cicerone.sample.ui.main.MainActivity;
import ru.terrakok.cicerone.sample.ui.main.SampleFragment;
import ru.terrakok.cicerone.sample.ui.start.StartActivity;

/**
 * Created by terrakok 24.11.16
 */

@Singleton
@Component(modules = {
        NavigationModule.class,
        LocalNavigationModule.class
})
public interface AppComponent {

    void inject(StartActivity activity);

    void inject(MainActivity activity);

    void inject(SampleFragment fragment);

    void inject(BottomNavigationActivity activity);

    void inject(TabContainerFragment fragment);

    void inject(ProfileFragment fragment);

    void inject(SelectPhotoFragment fragment);

    void inject(AndroidTabFragment fragment);

    void inject(BugTabFragment fragment);

    void inject(DogTabFragment fragment);

    void inject(ru.terrakok.cicerone.sample.java.AppTabContainerFragment fragment);

    void inject(ru.terrakok.cicerone.sample.java.BugTabFragment fragment);

    void inject(ru.terrakok.cicerone.sample.java.DogTabFragment fragment);

    void inject(ProfileActivity activity);
}
