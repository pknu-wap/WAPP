package com.wap.wapp.core.network.model.survey

import com.wap.wapp.core.model.survey.Rating

enum class RatingResponse {
    GOOD, MEDIOCRE, BAD
}

internal fun RatingResponse.toDomain(): Rating = when (this) {
    RatingResponse.GOOD -> Rating.GOOD
    RatingResponse.MEDIOCRE -> Rating.MEDIOCRE
    RatingResponse.BAD -> Rating.BAD
}
