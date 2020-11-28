package chapter03;

//扩展函数非常简单，它就是一个类的成员函数，不过定义在类的外面。
fun String.lastChar(): Char = this.get(this.length - 1)

//可以像其他成员函数一样用this 。而且也可以像普通的成员函数一样
//将为类的成员函数和扩展函数使用术语方法
//扩展函数不能访问私有的或者是受保护的成员
fun String.lastChar2(): Char = get(length - 1)

fun main(args: Array<String>) {
    println("123".lastChar())
}