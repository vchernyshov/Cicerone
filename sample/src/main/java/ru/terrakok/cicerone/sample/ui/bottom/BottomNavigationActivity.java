package ru.terrakok.cicerone.sample.ui.bottom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.kotlin.AndroidTabFragment;
import ru.terrakok.cicerone.sample.kotlin.BugTabFragment;
import ru.terrakok.cicerone.sample.kotlin.DogTabFragment;
import ru.terrakok.cicerone.sample.mvp.bottom.BottomNavigationPresenter;
import ru.terrakok.cicerone.sample.mvp.bottom.BottomNavigationView;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;
import ru.terrakok.cicerone.sample.ui.common.RouterProvider;

/**
 * Created by terrakok 25.11.16
 */
public class BottomNavigationActivity extends MvpAppCompatActivity implements BottomNavigationView, RouterProvider {
    private BottomNavigationBar bottomNavigationBar;
    private Fragment androidTabFragment;
    private Fragment bugTabFragment;
    private Fragment dogTabFragment;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    @InjectPresenter
    BottomNavigationPresenter presenter;

    @ProvidePresenter
    public BottomNavigationPresenter createBottomNavigationPresenter() {
        return new BottomNavigationPresenter(router);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.ab_bottom_navigation_bar);

        initViews();
        initContainers();

        if (savedInstanceState == null) {
            bottomNavigationBar.selectTab(ANDROID_TAB_POSITION, true);
        }
    }

    private void initViews() {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_android_white_24dp, R.string.tab_android))
                .addItem(new BottomNavigationItem(R.drawable.ic_bug_report_white_24dp, R.string.tab_bug))
                .addItem(new BottomNavigationItem(R.drawable.ic_pets_white_24dp, R.string.tab_dog))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case ANDROID_TAB_POSITION:
                        presenter.onTabAndroidClick();
                        break;
                    case BUG_TAB_POSITION:
                        presenter.onTabBugClick();
                        break;
                    case DOG_TAB_POSITION:
                        presenter.onTabDogClick();
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                onTabSelected(position);
            }
        });

    }

    private void initContainers() {
        FragmentManager fm = getSupportFragmentManager();
        androidTabFragment = fm.findFragmentByTag(Screens.ANDROID_TAB);
        if (androidTabFragment == null) {
            androidTabFragment = new AndroidTabFragment();
            fm.beginTransaction()
                    .add(R.id.ab_container, androidTabFragment, Screens.ANDROID_TAB)
                    .detach(androidTabFragment).commitNow();
        }

        bugTabFragment = fm.findFragmentByTag(Screens.BUG_TAB);
        if (bugTabFragment == null) {
            bugTabFragment = new BugTabFragment();
            fm.beginTransaction()
                    .add(R.id.ab_container, bugTabFragment, Screens.BUG_TAB)
                    .detach(bugTabFragment).commitNow();
        }

        dogTabFragment = fm.findFragmentByTag(Screens.DOG_TAB);
        if (dogTabFragment == null) {
            dogTabFragment = new DogTabFragment();
            fm.beginTransaction()
                    .add(R.id.ab_container, dogTabFragment, Screens.DOG_TAB)
                    .detach(dogTabFragment).commitNow();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ab_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            presenter.onBackPressed();
        }
    }

    private Navigator navigator = new Navigator() {
        @Override
        public void applyCommands(Command[] commands) {
            for (Command command : commands) applyCommand(command);
        }

        private void applyCommand(Command command) {
            if (command instanceof Back) {
                finish();
            } else if (command instanceof Replace) {
                FragmentManager fm = getSupportFragmentManager();

                switch (((Replace) command).getScreenKey()) {
                    case Screens.ANDROID_SCREEN:
                        fm.beginTransaction()
                                .detach(bugTabFragment)
                                .detach(dogTabFragment)
                                .attach(androidTabFragment)
                                .commitNow();
                        break;
                    case Screens.BUG_SCREEN:
                        fm.beginTransaction()
                                .detach(androidTabFragment)
                                .detach(dogTabFragment)
                                .attach(bugTabFragment)
                                .commitNow();
                        break;
                    case Screens.DOG_SCREEN:
                        fm.beginTransaction()
                                .detach(androidTabFragment)
                                .detach(bugTabFragment)
                                .attach(dogTabFragment)
                                .commitNow();
                        break;
                }
            }
        }
    };

    @Override
    public void highlightTab(int position) {
        bottomNavigationBar.selectTab(position, false);
    }

    @Override
    public Router getRouter() {
        return router;
    }
}
