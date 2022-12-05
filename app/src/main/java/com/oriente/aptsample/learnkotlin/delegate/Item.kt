package com.oriente.aptsample.learnkotlin.delegate

import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

/**
 *
 * 不使用类对属性进行委托，而是直接使用方法
 * 2个参数
 * 第一个参数item是代理的属性的类或者父类
 * 第二个参数是固定的 property: KProperty<*>
 *
 * 1:为什么是String.setValue 而不是其他的xxx.setValue，因为委托的对象的类型是String
 * 2:setValue()是三个参数
 *      第一个参数是委托的属性Item.version222的类或者其父类
 *      第二个参数是一个固定的property: KProperty<*>
 *      第三个参数是value:String 因为委托的属性的类型是String
 *
 *
 */
private operator fun String.setValue(item: Item, property: KProperty<*>, value: String) {
    println("----------------委托属性被设置----------------")
    println("property 是谁 $property  item是 $item")
    //需要反射机制 反射：修改version222的值为value  只能修改version222的值，不能修改version111的值
    //反射需要导入此包 implementation 'org.jetbrains.kotlin:kotlin-reflect:1.3.41'
    property.javaField?.isAccessible = true
    property.javaField?.set(item, value)
}

operator fun String.getValue(item: Item, property: KProperty<*>): String = this + "自定义getValue"

class Item {

    var version111: String = "Version1 data"
    var version222: String by version111
}

fun main() {
    val item = Item()
    println(item.version222)

    item.version222 = "AAAAAAAAAAA"
    println("反射之后设置的值 ${item.version222}")
}