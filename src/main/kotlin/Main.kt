import solver.Guess
import solver.Result
import solver.WordleSolver

fun main() {
    println("Wordle Solver\n")

    WordleSolver.suggestGuesses(
        sequence {
            while (true) {
                try {
                    print("Provide guess: ")
                    val guess = Guess(readLine())

                    print("Provide result: ")
                    val result = Result(readLine())

                    if (result.all { it == 'x' }) {
                        println("You're welcome!")
                        return@sequence
                    }

                   yield(guess to result)
                } catch (e: Exception) {
                    println("ERROR ${e.message}. Try again")
                }
            }
        }
    ).forEach { guesses ->
        println("Try: ${guesses.joinToString(" - ")}")
    }
}