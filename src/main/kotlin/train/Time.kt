package train

val String.hours: Time
    get() = Time.valueOf(this)

data class Time(val hours: Int, val minutes: Int = 0) : Comparable<Time> {
    init {
        require(hours in 0..23) { "hours must be within 0 and 23" }
        require(minutes in 0..59) { "minutes must be within 0 and 59" }
    }

    companion object {
        fun valueOf(s: String): Time {
            require(s.matches("""\d{2}:\d{2}""".toRegex())) { "text time should be of form 00:00" }
            val t = s.split(":").map { it.removePrefix("0").toInt() }
            return Time(t[0], t[1])
        }
    }

    val asMinutes: Int
        get() = hours * 60 + minutes

    /**
     * Smart compiler
     * show smart cast with elvis and require
     * Alternative implementation:
    fun minus(that: Time?): Int {
    that ?: throw NullPointerException("required")
    or: require(that != null)
    return this.asMinutes - that.asMinutes
    }
     */
    operator fun minus(that: Time): Int = this.asMinutes - that.asMinutes

    override fun compareTo(other: Time): Int = asMinutes - other.asMinutes

    override fun toString(): String =
        "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"

}
