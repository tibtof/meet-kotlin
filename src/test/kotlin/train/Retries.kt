package train

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.Duration

inline class Retries(val value: Int)
typealias Times = Pair<Retries, Duration>

val Int.millis: Duration
    get() = Duration.ofMillis(this.toLong())

infix fun Int.times(pause: Duration) = Times(Retries(this), pause)
fun pause(duration: Duration) = duration

tailrec fun <T> retry(retries: Times, until: (T) -> Boolean, body: () -> T): T {
    val result = body()

    if (until(result) || retries.isLastTry) return result

    runBlocking {
        delay(retries.second.toMillis())
    }
    return retry(retries.countDown(), until, body)
}

fun Times.countDown(): Times = Retries(first.value - 1) to second
val Times.isLastTry
    get() = first.value == 0
