package filters

import solver.Guess
import solver.Result

class HybridCandidateFilter : CandidateFilter {

    private val internalFilter = RuleCandidateFilter()

    override fun filterCandidates(
        candidates: List<String>,
        guessHistory: List<Pair<Guess, Result>>
    ): List<String> {
        if (guessHistory.size < 2) {
            // Ignore results and choose candidates to cover the most unique letters
            val letterSpreadCandidates =
                candidates.filter { candidate ->
                    candidate.none { char ->
                        guessHistory.any { (guess, _) -> guess.contains(char) }
                    }
                }

            if (letterSpreadCandidates.isNotEmpty()) {
                return letterSpreadCandidates
            }
        }

        return internalFilter.filterCandidates(candidates, guessHistory)
    }
}