package com.oriente.aptsample.learnkotlin.delegate

import kotlin.reflect.KProperty

interface DB {
    fun save()
}

class MyOracleDb : DB {
    override fun save() {
        println("save to Oracle")
    }
}

class MysqlDb : DB {
    override fun save() {
        println("save to Mysql")
    }
}

//1:类委托
class CreateDBAction(db: DB) : DB by db

//以下是CreateDBAction类的反编译类 ---其实就是静态代理
/**
 * public final class CreateDBAction implements DB {
// $FF: synthetic field
private final DB $$delegate_0;

public CreateDBAction(@NotNull DB db) {
Intrinsics.checkNotNullParameter(db, "db");
super();
this.$$delegate_0 = db;
}

public void save() {
this.$$delegate_0.save();
}
}
 */

//2:属性委托
class Example {
    //    var aaa:String by MyDelegate()
    var ccc: String = "aaaaa"

}


class MyDelegate {
    //为什么只有val 可读的变量才可以用lazy，是因为lazy返回的是Lazy对象，而Lazy里只有getValue的方法，没有setValue的方法，且方法一定要使用operator修饰

    val emails: List<String> by lazy {
        listOf("aaaaa@.com")
    }
    val phone:String by lazy {
        "aaaaaa"
    }

//    operator fun getValue(example: Any, property: KProperty<*>): String {
//
//    }
//
//    operator fun setValue(example: Any, property: KProperty<*>, value: String) {
//
//    }

}

fun main() {
    CreateDBAction(MyOracleDb()).save()
    CreateDBAction(MysqlDb()).save()


}