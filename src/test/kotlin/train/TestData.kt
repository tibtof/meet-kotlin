package train

val munich = Station("Munich")

val nuremberg = Station("Nuremberg")

val frankfurt = Station("Frankfurt")

val cologne = Station("Cologne")

val essen = Station("Essen")

val ice724MunichTime = Time(8, 50)

val ice724NurembergTime = Time(10)

val ice724FrankfurtTime = Time(12, 10)

val ice724CologneTime = Time(13, 39)

val ice726MunichTime = Time(7, 50)

val ice726NurembergTime = Time(9)

val ice726FrankfurtTime = Time(11, 10)

val ice726CologneTime = Time(13, 2)

val ice724 = Train(
    InterCityExpress(724, false),
    listOf(
        ice724MunichTime to munich,
        ice724NurembergTime to nuremberg,
        ice724FrankfurtTime to frankfurt,
        ice724CologneTime to cologne
    )
)

val ice726 = Train(
    InterCityExpress(726, true),
    listOf(
        ice726MunichTime to munich,
        ice726NurembergTime to nuremberg,
        ice726FrankfurtTime to frankfurt,
        ice726CologneTime to essen
    )
)