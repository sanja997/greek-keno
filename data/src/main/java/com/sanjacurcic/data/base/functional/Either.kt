sealed class Either<out E, out R> {
    data class Error<out E>(val data: E) : Either<E, Nothing>()
    data class Result<out R>(val data: R) : Either<Nothing, R>()

    val isResult get() = this is Result<R>
    val isError get() = this is Error<E>

    fun result() = (this as Result<R>).data
    fun error() = (this as Error<E>).data
}