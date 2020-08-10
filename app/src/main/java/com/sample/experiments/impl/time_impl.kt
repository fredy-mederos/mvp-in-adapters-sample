package com.sample.experiments.impl

import android.text.format.DateFormat
import com.sample.experiments.domain.FormatDate
import com.sample.experiments.domain.TimeProvider
import java.util.*
import javax.inject.Inject

class FormatDateImpl @Inject constructor() : FormatDate {
    override fun invoke(format: String, date: Date): String {
        return DateFormat.format(format, date).toString()
    }

}

class TimeProviderImpl @Inject constructor() : TimeProvider {
    override fun currentTime() = System.currentTimeMillis()
}