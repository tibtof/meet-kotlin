package train

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.Duration

class TrainTest : FreeSpec({
    val testTrain = Train(GenericTrain,
        listOf("10:00".hours to Station("Bucharest"),
            "20:00".hours to Station("Amsterdam")))

    "Train constructor should not accept" - {
        listOf(
            row("no stations", emptyList()),
            row("one station", listOf("10:00".hours to Station("Bucharest")))
        ).map { (description, schedule) ->
            description {
                shouldThrow<IllegalArgumentException> { Train(GenericTrain, schedule) }
            }
        }
    }

})