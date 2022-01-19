package util

import kotlin.test.assertFalse

fun <T> assertDoesNotContain(iterable: Iterable<T>, element: T) =
    assertFalse(iterable.contains(element), "Expected the collection to NOT contain the element.\nCollection <$iterable>, element <$element>.")