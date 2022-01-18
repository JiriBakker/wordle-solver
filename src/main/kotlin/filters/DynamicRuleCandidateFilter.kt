package filters


class DynamicCandidateFilter(private val remainingThreshold: Int) : CandidateFilter {

    private val internalFilter = RemainingCandidateFilter()

    override fun filterCandidates(
        candidates: List<String>,
        guessHistory: List<Pair<String, String>>
    ): List<String> {
        val remainingCandidates = internalFilter.filterCandidates(candidates, guessHistory)

        if (remainingCandidates.size < remainingThreshold) {
            return remainingCandidates
        }

        val letterSpreadCandidates =
            candidates.filter { candidate ->
                candidate.none { char ->
                    guessHistory.any { (guess, _) -> guess.contains(char) }
                }
            }

        return letterSpreadCandidates.ifEmpty { remainingCandidates }
    }
}