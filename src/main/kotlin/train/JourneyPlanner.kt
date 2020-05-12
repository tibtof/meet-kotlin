package train

inline class Percent(val value: Int)

class JourneyPlanner(private val trains: Set<Train>) {
    val stations: Set<Station> = trains.flatMap(Train::stations).toSet()

    fun trainsAt(station: Station): Set<Train> = trains.filter { it.stations.contains(station) }.toSet()

    fun stopsAt(station: Station): Set<Pair<Time, Train>> =
        trains.flatMap { train -> train.schedule.filter { it.second == station }.map { it.first to train } }.toSet()

    fun stopsAtWithForComprehension(station: Station): Set<Pair<Time, Train>> = `for`(
        { trains },
        { (_, schedule) -> schedule.filter { it.second == station } }
    ) { train, schedule -> schedule.first to train }
}

//Default upper bound: Any?
//two levels for comprehension
inline fun <T, K, R, reified C> `for`(firstBind: () -> Collection<T>,
                                      secondBind: (it: T) -> Collection<K>,
                                      yield: (t: T, k: K) -> R): C where C : Collection<R> {
    val ts = firstBind()
    val ks = ts.flatMap { secondBind(it).map { r -> it to r } }
    val rs = ks.map { yield(it.first, it.second) }
    return when (true) {
        listOf<R>() is C -> rs.toList() as C
        setOf<R>() is C -> rs.toSet() as C
        else -> throw UnsupportedOperationException()
    }
}

//3 level for comprehension
fun <T, K, S, R> `for`(firstBind: () -> Collection<T>,
                       secondBind: (t: T) -> Collection<K>,
                       thirdBind: (t: T, k: K) -> Collection<S>,
                       yield: (t: T, k: K, s: S) -> R): Set<R> {
    val ts = firstBind()
    val ks = ts.flatMap { secondBind(it).map { r -> it to r } }
    val ss = ks.flatMap { thirdBind(it.first, it.second).map { s -> Triple(it.first, it.second, s) } }
    val rs = ss.map { yield(it.first, it.second, it.third) }.toSet()
    return rs.toSet()
}
