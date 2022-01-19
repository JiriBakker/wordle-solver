package filters

import util.assertDoesNotContain
import input.FiveLetterWords
import org.junit.jupiter.api.Test
import solver.Guess
import solver.Result
import kotlin.test.assertContains
import kotlin.test.assertEquals

class CandidateFilterTests {

    private class TestCase(
        val guessHistory: List<Pair<Guess, Result>>,
        val validateCandidates: (List<String>) -> Unit
    )

    private val fiveLetterWords = FiveLetterWords.loadEnglish()

    // Because the Dynamic approach has a different strategy we split the test cases into two categories
    private val baseTestCases = listOf(
        TestCase(
            guessHistory = listOf(Guess("aeros") to Result("x?---")),
            validateCandidates = { candidates ->
                assertContains(candidates, "apple")
                assertDoesNotContain(candidates, "after")
                assertDoesNotContain(candidates, "aeros")
            }
        ),
        TestCase(
            guessHistory = listOf(Guess("aeros") to Result("xxxxx")),
            validateCandidates = { candidates ->
                assertEquals(1, candidates.size)
                assertContains(candidates, "aeros")
            }
        ),
        TestCase(
            guessHistory = listOf(Guess("apple") to Result("-----")),
            validateCandidates = { candidates ->
                assertDoesNotContain(candidates, "apple")
            }
        ),
        TestCase(
            guessHistory = listOf(Guess("tames") to Result("?????")),
            validateCandidates = { candidates ->
                assertEquals(1, candidates.size)
                assertContains(candidates, "steam")
            }
        )
        // TODO more test cases?
    )

    private val solvedTestCases = listOf(
        TestCase(
            guessHistory =
                listOf(
                    Guess("steam") to Result("-?---"),
                    Guess("porky") to Result("xx---"),
                    Guess("potin") to Result("xx???")
                ),
            validateCandidates = { candidates ->
                assertEquals(1, candidates.size)
                assertContains(candidates, "point")
            }
        ),
        TestCase(
            guessHistory =
                listOf(
                    Guess("spear") to Result("-?---"),
                    Guess("mogul") to Result("-x---"),
                    Guess("potty") to Result("xx?--")
                ),
            validateCandidates = { candidates ->
                assertEquals(1, candidates.size)
                assertContains(candidates, "point")
            }
        ),
        TestCase(
            guessHistory =
                listOf(
                    Guess("spear") to Result("x--xx"),
                    Guess("sonar") to Result("xx-xx")
                ),
            validateCandidates = { candidates ->
                assertEquals(3, candidates.size)
                assertContains(candidates, "solar")
            }
        ),
        TestCase(
            guessHistory =
                listOf(
                    Guess("spear") to Result("-?-?-"),
                    Guess("fight") to Result("-?---"),
                    Guess("piano") to Result("x???-")
                ),
            validateCandidates = { candidates ->
                assertEquals(3, candidates.size)
                assertContains(candidates, "panic")
            }
        ),
        TestCase(
            guessHistory =
                listOf(
                    Guess("spear") to Result("--??-"),
                    Guess("fudge") to Result("----?"),
                    Guess("noble") to Result("--x-?"),
                    Guess("webby") to Result("-?x?x")
                ),
            validateCandidates = { candidates ->
                assertEquals(1, candidates.size)
                assertContains(candidates, "abbey")
            }
        )
        // TODO more test cases?
    )

    private val allTestCases = baseTestCases.plus(solvedTestCases)

    @Test
    fun testRuleCandidateFilter() {
        val candidateFilter = RuleCandidateFilter()

        allTestCases.forEach { testCase ->
            val candidates = candidateFilter.filterCandidates(fiveLetterWords, testCase.guessHistory)
            testCase.validateCandidates(candidates)
        }
    }

    @Test
    fun testRemainingCandidateFilter() {
        val candidateFilter = RemainingCandidateFilter()

        allTestCases.forEach { testCase ->
            val candidates = candidateFilter.filterCandidates(fiveLetterWords, testCase.guessHistory)
            testCase.validateCandidates(candidates)
        }
    }

    @Test
    fun testDynamicCandidateFilter() {
        val candidateFilter = HybridCandidateFilter()

        solvedTestCases.forEach { testCase ->
            val candidates = candidateFilter.filterCandidates(fiveLetterWords, testCase.guessHistory)
            testCase.validateCandidates(candidates)
        }
    }

}