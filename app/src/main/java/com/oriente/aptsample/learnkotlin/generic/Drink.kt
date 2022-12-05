package com.oriente.aptsample.learnkotlin.generic

/**
 * ****************************************************************************************
 *
 * 1:泛型接口
 * 2:泛型类
 * 3:泛型字段
 * 4:泛型方法
 * 5:泛型约束
 * 6:泛型上限 out
 * 7:泛型下限 in
 *
 * ****************************************************************************************
 */






/**
 * 泛型接口
 */
interface Drink<T> {
    fun drink(t: T)
}

class DrinkApple : Drink<String> {
    override fun drink(t: String) {
        println("drink $t")
    }
}

fun main() {
    //泛型接口
//    val drinkApple = DrinkApple()
//    drinkApple.drink("apple juice")

    //泛型类
//    val blueColor = BlueColor("blue")
//    blueColor.printColor()

    //泛型方法
//   fromJson("{}", String::class.java)
    //泛型约束
//    fromJson2("{}", SubUser::class.java)
    //泛型约束
//    fromJson3("{}", SubUser::class.java)


}

/**
 * 泛型类 和泛型字段
 * 构造方法里如果添加var 或者val  表示是一个成员变量，在类中或者其对象都可以使用，
 * 但如果只想让其在类中可被使用，让其对象不可使用，可以添加private限定关键词
 */
abstract class Color<T>(
    private val t: T
    /**泛型字段*/
) {
    abstract fun printColor()
}

class BlueColor(private val color: String) : Color<String>(color) {
    override fun printColor() {
        println("printColor $color")
    }
}


/**
 * 泛型方法
 */

fun <T> fromJson(json: String, tClass: Class<T>): T? {
    return tClass.newInstance()
}


/**
 * 泛型约束
 * 所传递的类型T必须满足是User的子类或者User类
 */
fun <T : User> fromJson2(json: String, tClass: Class<T>): T? {
    return tClass.newInstance()
}

interface User {}

/**
 * 所传递的类型T必须同时满足where 子句的所有条件，在下面的示例中，类型T必须既要实现User，也要实现Comparable接口
 *
 */
fun <T> fromJson3(json: String, tClass: Class<T>): T? where T : User, T : Comparable<T> {
    return tClass.newInstance()
}

class SubUser : User, Comparable<SubUser> {
    override fun compareTo(other: SubUser): Int {
        return 1
    }
}


open class Animal

open class DogAnimal : Animal()

class CatAnimal : Animal()

class WhiteDogAnimal : DogAnimal()


fun animalTest() {
    val animal: Animal = DogAnimal() //不会报错 将子类赋值给父类

    //var animalList:ArrayList<Animal> = ArrayList<DogAnimal>() //编译报错，子类的集合不能赋值给父类的集合

    // 在使用处 使用out关键词声明---泛型上限 传入的泛型参数可以是Animal以及他的子类（DogAnimal,CatAnimal,WhiteDogAnimal）
    var animalList: ArrayList<out Animal> = ArrayList<DogAnimal>()

    var animalList2: ArrayList<Animal> = ArrayList<DogAnimal>()



    //var animalList3:InArrayList<DogAnimal> = InArrayList<Animal>()//报错
    //使用处使用in关键词---泛型下限 允许传入的泛型类型是DogAnimal或者其父类Animal
    var animalList4:InArrayList<in DogAnimal> = InArrayList<Animal>()

    var animalList5:In2ArrayList< DogAnimal> = In2ArrayList<Animal>()
}

//在定义处使用out关键字 允许传入的参数可以是T以及T的子类
class ArrayList<out T> {

}

class InArrayList<T>{

}

//在定义处使用in关键字--允许传入的泛型类型是T或者其父类
class In2ArrayList<in T>{

}

