/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sedky.applicationtests.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import com.sedky.applicationtests.R
import com.sedky.applicationtests.data.model.SleepNight
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.security.auth.login.LoginException

val TAG: String = "Util"
private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

fun convertDurationToFormatted(startMilli: Long, endMilli: Long, resources: Resources): String {
    val durationMilli = endMilli - startMilli
    val weekDayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(startMilli)
    return when {
        durationMilli < ONE_MINUTE_MILLIS -> {
            Log.i(TAG, "convertDurationToFormatted: Seconds")
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            resources.getString(R.string.seconds_length, seconds, weekDayString)
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            Log.i(TAG, "convertDurationToFormatted: Minutes")
            Log.i(TAG, "StartMilli: $startMilli , EndMilli: $endMilli , DurationMilli: $durationMilli")
            Log.i(TAG, "ONE_HOUR_MELLI: $ONE_HOUR_MILLIS")
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
            Log.i(TAG, "minutes: $minutes")
            resources.getString(R.string.minutes_length, minutes, weekDayString)
        }

        else -> {
            Log.i(TAG, "convertDurationToFormatted: hours")
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
            resources.getString(R.string.hours_length, hours, weekDayString)
        }
    }
}

/**
 * These functions create a formatted string that can be set in a TextView.
 */

/**
 * Returns a string representing the numeric quality rating.
 */
fun convertNumericQualityToString(quality: Int, resources: Resources): String {
    var qualityString = resources.getString(R.string.three_ok)
    when (quality) {
        -1 -> qualityString = "--"
        0 -> qualityString = resources.getString(R.string.zero_very_bad)
        1 -> qualityString = resources.getString(R.string.one_poor)
        2 -> qualityString = resources.getString(R.string.two_soso)
        4 -> qualityString = resources.getString(R.string.four_pretty_good)
        5 -> qualityString = resources.getString(R.string.five_excellent)
    }
    return qualityString
}


/**
 * Take the Long milliseconds returned by the system and stored in Room,
 * and convert it to a nicely formatted string for display.
 *
 * EEEE - Display the long letter version of the weekday
 * MMM - Display the letter abbreviation of the nmotny
 * dd-yyyy - day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format
 */
@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}

/**
 * Takes a list of SleepNights and converts and formats it into one string for display.
 *
 * For display in a TextView, we have to supply one string, and styles are per TextView, not
 * applicable per word. So, we build a formatted string using HTML. This is handy, but we will
 * learn a better way of displaying this data in a future lesson.
 *
 * @param   nights - List of all SleepNights in the database.
 * @param   resources - Resources object for all the resources defined for our app.
 *
 * @return  Spanned - An interface for text that has formatting attached to it.
 *           See: https://developer.android.com/reference/android/text/Spanned
 */

// TODO (11) Uncomment the code.

fun formatNights(nights: List<SleepNight>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTimeMelli)}<br>")
            if (it.endTimeMelli != it.startTimeMelli) {
                append(resources.getString(R.string.end_time))
                append("\t${convertLongToDateString(it.endTimeMelli)}<br>")
                append(resources.getString(R.string.quality))
                append("\t${convertNumericQualityToString(it.sleepQuality, resources)}<br>")
                append(resources.getString(R.string.hours_slept))
                // Hours
                append("\t ${it.endTimeMelli.minus(it.startTimeMelli) / 1000 / 60 / 60}:")
                // Minutes
                append("${it.endTimeMelli.minus(it.startTimeMelli) / 1000 / 60}:")
                // Seconds
                append("${it.endTimeMelli.minus(it.startTimeMelli) / 1000}<br><br>")
            }
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
