package com.example.homework2

object Helper {
    fun isAllAns(): Boolean {
        answerCheckedMutableList.forEach {
            if (it == -1) return false
        }
        return true
    }

    val answerCheckedMutableList: MutableList<Int> = mutableListOf()
}