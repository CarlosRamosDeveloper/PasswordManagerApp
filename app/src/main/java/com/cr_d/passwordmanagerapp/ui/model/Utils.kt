package com.cr_d.passwordmanagerapp.ui.model

import java.time.format.DateTimeFormatter
import java.time.LocalDate

fun LocalDate.formatAs(dateFormat: DateFormatOption): String {
    return when(dateFormat) {
        DateFormatOption.YMD -> this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        DateFormatOption.DMY -> this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }
}
