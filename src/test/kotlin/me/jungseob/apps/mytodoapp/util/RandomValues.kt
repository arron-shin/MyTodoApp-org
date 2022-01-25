package me.jungseob.apps.mytodoapp.util

import java.time.Duration
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.random.Random
import org.apache.commons.lang3.RandomStringUtils

fun randomShortAlphanumeric(): String = RandomStringUtils.randomAlphanumeric(10)

fun randomNonNegativeLong(): Long = (0..Long.MAX_VALUE).random()

fun randomInstant(minYearInclusive: Int = 2000, maxYearExclusive: Int = 3000): Instant {
    val minTimeInclusive = instantOfMidnightUtc(year = minYearInclusive, month = 1, dayOfMonth = 1)
    val maxTimeExclusive = instantOfMidnightUtc(year = maxYearExclusive, month = 1, dayOfMonth = 1)
    val seconds = Random.nextLong(minTimeInclusive.epochSecond, maxTimeExclusive.epochSecond)
    val nanos = Random.nextLong(0, Duration.ofSeconds(1).toNanos())
    return Instant.ofEpochSecond(seconds, nanos)
}

private fun instantOfMidnightUtc(year: Int, month: Int, dayOfMonth: Int): Instant {
    val dateTime = OffsetDateTime.of(
        year, month, dayOfMonth,
        0 /* hour */, 0 /* minute */, 0 /* second */, 0 /* nanoOfSecond */,
        ZoneOffset.UTC
    )
    return dateTime.toInstant()
}
