package train

import java.util.concurrent.ThreadLocalRandom

typealias Stop = Pair<Time, Station>

class Train(val kind: TrainInfo, schedule: List<Stop>) {
    val schedule: List<Stop>

    init {
        require(schedule.size >= 2) { "schedule must contain at least two elements" }
        this.schedule = schedule.sortedBy { (time, _) -> time }
    }

    /**
     * Computed val
     * Lazy computation when the property is first accessed
     */
    val stations: List<Station> by lazy { this.schedule.map { it.second } }

    /**
     * Nullable return type - null safe navigation
     */
    fun timeAt(station: Station): Time? = schedule.find { (_, s) -> s == station }?.first

    /**
     * When matches the argument against all branches, until one the conditions is satisfied
     * When with full branch coverage on sealed classes (ADT)
     */
    fun priceSupplement(): Percent = when (kind) {
        is TransEuropean -> Percent(50)
        is Intercity -> Percent(30)
        is Regional -> Percent(0)
    }

    /**
     * When without argument evaluates the conditional expressions until one of them is satisfied
     */
    fun locationAt(time: Time): Location {
        //local (extension) function to add a randomly a delay of 1 minute
        fun Time.randomDelay(): Time = this.copy(minutes = minutes + ThreadLocalRandom.current().nextInt(2))

        return when {
            time < schedule.first().first -> Depot
            time > schedule.last().first -> Depot
            else -> schedule.find { it.first.randomDelay() == time }?.second ?: EnRoute
        }
    }

}

/**
 * An inline class must have a single property initialized in the primary constructor.
 * At runtime, instances of the inline class will be represented using this single property.
 * Used only by the compiler for type safety
 * (experimental starting with Kotlin 1.3)
 */
inline class Percent(val value: Int)

sealed class Location
data class Station(val name: String) : Location()
object Depot : Location()
object EnRoute : Location()
