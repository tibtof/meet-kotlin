package train

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class JourneyPlannerTest : FreeSpec({
    "journey planner should be built from a list of trains" {
        shouldNotThrowAny { JourneyPlanner(setOf(ice724, ice726)) }
    }

    "stations should contain all unique stations of all trains" {
        JourneyPlanner(setOf(ice724, ice726)).stations shouldBe setOf(munich, nuremberg, frankfurt, cologne, essen)
    }

    "price supplement should be" - {
        listOf(row(Percent(0), GenericTrain),
            row(Percent(20), Regional(1)),
            row(Percent(30), Express(1)),
            row(Percent(50), InterCityExpress(1, false))
        ).map { (percent, kind) ->
            "$percent for type ${kind::class}" {
                val train = Train(kind, listOf("10:00".hours to munich, "12:00".hours to nuremberg))
                val planner = JourneyPlanner(setOf(train))

                planner.priceSupplement(train) shouldBe percent
            }
        }
    }

    "trainsAt should return all trains that stop in that station" {
        JourneyPlanner(setOf(ice724, ice726)).trainsAt(munich) shouldBe setOf(ice724, ice726)
    }

    "stopsAt should return the trains and times for that station" {
        JourneyPlanner(setOf(ice724, ice726)).stopsAt(munich) shouldBe
            setOf(ice724MunichTime to ice724, ice726MunichTime to ice726)
    }
})
