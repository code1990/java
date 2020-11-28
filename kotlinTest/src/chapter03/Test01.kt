package chapter03

fun main(args: Array<String>) {
    val set = hashSetOf(1,7,40)
    println(set)
    val list = arrayListOf(1,2,3)
    println(list)
    //to 并不是一个特殊的结构，而是一个普通函数
    val map = hashMapOf(1 to "one",2 to "two")
    println(map)
    //Kotlin 的javaClass 等价于Java的  getClass ( )
    println(set.javaClass)
    println(list.javaClass)
    println(map.javaClass)
    println(list.max())
    println(list.last())
}