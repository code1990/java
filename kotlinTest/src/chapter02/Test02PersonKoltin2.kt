package chapter02

//public 是默认的可见性，所以你能省略它。
class Test02PersonKoltin(val name: String)


//public 是默认的可见性，所以你能省略它。
class Test02PersonKoltin2(
        val name: String,
        var isMarried: Boolean
//val只读属性：生成一个字段和一个简单的getter
//var可写属性：一个字段、一个getter 和一个setter
//如果属性的名称以is 打头， getter 不会增加任何的  前缀：而它的setter 名称中的is 会被替换成set
)

fun main(args: Array<String>) {
    val t = Test02PersonKoltin2("Bob", true)
    println(t.name)
    println(t.isMarried)
    t.isMarried = false
    println(t.isMarried)

}