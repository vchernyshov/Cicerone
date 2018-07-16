package ru.terrakok.cicerone.sample.kotlin

import ru.terrakok.cicerone.sample.R

abstract class AppTabContainerFragment : AbsTabContainerFragment() {

    override val tabContainerId: Int = R.id.ftc_container
    override val tabLayoutId: Int = R.layout.fragment_tab_container
}