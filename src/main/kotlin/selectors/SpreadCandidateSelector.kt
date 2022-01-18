package selectors

class SpreadCandidateSelector : CandidateSelector {
    override fun selectBest(candidates: List<String>): String? {
         val wordsPerLetter =
             ('a' .. 'z').associateWith { char ->
                 candidates.count { it.contains(char) }
             }

        fun String.getUniqueLetters() =
            this.groupBy { it }.keys

        return candidates.maxByOrNull { word -> word.getUniqueLetters().sumOf { char -> wordsPerLetter[char]!! } }
    }
}