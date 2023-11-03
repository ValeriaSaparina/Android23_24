package com.example.homework3.utils

import com.example.homework3.R
import com.example.homework3.models.DateData
import com.example.homework3.models.NewsData
import kotlin.random.Random

object NewsGenerator {

    private val news: MutableList<NewsData> = mutableListOf(
        NewsData(
//            Date(),
            0,
            "Breaking News 1",
            "This is the description of breaking news 1.",
            R.drawable.img
        ),
        NewsData(
//            Date(),
            1,
            "Breaking News 2",
            "This is the description of breaking news 2.",
            R.drawable.img_1
        ),
        NewsData(
//            Date(),
            2,
            "Breaking News 3",
            "This is the description of breaking news 3.",
            R.drawable.img_2
        )
    )

    val titleList = mutableListOf(
        "Breaking News 1",
        "Breaking News 2",
        "Breaking News 3",
    )

    val descriptionList = mutableListOf(
        "This is the description of breaking news 1.",
        "This is the description of breaking news 2.",
        "This is the description of breaking news 3.",

        )

    val imgList = mutableListOf(
        R.drawable.img,
        R.drawable.img_1,
        R.drawable.img_2,
    )

    private val result = mutableListOf<Any>()
    private val newResult = mutableListOf<Any>()
    private var id = 0

    fun getNews(count: Int): MutableList<Any> {
        if (result.isEmpty()) {

            addBtn()
            repeat(count) {
                result.add(news[it % news.size].copy(id = id))
                id++
            }

            val countDate = count / 8
            addDate(countDate)

        }

        return result
    }

    fun getNewsPos(position: Int): NewsData {
        return result[position] as NewsData
    }

    fun addNewNews(count: Int) {
        removeDate()
        repeat(count) {
            result.add(
                Random.nextInt(1, result.size),
                NewsData(
                    id = id,
                    title = titleList.random(),
                    description = descriptionList.random(),
                    img = R.drawable.img_3
                )
            )
            id++
        }
        addDate(result.size / 8)
    }

    fun removeDate() {
        val dateList = mutableListOf<DateData>()
        repeat(result.size) {
            if (result[it] is DateData) dateList.add(result[it] as DateData)
        }

        result.removeAll(dateList)
    }

    fun addDate(count: Int) {
        var pos = 0
        repeat(count) {
            pos += 9
            result.add(pos, DateData("02.11"))
            id++
        }
    }


    private fun addBtn() {
        result.add(0, R.id.bottom_sheet_btn)
    }

}