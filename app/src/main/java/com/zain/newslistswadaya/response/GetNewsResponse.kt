package com.zain.newslistswadaya.response

data class GetNewsResponse(
	val totalResults: Int? = null,
	val articles: List<ArticlesItem?>? = null,
	val status: String? = null
)

data class Source(
	val name: String? = null,
	val id: Any? = null
)

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
) {
	companion object {
		const val TYPE_BIG = 0
		const val TYPE_SMALL = 1
	}
}

