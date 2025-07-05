package top.catnies.firenchantkt.util

object NumberUtils {
    // 罗马数字字符映射
    private val romanNumerals = listOf(
        1000 to "M", 900 to "CM", 500 to "D", 400 to "CD",
        100 to "C", 90 to "XC", 50 to "L", 40 to "XL",
        10 to "X", 9 to "IX", 5 to "V", 4 to "IV", 1 to "I"
    )

    // 将整数转成罗马数字
    fun Int.toRoman(): String {
        require(this > 0) { "负数无法转换成罗马数字" }
        var num = this
        return buildString {
            romanNumerals.forEach { (value, symbol) ->
                val count = num / value
                repeat(count) { append(symbol) }
                num %= value
            }
        }
    }
}