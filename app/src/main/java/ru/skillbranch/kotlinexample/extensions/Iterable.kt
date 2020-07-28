package ru.skillbranch.kotlinexample.extensions

fun <T>List<T>.dropLastUntil(predicate: (T) ->Boolean):List<T>{
    if (!isEmpty()) {
        val iterator = listIterator(size)
        val list = ArrayList<T>()
        while (iterator.hasPrevious()) {
            if (predicate(iterator.previous())) {
                return take(iterator.nextIndex())

            }
        }
    }
    return emptyList()

}