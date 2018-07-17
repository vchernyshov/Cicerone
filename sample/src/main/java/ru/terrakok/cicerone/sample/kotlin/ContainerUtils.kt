package ru.terrakok.cicerone.sample.kotlin

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

interface FragmentCreator {
    fun create(tag: String): Fragment?
}

fun replaceContainer(fm: FragmentManager, containers: Map<String, Fragment>, tag: String) {
    fm.beginTransaction().apply {
        containers.forEach {
            when (tag) {
                it.key -> attach(it.value)
                else -> detach(it.value)
            }
        }
    }.commitNow()
}

fun initializeContainers(fm: FragmentManager, containers: MutableMap<String, Fragment>, creator: FragmentCreator, containerId: Int, tags: List<String>) {
    tags.forEach { tag ->
        containers[tag] = initializeSingleContainer(fm, creator, containerId, tag)
    }
}

fun initializeSingleContainer(fm: FragmentManager, creator: FragmentCreator, containerId: Int, tag: String): Fragment {
    var fragment = fm.findFragmentByTag(tag)
    if (fragment == null) {
        fragment = creator.create(tag)
        fm.beginTransaction()
            .add(containerId, fragment, tag)
            .detach(fragment)
            .commitNow()
    }
    return fragment
}