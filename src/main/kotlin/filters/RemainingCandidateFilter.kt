package filters

import java.lang.Integer.max

class RemainingCandidateFilter : CandidateFilter {

    override fun filterCandidates(
        candidates: List<String>,
        guessHistory: List<Pair<String, String>>
    ): List<String> {
        val correctLetters = mutableMapOf<Int, Char>()
        val letterCounts = mutableMapOf<Char, Int>()
        val invalidPositions = mutableMapOf<Char, Set<Int>>()

        guessHistory.forEach { (guess, result) ->
            val resultLetterCounts =
                result.zip(guess).withIndex()
                    .groupBy { it.value.second }
                    .map { it.key to it.value.count { result -> result.value.first in setOf('?', 'x') } }
                    .toMap()

            resultLetterCounts.forEach { (char, count) ->
                letterCounts[char] = max(count, letterCounts[char] ?: 0)
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
            val matchesLetterCounts = letterCounts.all { (char, count) -> word.count { it == char } == count }
            val matchesInvalidPositions = invalidPositions.all { (char, positions) -> positions.none { word[it] == char } }
            matchesCorrectLetters && matchesLetterCounts && matchesInvalidPositions
        }

    }

}