package com.example.newstart

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat

class NewTest {
    fun miniMaxSum(arr: Array<Int>): Unit {
        // Write your code here
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        var sum = 0
        arr.forEach {
            min = if(min > it) it else min
            max = if(max < it) it else max
            sum += it
        }
        var minSum = sum - max
        var maxSum = sum - min
        val builder = StringBuilder()
        builder.append(minSum).append(" ").append(maxSum)
        System.out.printf(builder.toString())
    }

    fun breakingRecords(scores: Array<Int>): Array<Int> {
        // Write your code here
        var result = arrayOf(2)
        var min = 0
        var max = 0
        scores.forEach {
            if(min == 0 && max == 0){
                min = it
                max = it
            }else if(it > max) {
                max = it
                result[0] +=1
            }else if(it < min)
                min = it
            result[1]+= 1
        }
        return result
    }
}