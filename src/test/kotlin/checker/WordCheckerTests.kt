package checker

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class WordCheckerTests {
    @Test
    fun canFindCorrectLetters() {
        assertEquals("x----", "abcde".wordleMatch("awxyz"))
        assertEquals("-x---", "abcde".wordleMatch("wbxyz"))
        assertEquals("--x--", "abcde".wordleMatch("wxcyz"))
        assertEquals("---x-", "abcde".wordleMatch("wxydz"))
        assertEquals("----x", "abcde".wordleMatch("wxyze"))
        assertEquals("xx---", "abcde".wordleMatch("abxyz"))
    }

    @Test
    fun canFindMisplacedLetters() {
        assertEquals("----?", "abcde".wordleMatch("wxyza"))
        assertEquals("?----", "abcde".wordleMatch("bwxyz"))
        assertEquals("-?---", "abcde".wordleMatch("wcxyz"))
        assertEquals("----?", "abcde".wordleMatch("wxyzd"))
        assertEquals("?----", "abcde".wordleMatch("ewxyz"))
        assertEquals("---??", "abcde".wordleMatch("xyzab"))
    }

    @Test
    fun canDealWithCorrectAndMisplacedLetters() {
        assertEquals("-x--?", "abcde".wordleMatch("xbyza"))
        assertEquals("xxx??", "abcde".wordleMatch("abced"))
    }

    @Test
    fun canDealWithDuplicateLetters() {
        assertEquals("x-??-", "abcde".wordleMatch("aabcz"))
        assertEquals("x??-?", "aabcd".wordleMatch("abcza"))
    }
}