package chapter02

//2.1.1　Hello,world!18
fun main(args: Array<String>) {
    println("Hello World")
}

//2.1.2　函数18
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

//表达式函数体
fun max2(a: Int, b: Int): Int = if (a > b) a else b

//还可以进一步简化max 函数，省掉返回类型：
fun max3(a: Int, b: Int) = if (a > b) a else b

//2.1.3　变量20
val str1 = "this is string"
val int1 = 11
val int2: Int = 22
val year = 7.5e6 //7.5*1000000
//可变变量和不可变量
//val value 只能初始化一次
//var variable

fun printInt() {
    val name = "kotlin";
    //字符串模板$name
    print("hello,$name")
    //引用更复杂的表达式 {}包裹起来
    val array = listOf<Int>(1)
    print("hello,${array[0]}")
}

//2.1.4　更简单的字符串格式化 ：字符串模板 22