package com.codepalace.chatbot.utils

import com.codepalace.chatbot.utils.Constants.OPEN_GOOGLE
import com.codepalace.chatbot.utils.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {

            //Hello
            message.contains("hallo") -> {
                when (random) {
                    0 -> "Hallo juga"
                    1 -> "Hallo, ada yang ditanyakan?"
                    2 -> "Hallo, bagaimana kabarmu hari ini?"
                    else -> "error" }
            }
            //Kabar
            message.contains("kabar saya baik") -> {
                when (random) {
                    0 -> "Syukurlah, apakah kamu ingin konsultasi?"
                    1 -> "Syukurlah, ada yang bisa saya bantu?"
                    else -> "Syukurlah, ada yang bisa saya bantu?"}
            }

            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            message.contains("saya ingin konsultasi") -> {
                when (random) {
                    0 -> "Baik, apakah kamu ingin konsultasi sekarang?"
                    1 -> "Baik, apakah kamu ingin konsultasi sekarang?"
                    else -> "Baik, apakah kamu ingin konsultasi sekarang?"}
            }

            message.contains("sedih") -> {
                when (random) {
                    else -> "Nina ikut sedih mendengarnya... apa kamu mau cerita?"}
            }

            message.contains("saya sedih") -> {
                when (random) {
                    else -> "Nina ikut sedih mendengarnya... apa kamu mau cerita?"}
            }

            message.contains("tidak") -> {
                when (random) {
                    else -> "Baik, tolong jaga diri ya..."}
            }

            //Open WhatsApp
            message.contains("ya")-> {
                OPEN_GOOGLE
            }
            //Open WhatsApp
            message.contains("ada")-> {
                OPEN_GOOGLE
            }
            //Open WhatsApp
            message.contains("konsultasi sekarang")-> {
                OPEN_GOOGLE
            }

            //Open WhatsApp
            message.contains("mulai konsultasi")-> {
                OPEN_GOOGLE
            }

            //Open WhatsApp
            message.contains("konsultasi")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "Saya kurang paham..."
                    1 -> "Coba tanyakan hal lain"
                    2 -> "Mungkin ada pertanyaan lain?"
                    else -> "error"
                }
            }
        }
    }
}