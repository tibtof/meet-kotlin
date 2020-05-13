package train

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.comparables.beGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class TimeTest : FreeSpec({
    "time should have hours and minutes" {
        with(Time(1, 59)) {
            hours shouldBe 1
            minutes shouldBe 59
        }
    }

    "should have the default value 0 for minutes" {
        with(Time(12)) {
            hours shouldBe 12
            minutes shouldBe 0
        }
    }

})
