package com.rzm.testapplication.singleTon

class SingletonDemo private constructor() {
    val mutableAny by lazy() {
        mutableListOf<String>()
    }
    companion object {
        private var instance: SingletonDemo? = null
            get() {
                if (field == null) {
                    field = SingletonDemo()
                }
                return field
            }
        fun get(): SingletonDemo{
        //细心的小伙伴肯定发现了，这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字
         return instance!!
        }
    }
}

fun main() {
    var get = SingletonDemo.get()
    var get2 = SingletonDemo.get()
    println(get)
    println(get2)
}