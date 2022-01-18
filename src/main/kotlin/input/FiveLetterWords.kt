package input

import java.io.File

object FiveLetterWords {
    fun load() = File("words.txt").readLines()
}