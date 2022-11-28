package com.trongnk.movieapp.util

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<out T>(private val _content: T) {
    private val objectsThatHandledTheEvent = HashSet<String>(0)

    fun getContentIfNotHandled(objectThatHandledTheEvent: Any): T? {
        val handlerName = objectThatHandledTheEvent.javaClass.name
        return if (objectsThatHandledTheEvent.add(handlerName)) {
            _content
        } else {
            null
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peek(): T = _content

    /**
     * An [Observer] for [event]s, simplifying the pattern of checking if the [event]'s content has
     * already been handled.
     *
     * [observer] is *only* called if the [event]'s contents has not been handled.
     */
    class Observer<T>(
        private val objectThatHandledTheEvent: Any,
        private val observer: (T) -> Unit
    ) :
        androidx.lifecycle.Observer<Event<T>> {
        override fun onChanged(event: Event<T>?) {
            event?.getContentIfNotHandled(objectThatHandledTheEvent)?.let(observer)
        }
    }
}

fun event() = Event(Unit)
