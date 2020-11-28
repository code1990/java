package chapter03

class User(val id: Int, val name: String, val address: String)

//常重复代码的函数
fun saveUser(user: User) {
    if (user.name.isEmpty()) {
        throw IllegalArgumentException("empty name")
    }
    if (user.address.isEmpty()) {
        throw IllegalArgumentException("empty name")
    }
}

fun saveUser2(user: User) {
    //声明一个局部函数来验证所有字段
    fun validate(user: User,
                 value: String,
                 fieldName: String
    ) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("empty ${fieldName}")
        }
    }
    //局部函数验证特定字段
    validate(user, user.name, "Name")
    validate(user, user.name, "Address")
}

fun saveUser3(user: User) {
    //声明一个局部函数来验证所有字段
    fun validate(
            value: String,
            fieldName: String
    ) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("empty ${fieldName}")
        }
    }
    //局部函数验证特定字段
    validate(user.name, "Name")
    validate(user.address, "Address")
}
//拓展函数
fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("empty ${fieldName}")
        }
    }
    validate(name, "name")
    validate(address, "address")
}

fun saveUser4(user:User){
    user.validateBeforeSave()
}