package ru.terrakok.cicerone.sample.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.sample.subnavigation.LocalCiceroneHolder
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener
import ru.terrakok.cicerone.sample.ui.common.RouterProvider
import javax.inject.Inject

abstract class AbsTabContainerFragment : Fragment(), RouterProvider, BackButtonListener {

    @Inject
    internal lateinit var ciceroneHolder: LocalCiceroneHolder

    protected abstract val tabContainerId: Int
    protected abstract val tabLayoutId: Int
    protected abstract val containerName: String
    protected abstract val initialChild: String
    protected abstract val navigator: Navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(tabLayoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.findFragmentById(tabContainerId) == null) {
            ciceroneHolder.getCicerone(containerName).router.replaceScreen(initialChild, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        ciceroneHolder.getCicerone(containerName).navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        ciceroneHolder.getCicerone(containerName).navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun getRouter(): Router {
        return ciceroneHolder.getCicerone(containerName).router
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(tabContainerId)
        if (fragment is BackButtonListener && (fragment as BackButtonListener).onBackPressed()) {
            return true
        } else {
            (activity as RouterProvider).router.exit()
            return true
        }
    }
}