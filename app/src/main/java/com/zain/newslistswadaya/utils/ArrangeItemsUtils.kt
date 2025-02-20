package com.zain.newslistswadaya.utils

import com.zain.newslistswadaya.response.ArticlesItem

object ArrangeItemsUtils {
    fun arrangeItems(data: List<ArticlesItem?>?): List<ArticlesItem> {
        val items = mutableListOf<ArticlesItem>()
        var index = 0

        while (index < data!!.size) {
            if (index < data.size) {
                data[index]!!.type = ArticlesItem.TYPE_BIG
                data[index]?.let { items.add(it) }
                index++
            }

            for (i in 0 until 4) {
                if (index < data.size) {
                    data[index]!!.type = ArticlesItem.TYPE_SMALL
                    data[index]?.let { items.add(it) }
                    index++
                }
            }
        }

        return items
    }
}