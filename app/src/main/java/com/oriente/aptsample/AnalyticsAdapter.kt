package com.oriente.aptsample

import android.util.Log
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

class AnalyticsAdapter @Inject constructor(
    private var networkService: NetworkService,
    private var fragmentActivity:FragmentActivity
) {
    fun analytics() {

        Log.d("alex", "analytics")

    }
}