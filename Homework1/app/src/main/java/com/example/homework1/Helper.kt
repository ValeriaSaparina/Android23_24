package com.example.homework1

import android.util.Log

class Helper {

    companion object {
        private var input = ""
        private var position = 0
        private const val countTv = 3

        fun add(arg: String) {
            input = arg
            if (position < countTv) {
                position += 1
            } else {
                position = 1
            }
        }

        fun getPosition(): Int {
            return position
        }

        fun getValue(): String {
            return input
        }
    }

}