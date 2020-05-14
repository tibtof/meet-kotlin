package train

/**
 * sealed classes can be extended only in the current file
 * sealed classes are open and abstract by default
 */
sealed class TrainInfo(open val number: Int)

data class TransEuropean(override val number: Int, val hasWifi: Boolean = true) : TrainInfo(number)
data class Intercity(override val number: Int) : TrainInfo(number)
data class Regional(override val number: Int) : TrainInfo(number)
