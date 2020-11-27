package chapter02
//包名称后加上．＊来导入特定包中定义的所有声明
import java.util.*
//2.2.1　属性24
//2.2.2　自定义访问器 25
//2.2.3　Kotlin 源码布局 ：目录和包26
class Rectangle(val height: Int, val width: Int) {
    //属性is Square 不需要宇段来保存它的值。它只有一个自定义实现的getter 。
    //它的值是每次访问属性的时候计算出来的。
    val isSquare: Boolean
//        get() {return height==width}
        get() = height == width

}

fun createRandomRectangle():Rectangle{
    val random = Random()
    return Rectangle(random.nextInt(),random.nextInt())
}
fun main(args: Array<String>) {
    val r = Rectangle(11,12)
    println(r.isSquare)
    println(createRandomRectangle().isSquare)
}