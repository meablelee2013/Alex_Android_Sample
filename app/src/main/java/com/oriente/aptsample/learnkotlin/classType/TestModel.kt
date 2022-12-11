package com.oriente.aptsample.learnkotlin.classType

class TestModel(var name: String, var age: Int)


data class TestModel1(var name: String, var age: Int)



//密封类 必须要有类型,且要继承本类
sealed class Exam {
    //Fraction Fraction1 不需要参数，
    object Fraction : Exam()
    object Fraction1 : Exam()
    //Fraction2 因为有参数，
    class Fraction2(val studentName: String) : Exam()


}

class Teachers(private val exam:Exam){
    fun show():String =
        when (exam){
            is Exam.Fraction -> "学生成绩很差"
            is Exam.Fraction1 -> "学生成绩一般"
            is Exam.Fraction2 -> "学生成绩优秀"
        }
}

fun main() {
//    val testModel = TestModel("haha", 200)
//    val testModel2 = TestModel("haha", 200)
//    println(testModel)
//    println(testModel2)
//    println()
//    //普通类 == 是比较引用 类似于java 的equals
//    println(TestModel("haha", 200) == TestModel("haha", 200))
//    //数据类 == 是值的比较
//    println(TestModel1("haha", 200) == TestModel1("haha", 200))

    println(Teachers(Exam.Fraction).show())
    println(Teachers(Exam.Fraction1).show())
    println(Teachers(Exam.Fraction2("haha")).show())
}