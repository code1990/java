package chapter03

fun <T> joinToString(
        collection: Collection<T>,
        separator: String,
        prefix: String,
        postfix: String
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

//指定参数的默认值
fun <T> joinToString2(
        collection: Collection<T>,
        separator: String = ",",
        prefix: String = "",
        postfix: String = ""
) {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
}

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3)
    println(joinToString(list, "#", "[", "]"))
    //可以显式地标明一些参数的名称。
    println(joinToString(list, separator = "#", prefix = "[", postfix = "]"))

    println(joinToString2(list))
    println(joinToString2(list,"#"))
    println(joinToString2(list,"#","]","["))
}
//如果需要从Java 代码中做频繁的调用，而且希望
//它能对Java 的调用者更简便，可以用＠ JvmOverloads 注解它。这个指示编译
//器生成Java 重载函数，从最后一个开始省略每个参数。

//可以把这些函数直
//接放到代码文件的顶层，不用从属于任何的类。这些放在文件顶层的函数依然是包
//内的成员，如果你需要从包外访问它，则需要import， 但不再需要额外包一层。
fun joinToString3() {
    println(111)
}

//要改变包含Kotlin 顶层函数的生成的类的名称，需要为这个文件添加e
//JvmName 的注解，将其放到这个文件的开头，位于包名的前面：
//@file:JvmName("StringFunctions")

//顶层属性
var opCount =0
fun performOperation(){
    opCount++
}
fun reportOperation(){
    println("Operation performed $opCount times")
}
//一个静态的宇段
val UNIX_LINE_SEPARATOR="\n"
//以public static final
//const 来修饰它
const val UNIX_LINE_SEPARATOR2="\n"

