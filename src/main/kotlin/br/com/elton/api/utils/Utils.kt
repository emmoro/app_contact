package br.com.elton.api.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Singleton
class Utils {

    fun convertDateToString(date : LocalDateTime) : String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        return date.format(formatter)
    }

}