package chapter02

import java.io.BufferedReader
import java.io.StringReader
import java.lang.IllegalArgumentException

fun main(args: Array<String>) {

    val number = 1
    val percentage = if (number in 0..100) number else throw IllegalArgumentException("must in 0 and 100")
    println(percentage)
    if (percentage !in 0..100) {
        throw IllegalArgumentException("must in 0 and 100")
    }
    val reader = BufferedReader(StringReader("239"))
    println(readNumber(reader))
    println(readNumber2(reader))
    println(readNumber3(reader))
}


//像在Java中一样使用try
fun readNumber(reader: BufferedReader): Int? {
    try {
        val line = reader.readLine()
        return Integer.parseInt(line)
    } catch (e: NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
}

//把try 当作表达式使用
fun readNumber2(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (e: NumberFormatException) {
        return
    }
    println(number)
}

//在catch 中返回值
fun readNumber3(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (e: NumberFormatException) {
        null
    }
    println(number)
}