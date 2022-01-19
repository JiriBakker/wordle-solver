package input

import java.io.File

object FiveLetterWords {
    fun loadEnglish() = File("words/english_5letter.txt").readLines()
}