package train

sealed class TrainInfo(open val number: Int)

data class InterCityExpress(override val number: Int, val hasWifi: Boolean) : TrainInfo(number)
data class Express(override val number: Int) : TrainInfo(number)
data class Regional(override val number: Int) : TrainInfo(number)
object GenericTrain : TrainInfo(1)
