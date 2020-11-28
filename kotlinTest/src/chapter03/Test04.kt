package chapter03

fun main(args: Array<String>) {
    val c = "kotlin".lastChar()
    println(c)
    val list = listOf(1, 2, 3)
    println(list.joinToString(";", "(", ")"))
    val list2 = arrayListOf(1, 2, 3)
    println(list2.joinToString(";", "(", ")"))
    val button = Button()
    button.click()
    button.showOff()
    println("123".lastChar)
    val sb = StringBuilder("Kotlin?")
    sb.lastChar='!'
    println(sb)
}
//可以使用关键字出来修改导入的类或者函数名称：
//import strings.lastChar as last
//val c =” Kotlin".last()

fun <T> Collection<T>.joinToString(
        separator: String = ",",
        prefix: String = "",
        postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

open class View {
    open fun click() = println("View clicked")
}

class Button : View() {
    override fun click() = println("Button clicked")
}
//不能重写扩展函数
fun View.showOff()= println("I am view")
//扩展函数并不存在重写，因为Koti in 会把它们当作静态函数对待。
fun Button.showOff()= println("I am Button")
//声明一个扩展属性
val String.lastChar:Char
    get() = get(length-1)

var StringBuilder.lastChar:Char
    get() = get(length-1)
    set(value:Char) {
        this.setCharAt(length-1,value)
    }