package selectors

interface CandidateSelector {
    fun selectBest(candidates: List<String>): String?
}