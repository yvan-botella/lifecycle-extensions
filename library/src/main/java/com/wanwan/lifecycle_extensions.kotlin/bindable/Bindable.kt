package com.wanwan.lifecycle_callback.demo.protocol

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wanwan.lifecycle_callback.ActivityLifecycleRegister
import com.wanwan.lifecycle_callback.FragmentLifecycleRegister
import com.wanwan.lifecycle_callback.callback.ActivityLifecycleCallbacks
import com.wanwan.lifecycle_callback.callback.FragmentLifecycleCallbacks
import com.wanwan.lifecycle_callback.protocol.ActivityLifecycleImpl
import com.wanwan.lifecycle_callback.protocol.FragmentLifecycleImpl
import com.wanwan.lifecycle_callback.protocol.TAG

/**
 * Created by yvan.botella on 12/10/2017.
 */
interface Bindable<T>: TAG {

    var binding: View?

    companion object : ActivityLifecycleRegister, FragmentLifecycleRegister {

        override fun register(activity: ActivityLifecycleImpl) {
            activity.registerActivityCallback(activityLifecycle)
        }

        override fun register(fragment: FragmentLifecycleImpl) {
            fragment.registerFragmentCallback(fragmentLifecycle)
        }

        private val activityLifecycle = object : ActivityLifecycleCallbacks {
            override fun onCreate(activity: Activity, savedInstanceState: Bundle?) {
                val viewRoot = activity.findViewById<View>(android.R.id.content)

                if (activity is Bindable<*>) {
                    activity.binding = viewRoot
                }
            }
        }
        private val fragmentLifecycle = object : FragmentLifecycleCallbacks {
            override fun onCreateView(fragment: Fragment, result: View?, inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
                if (fragment is Bindable<*>) {
                    fragment.binding = result
                }
                return result
            }
        }
    }
}