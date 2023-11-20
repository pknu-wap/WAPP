package com.wap.wapp.feature.notice

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDate

class CalendarTest {

    @Test
    fun `달력은 2023-11-20 형식의 날짜와, 요일을 반환한다`() {
        // given
        val calendar = Calendar()
        val localDate = LocalDate.of(2023, 11, 20)

        // when
        val actual = calendar.generateNowDateAndDay(localDate)

        // then
        val expected = "2023.11.20" to "월요일"
        assertThat(actual).isEqualTo(expected)
    }
}
