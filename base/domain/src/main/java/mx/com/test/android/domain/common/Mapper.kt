package mx.com.test.android.domain.common

abstract class Mapper<in E, out T> {

    abstract fun mapFrom(from: E): T

    fun mapFromOrNull(from: E?): T? {
        return if (from != null)
            mapFrom(from)
        else null
    }

    fun mapFromList(from: List<E>): List<T> {
        return from.map { mapFrom(it) }
    }

    fun mapFromListOrNull(from: List<E>?): List<T>? {
        return from?.map { mapFrom(it) }
    }
}