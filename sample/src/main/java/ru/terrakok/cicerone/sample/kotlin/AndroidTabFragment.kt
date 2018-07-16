package ru.terrakok.cicerone.sample.kotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.sample.SampleApplication
import ru.terrakok.cicerone.sample.Screens
import ru.terrakok.cicerone.sample.ui.bottom.ForwardFragment
import ru.terrakok.cicerone.sample.ui.common.RouterProvider

class AndroidTabFragment : AppTabContainerFragment() {

    override val containerName: String = Screens.ANDROID_TAB
    override val initialChild: String = Screens.FORWARD_SCREEN
    override val navigator: Navigator by lazy {
        object : SupportAppNavigator(activity, childFragmentManager, tabContainerId) {
            override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
                if (screenKey == Screens.GITHUB_SCREEN) {
                    return Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"))
                }
                return null
            }

            override fun createFragment(screenKey: String?, data: Any?): Fragment {
                if (screenKey == Screens.FORWARD_SCREEN) {
                    return ForwardFragment.getNewInstance(containerName, data as Int)
                }
                throw IllegalArgumentException("Not supported screenKey $screenKey")
            }

            override fun exit() {
                (activity as RouterProvider).router.exit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
}