package filters

import java.lang.Integer.max

class RemainingCandidateFilter : CandidateFilter {

    override fun filterCandidates(
        candidates: List<String>,
        guessHistory: List<Pair<String, String>>
    ): List<String> {
        val correctLetters = mutableMapOf<Int, Char>()
        val minLetterCounts = mutableMapOf<Char, Int>()
        val maxLetterCounts = mutableMapOf<Char, Int>()
        val invalidPositions = mutableMapOf<Char, Set<Int>>()

        guessHistory.forEach { (guess, result) ->
            result.zip(guess).withIndex()
                .groupBy { it.value.second }
                .forEach { (char, results) ->
                    val misses = results.count { it.value.first == '-' }
                    val hits = results.count { it.value.first == '?' || it.value.first == 'x' }
                    if (misses > 0) {
                        minLetterCounts[char] = hits
                        maxLetterCounts[char] = hits
                    } else {
                        minLetterCounts[char] = max(hits, minLetterCounts[char] ?: 0)
                    }
                }


            result.zip(guess).withIndex()
                .filter { it.value.first == 'x' }
                .forEach {
                    correctLetters[it.index] = it.value.second
                }

            result.zip(guess).withIndex()
                .filter { it.value.first == '?' }
                .forEach {
                    invalidPositions[it.value.second] = (invalidPositions[it.value.second] ?: emptySet()).plus(it.index)
                }
        }

        return candidates.filter { word ->
            val matchesCorrectLetters = correctLetters.all { (index, char) -> word[index] == char }
            val matchesLetterCounts =
                minLetterCounts.all { (char, count) -> word.count { it == char } >= count }
                    && maxLetterCounts.all { (char, count) -> word.count { it == char } <= count }
            val matchesInvalidPositions = invalidPositions.all { (char, positions) -> positions.none { word[it] == char } }
            matchesCorrectLetters && matchesLetterCounts && matchesInvalidPositions
        }

    }

}