package train

/**
 * Classes and methods are final by default, to make them inheritable we have to use open modifier
 *
 * Extension example: class Hour(hours: Int) : Time(hours, 0)
 * singleton: object Ten: Time(10, 0)
 *
 * : is used to extend classes or implement interfaces
 * the compiler figures out if we try to extend multiple classes
 *
 * Except covariance, generics are used similar to Java
 *
 * Any is the supertype of all types
 *
 */
data class Time(val hours: Int, val minutes: Int = 0) : Comparable<Time> {
    init {
        require(hours in 0..23) { "hours must be within 0 and 23" }
        require(minutes in 0..59) { "minutes must be within 0 and 59" }
    }

    /**
     * If yoneed a function or a property to be tied to a class rather than to instances of it,
     *   you can declare it inside a *companion object*
     * The *companion object* is a singleton, and its members can be accessed directly via the name of the containing class
     */
    companion object {
        /**
         * For Java interoperability, to have a true static method, we can use @JvmStatic
         */
        fun valueOf(timeText: String): Time {
            require(timeText.matches("""\d{1,2}:\d{2}""".toRegex())) { "text time should be of form 10:05 or 0:00" }
            val (minutes, hours) = timeText.split(':').map { it.toInt() }
            return Time(minutes, hours)
        }
    }

    /**
     * Computed property
     */
    val asMinutes: Int
        get() = hours * 60 + minutes

    /**
     * Smart cast with elvis and require, from nullable types to not nullable types
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

    /**
     * Override interface function
     * override keyword is required, this was the compile will fail if the interface definition becomes outdated
     */
    override fun compareTo(other: Time): Int = this - other

    /**
     * String interpolation
     * By using ${} we can use complex expressions
     */
    override fun toString(): String = "$hours:${minutes.toString().padStart(2, '0')}"

}

/**
 * Extension function
 * It will be compiled as a static method with the string as the first argument
 * To use it, the package where it is declare has to be imported
 *
 * The compiler generates a class having the name of the file
 * were it adds the top level functions/properties as static methods
 */
val String.hours: Time
    get() = Time.valueOf(this)
