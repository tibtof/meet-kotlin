package train

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class TrainTest : FreeSpec({
    val testTrain = Train(TransEuropean(1),
        listOf("10:00".hours to Station("Bucharest"),
            "20:00".hours to Station("Amsterdam")))

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

})
