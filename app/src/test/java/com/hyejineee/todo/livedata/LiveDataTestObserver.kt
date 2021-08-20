package com.hyejineee.todo.livedata

import androidx.lifecycle.Observer
import java.lang.AssertionError

class LiveDataTestObserver<T> : Observer<T> {

    private val values : MutableList<T> = mutableListOf()

    override fun onChanged(t: T) {
        values.add(t)
    }

    fun assertValueSequence(sequence:List<T>) : LiveDataTestObserver<T> {
        var i = 0
        val actualIterator = values.iterator()
        val expectedIterator = sequence.iterator()

        var actualNext:Boolean
        var expectNext:Boolean

        println(
            "비교 - values : ${values}, sequence : ${sequence}"
        )


        while(true){
            actualNext = actualIterator.hasNext()
            expectNext = expectedIterator.hasNext()

            if(!actualNext || !expectNext) break

            val actual :T = actualIterator.next()
            val expected : T = expectedIterator.next()

            if(actual != expected){
                throw AssertionError("actual : ${actual}, expected : ${expected}, index:${i}")
            }

            i++
        }

        if (actualNext){
            throw AssertionError("More valued received than expected ($i)")
        }

        if(expectNext){
            throw AssertionError("Fewer values received than expected ($i)")
        }

        return this
    }
}