package com.cr_d.passwordmanagerapp.ui.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formatAs(dateFormat: DateFormatOption): String {
    return when(dateFormat) {
        DateFormatOption.YMD -> this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        DateFormatOption.DMY -> this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }
}
