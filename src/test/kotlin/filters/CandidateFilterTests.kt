package filters

import input.FiveLetterWords
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CandidateFilterTests {

    private val fiveLetterWords = FiveLetterWords.load()

    @Test
    fun filterCandidates() {
        val candidateFilter = RuleCandidateFilter()

        val guessHistory = listOf(
            "aeros" to "x?---"
        )

        val candidates = candidateFilter.filterCandidates(fiveLetterWords, guessHistory)

        assertEquals(1, candidates.size)
    }

}