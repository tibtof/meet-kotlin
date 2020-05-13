package train

data class Time(val hours: Int, val minutes: Int = 0) : Comparable<Time> {
    init {
        require(hours in 0..23) { "hours must be within 0 and 23" }
        require(minutes in 0..59) { "minutes must be within 0 and 59" }
    }

    companion object {
        fun valueOf(s: String): Time {
            require(s.matches("""\d{1,2}:\d{2}""".toRegex())) { "text time should be of form 10:05 or 0:00" }
            val (minutes, hours) = s.split(":")
                .map { if (it.length == 2) it.removePrefix("0") else it }
                .map { it.toInt() }
            return Time(minutes, hours)
        }
    }

    val asMinutes: Int
        get() = hours * 60 + minutes

    /**
     * Smart compiler
     * show smart cast with elvis and require
     * Alternative implementations:
     *   fun minus(that: Time?): Int {
     *       require(that != null)
     *       return this.asMinutes - that.asMinutes
     *   }

     *   fun minus(that: Time?): Int {
     *       that ?: throw NullPointerException("required")
     *       return this.asMinutes - that.asMinutes
     *   }
     */
    operator fun minus(that: Time): Int = this.asMinutes - that.asMinutes

    override fun compareTo(other: Time): Int = asMinutes - other.asMinutes

    override fun toString(): String =
        "$hours:${minutes.toString().padStart(2, '0')}"

}

val String.hours: Time
    get() = Time.valueOf(this)
