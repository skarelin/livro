package com.sergeykarelin.livro.data.dto

import com.google.gson.annotations.SerializedName


data class TranslationDTO(
        @SerializedName("username")
        val username: String,
        @SerializedName("yandexTranslationList")
        val translations: List<Translation>
) {

    data class Translation(
            @SerializedName("englishOriginal")
            val original: String,
            @SerializedName("russianTranslation")
            val translation: String
    )

}

