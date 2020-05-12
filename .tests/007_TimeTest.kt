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

    "time constructor should not accept " - {
        listOf(row("negative hour", -1, 0),
            row("too many hours", 25, 0),
            row("negative minutes", 0, -1),
            row("too many minutes", 0, 60)
        ).map { (description, hours, minutes) ->
            description {
                shouldThrow<IllegalArgumentException> { Time(hours, minutes) }
            }
        }
    }

    "asMinutes should convert to minutes" {
        Time(5, 15).asMinutes shouldBe 60 * 5 + 15
    }

    "toString should" - {
        "return friendly text" {
            Time(10, 30).toString() shouldBe "10:30"
        }
        "prefix single digits with 0" {
            Time(1, 7).toString() shouldBe "01:07"
        }
    }

    "minus should subtract two time instances" {
        Time(2, 30) - Time(0, 45) shouldBe 105
    }

    "time instances should be comparable" - {
        "gt" {
            Time(5, 0) should beGreaterThan(Time(4, 59))
        }
        "eq" {
            Time(12, 34).compareTo(Time(12, 34)) shouldBe 0
        }
        "lt" {
            Time(12, 55) should beGreaterThan(Time(12, 22))
        }
    }
})
