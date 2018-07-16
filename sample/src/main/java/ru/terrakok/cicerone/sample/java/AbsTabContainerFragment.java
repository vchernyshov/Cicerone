package ru.terrakok.cicerone.sample.java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.subnavigation.LocalCiceroneHolder;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;
import ru.terrakok.cicerone.sample.ui.common.RouterProvider;

public abstract class AbsTabContainerFragment extends Fragment implements RouterProvider, BackButtonListener {

    @Inject
    public LocalCiceroneHolder ciceroneHolder;

    protected abstract int tabContainerId();

    protected abstract int tabLayoutId();

    protected abstract String containerName();

    protected abstract String initialChild();

    protected abstract Navigator navigator();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(tabLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getChildFragmentManager().findFragmentById(tabContainerId()) == null) {
            ciceroneHolder.getCicerone(containerName()).getRouter().replaceScreen(initialChild(), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ciceroneHolder.getCicerone(containerName()).getNavigatorHolder().setNavigator(navigator());
    }

    @Override
    public void onPause() {
        ciceroneHolder.getCicerone(containerName()).getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    @Override
    public Router getRouter() {
        return ciceroneHolder.getCicerone(containerName()).getRouter();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.ftc_container);
        if (fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            ((RouterProvider) getActivity()).getRouter().exit();
            return true;
        }
    }
}
