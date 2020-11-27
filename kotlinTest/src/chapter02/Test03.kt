package chapter02

import java.lang.IllegalArgumentException

//Kotlin 中， en um 是一个所谓
//的软关键字：只有当它出现在c lass 前面时才有特殊的意义，在其他地方可以把
//它当作普通的名称使
enum class Color {
    RED, ORANGE, YELLOW, GREEN, BLUE
}

enum class Color2(
        val r: Int,
        val g: Int,
        val b: Int
) {
    //枚举必须分号结尾
    RED(255, 0, 0),
    ORANGE(255, 165, 0);

    //定义一个枚举方法
    fun rgb() = (r * 256 + g) * 256 + b
}

//使用“ when ”处理枚举类
fun getMnemonic(color: Color) {
    when (color) {
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "Gave"
    }
}

///在一个when 分支上合并多个选项
fun getWarmth(color: Color) = when (color) {
    Color.RED, Color.ORANGE -> "warm"
    Color.GREEN -> "Of"
    Color.BLUE -> "cold"
    else -> throw Exception("dirty color")
}

//在when 分支中使用不同的对象
fun mix(c1: Color, c2: Color) {
    //setOf 函数可以创建出一个Set
    when (setOf(c1, c2)) {
        setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
        setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
    }
}

//不带参数的when
fun mixOptimized(c1: Color, c2: Color) {
    when {
        (c1 == Color.RED && c2 == Color.YELLOW) || (c2 == Color.RED && c1 == Color.YELLOW) -> Color.ORANGE
        (c1 == Color.YELLOW && c2 == Color.BLUE) || (c2 == Color.YELLOW && c1 == Color.BLUE) -> Color.GREEN
        else -> throw Exception("dirty color")
    }
}

fun main(args: Array<String>) {
    println(getWarmth(Color.RED))
    println(getMnemonic(Color.RED))
    println(mix(Color.RED,Color.YELLOW))
    println(mixOptimized(Color.RED,Color.YELLOW))
}


//2.3.5 智能转换：合并类型检查和转换
interface Expr

//简单的值对象类，只有属性value ，实现了Expr接口
class Num(val value: Int) : Expr

class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int {
    //你要使用is 检查来判断一个变量是否是某种类型
    if (e is Num) {
        //使用as 关键字来表示到特定类型的显式转换：
        val n = e as Num
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left)
    }
    throw IllegalArgumentException("Unknown expression")
}

//重构：用“ when ”代替“ if”
fun eval2(e: Expr): Int =
        when (e) {
            is Num -> e.value
            is Sum -> eval2(e.right) + eval2(e.left)
            else -> 0
        }


//代码块作为“ if”和“ when ”的分支
fun evalWithLogging(e: Expr): Int =
        when (e) {
            is Num -> {
                println("num is ${e.value}")
                e.value
            }
            is Sum -> {
                val left = evalWithLogging(e.left)
                val right = evalWithLogging(e.right)
                println("sum is $right+$left")
                left + right
            }
            else -> 0
        }