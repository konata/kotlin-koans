package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)  : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return  value() - other.value()
    }

    fun value()  =  year * 1000 + month * 50 + dayOfMonth

    infix operator fun plus(time: TimeInterval): MyDate {
        return addTimeIntervals(time,time.times)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this,other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    var times = 1
    infix operator fun times(i: Int): TimeInterval {
        times = i
        return this
    }
}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> {
        var begin = start.copy()
        return object : Iterator<MyDate>{
            override fun hasNext(): Boolean {
                return endInclusive >= begin
            }

            override fun next(): MyDate {
                val ret = begin.copy()
                begin = begin.nextDay()
                return ret
            }
        }
    }

    infix operator fun contains(date: MyDate): Boolean {
        return date > start && date <= endInclusive
    }
}
