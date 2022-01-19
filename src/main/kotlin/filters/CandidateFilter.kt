package filters

import solver.Guess
import solver.Result

interface CandidateFilter {
    fun filterCandidates(
        candidates: List<String>,
        guessHistory: List<Pair<Guess, Result>>
    ): List<String>
}
