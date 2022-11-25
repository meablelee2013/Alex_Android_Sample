package com.oriente.aptsample.sample1.injectcustomobjectwithinject

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.oriente.aptsample.sample1.injectthirdparty.NetworkService
import javax.inject.Inject

class AnalyticsAdapter @Inject constructor(
    private var networkService: NetworkService,
    private var fragmentActivity: FragmentActivity
) {
    fun analytics() {
        Log.d("alex", "analytics")
    }
}