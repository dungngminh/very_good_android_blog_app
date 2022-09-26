package me.dungngminh.verygoodblogapp

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy

fun sum(a: Int, b: Int): Int {
    return a + b
}

enum class Type {
    HEHE,
    HUHU
}

open class Person

class Student(var name: String, var age: Int, var type: Type = Type.HEHE) : Person() {
    var combineNameAndAge: String = name + age

    fun jump() {
        if (age > 21)
            return;
        println("$name is under 21")
    }
}

class Foo(val name: String) {

    constructor(name: String, number: Int = 2) : this(name) {
        println("Number is $number")
    }

    init {
        println("init")
    }
}


class Foo2 {
    constructor(name: String, number: Int = 2) {
        println("Number is $number")
    }

    init {
        println("init")
    }
}


//fun main(){
//   var person: Student = Student("Dung", 20)
//    println(person.combineNameAndAge)
//    println(person.combineNameAndAge.get(1))
//    person.jump()
//    val number = if(person.age > 28) person.age else 80
//    println(number)
//    var person2: Student = Student("Dung", 22)
//    person2.jump()
//    println("=====================================================================================")
//    var listStudents = listOf<Student>(Student("a", 18), Student("b", 20, type = Type.HUHU), Student("c", 30), Student("d", 40))
////    for (student in listStudents){
////        student.jump()
////        when(student.name){
////            "a", "b" -> println("name is a")
////            "b" -> println("name is b")
////            "c" -> println("name is c")
////            else -> println("hehe")
////        }
////        when(student.type){
////            Type.HEHE -> println("hehe")
////            Type.HUHU -> println("huhu")
////        }
////    }
////    var i =1
////    for(i in 2..3){
////        println(i)
////        println("Hehe")
////    }
////    for((index, value) in listStudents.withIndex()){
////        println("This $index and value is ${value.name}")
////    }
//
////    for (i in 1..100) {
////        for (j in 1..100) {
////            if (j == 3) break
////            println(j)
////        }
////    }
//
//    val personA = Foo(name = "name", number = 2)
//    var foo2 = Foo2(name = "", number = 0);
//}

inline fun foo(crossinline abc: () -> Unit){
    abc()
}


fun main() {
    val obs1 = Observable.just("a", "b", "c", "d", "e");
    obs1.map { it + "b" }.filter { it.startsWith("a") }.subscribeBy(onNext = { result ->
        println("hehe" + result)
    }, onComplete = {
        println("completed")
    })

    obs1.doOnNext { println("hehe") }.subscribe {
        print(it)
    }

    foo {
        println("hehe")
        return@foo
    }
}