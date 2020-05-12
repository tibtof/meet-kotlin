package train

import java.util.concurrent.ThreadLocalRandom

data class Train(val kind: TrainInfo, val schedule: List<Pair<Time, Station>>) {
    init {
        require(schedule.size >= 2) { "schedule must contain at least two elements" }
    }

    val stations: List<Station> by lazy { schedule.map { it.second } }

    fun timeAt(station: Station): Time? = schedule.find { (_, s) -> s == station }?.first

    fun locationAt(time: Time): Location = when {
        time < schedule.first().first -> Depot
        time > schedule.last().first -> Depot
        else -> schedule.find { it.first.randomDelay() == time }?.second ?: EnRoute
    }

}

fun Time.randomDelay(): Time = this.copy(minutes = minutes + ThreadLocalRandom.current().nextInt(2))

sealed class Location
data class Station(val name: String) : Location()
object Depot : Location()
object EnRoute : Location()
