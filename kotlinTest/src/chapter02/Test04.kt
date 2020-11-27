package chapter02

import java.io.BufferedReader
import java.io.StringReader
import java.lang.IllegalArgumentException
import java.util.*

//2.4.1　“while”循环36

//Ko tlin 有while 循环和do - while 循环，它们的语法和Java 中相应的循环没有什么区别
//2.4.2　迭代数字 ：区间和数列 37

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz"
    i % 3 == 0 -> "Fizz"
    i % 5 == 0 -> "Buzz"
    else -> "$i"
}

fun main(args: Array<String>) {
    for (i in 1..100) {
        fizzBuzz(i)
    }
    for (i in 100 downTo 1 step 2) {
        println(fizzBuzz(i))
    }
    //2.4.3　迭代 map 38
    //使用TreeMap让键排序
    val binaryReps = TreeMap<Char, String>()
    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        //不需要调用get 和put
        binaryReps[c] = binary
    }
    for ((letter, binary) in binaryReps) {
        println("$letter=$binary")
    }
    val list = arrayListOf("10", "11", "1010")
    for ((index, element) in list.withIndex()) {
        println("$index:$element")
    }
    //2.4.4　使用“in”检查集合和区间的成员39
    println(isLetter('q'))
    println(isNotDigit('q'))

    //用in 检查作为when分支
    println(recognize('8'))
    //使用in运算符检查一个其他的对象是否属于这个区间
    println("kotlin" in "java".."scala")
    println("kotlin" in setOf("Java", "Scala"))


}

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'

fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "it is a digit"
    in 'a'..'z', in 'A'..'Z' -> "it is letter"
    else -> "i don't know"
}

