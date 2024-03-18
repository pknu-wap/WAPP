package com.wap.wapp.core.model.survey

enum class Rating {
    GOOD, MEDIOCRE, BAD
}

data class RatingDescription(
    val title: String,
    val content: String,
)

fun Rating.toDescription(): RatingDescription = when (this) {
    Rating.GOOD -> {
        RatingDescription("좋음", "행사 진행이 완벽하고, \n행사 내용이 많았음.")
    }
    Rating.MEDIOCRE -> {
        RatingDescription("보통", "행사 진행이 원활하고, \n행사 내용이 적당함.")
    }
    Rating.BAD -> {
        RatingDescription("나쁨", "행사 진행이 아쉬웠고, \n행사 내용이 부족함.")
    }
}
