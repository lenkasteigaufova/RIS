package common


sealed class State<out T, out E> {
    object Initial                                              : State<Nothing, Nothing>()
    object Loading                                              : State<Nothing, Nothing>()
    data class  Success<out V>(val value: V)                    : State<V, Nothing>()
    data class  Failure<out E>(val error: E)                    : State<Nothing, E>()
}

inline fun <E, V, V2> State<V, E>.map(f: (V) -> V2): State<V2, E> = when(this) {
    is State.Success    -> State.Success(f(this.value))
    is State.Loading    -> this
    is State.Failure    -> this
    is State.Initial    -> this
}