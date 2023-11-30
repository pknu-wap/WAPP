package com.wap.wapp.core.model.survey

enum class Rating {
    GOOD, MEDIOCRE, BAD
}

fun Rating.toNaturalLanguage(): String = when (this) {
    Rating.GOOD -> { "좋음" }
    Rating.MEDIOCRE -> { "보통" }
    Rating.BAD -> { "나쁨" }
}
