package ru.terrakok.cicerone.sample.java;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.ui.bottom.ForwardFragment;

public class BugTabFragment extends AppTabContainerFragment {

    private Navigator navigator;

    @Override
    protected String containerName() {
        return Screens.BUG_TAB;
    }

    @Override
    protected String initialChild() {
        return Screens.FORWARD_SCREEN;
    }

    @Override
    protected Navigator navigator() {
        if (navigator == null) {
            navigator = new SupportAppNavigator(getActivity(), getChildFragmentManager(), tabContainerId()) {
                @Override
                protected Intent createActivityIntent(Context context, String screenKey, Object data) {
                    if (screenKey.equals(Screens.GITHUB_SCREEN)) {
                        return new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"));
                    }
                    return null;
                }

                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    if (screenKey.equals(Screens.FORWARD_SCREEN)) {
                        return ForwardFragment.getNewInstance(containerName(), (int) data);
                    }
                    return null;
                }
            };
        }
        return navigator;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }
}
