package filters

private typealias Rule = (String) -> Boolean

class RuleCandidateFilter : CandidateFilter {

    override fun filterCandidates(
        candidates: List<String>,
        guessHistory: List<Pair<String, String>>
    ): List<String> {
        val rules = mutableListOf<Rule>()

        guessHistory.forEach { (guess, result) ->
            result.forEachIndexed { index, char ->
                val guessedLetter = guess[index]
                when (char) {
                    '-' -> rules.add { word -> word.none { it == guessedLetter } }
                    '?' -> rules.add { word -> word.any { it == guessedLetter } && word[index] != guessedLetter }
                    'x' -> rules.add { word -> word[index] == guessedLetter }
                }
            }
        }

        return candidates.filter { word -> rules.all { rule -> rule(word) } }
    }
}