package filters

interface CandidateFilter {
    fun filterCandidates(
        candidates: List<String>,
        guessHistory: List<Pair<String, String>>
    ): List<String>
}
