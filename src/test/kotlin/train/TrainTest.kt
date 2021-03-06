package train

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class TrainTest : FreeSpec({
    val testTrain = Train(TransEuropean(1),
        listOf("20:00".hours to Station("Amsterdam"),
            "10:00".hours to Station("Bucharest")))

    "Train constructor" - {
        "should sort stops ascendind by time" {
            testTrain.schedule shouldBe listOf("10:00".hours to Station("Bucharest"),
                "20:00".hours to Station("Amsterdam"))
        }

        "should not accept" - {
            listOf(
                row("no stations", emptyList()),
                row("one station", listOf("10:00".hours to Station("Bucharest")))
            ).map { (description, schedule) ->
                description {
                    shouldThrow<IllegalArgumentException> { Train(Regional(10), schedule) }
                }
            }
        }
    }

    "stations should be initialized correctly" {
        testTrain.stations shouldBe listOf(Station("Bucharest"), Station("Amsterdam"))
    }

    "timeAt should" - {
        "return the time for station en route" {
            testTrain.timeAt(Station("Bucharest")) shouldBe "10:00".hours
        }
        "return null for station not en route" {
            testTrain.timeAt(Station("Vienna")) shouldBe null
        }
    }

    "price supplement should be" - {
        listOf(row(Percent(0), Regional(1)),
            row(Percent(30), Intercity(1)),
            row(Percent(50), TransEuropean(1, false))
        ).map { (percent, kind) ->
            "$percent for type ${kind::class}" {
                val train = Train(kind, listOf("10:00".hours to Station("Bucharest"), "12:00".hours to Station("Constanta")))

                train.priceSupplement() shouldBe percent
            }
        }
    }

    "locationAt should return the train's location at the given time" - {
        listOf(
            row("09:00", Depot),
            row("10:00", Station("Bucharest")),
            row("11:00", EnRoute),
            row("20:00", Station("Amsterdam")),
            row("20:01", Depot)
        ).map { (time, expectedLocation) ->
            "$time - $expectedLocation" {
                retry(times = 10, until = { this is Station }) {
                    testTrain.locationAt(time.hours)
                } shouldBe expectedLocation
            }
        }
    }

})

/**
 * @param times how many times to retry
 * @param until stopping condition (functional literal with receiver)
 * @param body operation to repeat until it's result satisfies the stopping condition or the number of tries is reached
 */
tailrec fun <T> retry(times: Int, until: T.() -> Boolean, body: () -> T): T {
    val result = body()

    if (until(result) || times == 0) return result

    return retry(times - 1, until, body)
}
