package chapter03

fun main(args: Array<String>) {
    val strings: List<String> = listOf("one", "two", "three")
    println(strings.last())
    //to 不是内置的结构，而是一种特殊的函数调用，被称为中缀调用

    val map = mapOf(1 to "one", 2 to "two")

    //解构声明
    val (number, one) = 1 to "one"
    print(number)
    //to 函数是一个扩展函数，可以创建一对任何元素，这意味着它是泛型接收者的    扩展

    //显示创建一个正则表达式
    println("123.456-789.000".split("\\.|-".toRegex()))
    println("123.456-789.000".split(".", "-"))
    println(parsePath("/user/chapter.kt"))
    println(parsePath2("/user/chapter.kt"))

    val kotlinLogo = """| //
                        .|//
                        .|// \""".trimIndent()
    println(kotlinLogo.trimMargin("."))
    val price ="""${'$'} 99.9"""
    println(price)
}
//fun <T> List<T>.last():T
//fun Collection<Int>.max():Int
//多个参数上使用vararg 修饰符
//fun listOf<T>(vararg values:T)

//要允许使用中缀符号调用函数，需要使用infix 修饰符来标记它
//infix fun Any.to(other: Any) = Pair(this, other)

fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    println("Dir:$directory,name:$fileName,ext:$extension")
}

fun parsePath2(path:String){
    //三重引号的字符串 不需要使用转义字符
    val regex ="""(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult!=null){
        val(directory,fileName,extension)=matchResult.destructured
        println("Dir:$directory,name:$fileName,ext:$extension")
    }
}