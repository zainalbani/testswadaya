package com.zain.newslistswadaya.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetNewsResponse(
	val totalResults: Int? = null,
	val articles: List<ArticlesItem?>? = null,
	val status: String? = null
):Parcelable

@Parcelize
data class Source(
	val name: String? = null,
	val id: String? = null
):Parcelable

@Parcelize
data class ArticlesItem(
	val publishedAt: String? = null,
	val author: String? = null,
	val urlToImage: String? = null,
	val description: String? = null,
	val source: Source? = null,
	val title: String? = null,
	val url: String? = null,
	val content: String? = null,
	var type: Int = TYPE_SMALL
):Parcelable {
	companion object {
		const val TYPE_BIG = 0
		const val TYPE_SMALL = 1
	}
}

