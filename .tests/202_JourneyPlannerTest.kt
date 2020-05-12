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

})
