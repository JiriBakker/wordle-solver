package solver

class Result: CharSequence {

    companion object {
        private val supportedChars = setOf('x', '?', '-')
    }

    private val result: String

    override val length get() = result.length
    override fun get(index: Int) = result[index]
    override fun subSequence(startIndex: Int, endIndex: Int) = result.subSequence(startIndex, endIndex)


    constructor(chars: String?) {
        if (chars?.length != 5)
            throw Exception("Invalid guess length: ${chars?.length}")
        if (chars.any { !supportedChars.contains(it) })
            throw Exception("Only 'x', '?' and '-' are supported characters")

        result = chars
    }
}