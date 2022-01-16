package filters

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CandidateFilterTests {

    private val testWords = listOf(
        "abcde",
        "fghij",
        "klmno"
    )

    @Test
    fun filterCandidates() {
        val candidateFilter = RuleCandidateFilter()

        val guessHistory = listOf(
            "aeros" to "x?---"
        )

        val candidates = candidateFilter.filterCandidates(testWords, guessHistory)

        assertEquals(1, candidates.size)
    }

}