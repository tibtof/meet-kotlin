package train

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class JourneyPlannerTest : FreeSpec({
    "journey planner should be built from a list of trains" {
        shouldNotThrowAny { JourneyPlanner(setOf(ice724, ice726)) }
    }

})
