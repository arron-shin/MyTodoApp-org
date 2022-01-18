package me.jungseob.apps.mytodoapp.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import kotlin.random.Random
import org.apache.commons.lang3.RandomStringUtils

fun randomShortAlphanumeric(): String = RandomStringUtils.randomAlphanumeric(10)

fun randomNonNegativeLong(): Long = (0..Long.MAX_VALUE).random()

private val ZONE_ID_UTC = ZoneId.of("UTC")

private fun instantOfMidnightUtc(year: Int, month: Int, dayOfMonth: Int): Instant {
    val dateTime = OffsetDateTime.of(
        year, month, dayOfMonth,
        0 /* hour */, 0 /* minute */, 0 /* second */, 0 /* nanoOfSecond */,
        ZoneOffset.UTC
    )
    return dateTime.toInstant()
}

/**
 * Generates a random [Instant], in range [minYearInclusive, maxYearExclusive).
 * Years are considered as UTC times.
 */
fun randomInstantInYears(minYearInclusive: Int, maxYearExclusive: Int): Instant {
    val minTimeInclusive = instantOfMidnightUtc(year = minYearInclusive, month = 1, dayOfMonth = 1)
    val maxTimeExclusive = instantOfMidnightUtc(year = maxYearExclusive, month = 1, dayOfMonth = 1)
    val seconds = Random.nextLong(minTimeInclusive.epochSecond, maxTimeExclusive.epochSecond)
    val nanos = Random.nextLong(0, Duration.ofSeconds(1).toNanos())
    return Instant.ofEpochSecond(seconds, nanos)
}

fun randomInstantInYear2xxx() = randomInstantInYears(minYearInclusive = 2000, maxYearExclusive = 3000)

fun randomLocalDateTimeInYear2xxx(): LocalDateTime =
    LocalDateTime.ofInstant(randomInstantInYear2xxx().truncatedTo(ChronoUnit.MILLIS), ZONE_ID_UTC)
