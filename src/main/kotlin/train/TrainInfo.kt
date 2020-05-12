package train

sealed class TrainInfo(open val number: Int)

data class TransEuropean(override val number: Int, val hasWifi: Boolean = true) : TrainInfo(number)
data class Intercity(override val number: Int) : TrainInfo(number)
data class Regional(override val number: Int) : TrainInfo(number)
