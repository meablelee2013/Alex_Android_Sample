package com.oriente.aptsample.learnkotlin.learndatastruct

class LinkedList<T> {

    var head: Node<T>
    var size: Int = 0


    inner class Node<T>(var next: Node<T>?, var data: T?) {

    }

    constructor() {
        head = Node(null, null)
        size = 0
    }

    fun clear() {
        head.next = null
        this.size = 0
    }

    fun isEmpty(): Boolean {
        return size == 0
    }

    fun length(): Int {
        return size
    }

    fun get(i: Int): T? {
        //通过for循环找到i
        if (i >= size) {
            throw java.lang.Exception("index out of bound")
        } else {
            //记录第一个节点
            var n = head.next
            for (j in 0..i) {
                n = n?.next
            }
            return n?.data
        }
    }

    fun getNode(i: Int): Node<T>? {
        //通过for循环找到i
        if (i >= size) {
            throw java.lang.Exception("index out of bound")
        } else {
            //记录第一个节点
            var n = head.next
            for (j in 0..i) {
                n = n?.next
            }
            return n
        }
    }

    fun insert(data: T) {
        //找到链表的最后一个节点
        val n = getNode(size - 1)
        //创建新节点
        val newNode = Node(null, data)

        //将最后一个节点指向新节点
        n?.next = newNode

        //size+1
        size += 1
    }

    /**
     * 在i位置插入节点他四步
     * 1：找到i-1位置的节点
     * 2：找到i位置的节点
     * 3：创建新节点，并将新节点指向i位置的节点
     * 4：将i-1位置的节点指向新节点
     * 5：size+1
     */
    fun insert(i: Int, data: T) {
        //先找到i-1节点
        //1:通过for循环找到i-1的节点
        if (i >= size) {
            throw java.lang.Exception("index out of bound")
        }
        //i-1节点
        val n = getNode(i - 1)
        //2:找到i位置的节点
        var iNode = n?.next

        //3：创建新节点，并将新节点指向i位置的节点
        val newNode = Node(iNode, data)
        //4：将i-1节点指向新节点
        n?.next = newNode
        //size+1
        size += 1
    }

    /**
     * 1：找到i-1节点
     * 2:打到i+1节点
     * 3:将i-1节指向i+1节点
     * 4:size-1
     */
    fun remove(i: Int) {
        //需要判断i是不是最后一个节点

        var n: Node<T>? = head
        if (i > size) {
            throw java.lang.Exception("index out of bound")
        } else if (i == size - 1) {//最后一个节点
            val node = getNode(i - 1)
            node?.next = null
            size--
        } else {
            val pre = getNode(i - 1)
            val next = getNode(i + 1)
            //当前n节点就是i-1位置的节点
            pre?.next = next
            size--
        }
    }

}