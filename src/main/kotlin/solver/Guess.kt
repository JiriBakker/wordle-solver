package solver

class Guess: CharSequence {
    private val guess: String

    override val length get() = guess.length
    override fun get(index: Int) = guess[index]
    override fun subSequence(startIndex: Int, endIndex: Int) = guess.subSequence(startIndex, endIndex)

    constructor(chars: String?) {
        if (chars?.length != 5)
            throw Exception("Invalid guess length: ${chars?.length}")

        guess = chars
    }
}