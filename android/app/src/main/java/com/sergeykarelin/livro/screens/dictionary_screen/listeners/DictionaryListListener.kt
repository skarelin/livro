package com.sergeykarelin.livro.screens.dictionary_screen.listeners

import com.sergeykarelin.livro.data.dto.TranslationDTO

interface DictionaryListListener {

    fun findWord(translation: TranslationDTO.Translation)

    fun deleteWord(text: String)

}