package ru.terrakok.cicerone.sample.java;

import ru.terrakok.cicerone.sample.R;

public abstract class AppTabContainerFragment extends AbsTabContainerFragment {

    @Override
    protected int tabContainerId() {
        return R.id.ftc_container;
    }

    @Override
    protected int tabLayoutId() {
        return R.layout.fragment_tab_container;
    }
}
