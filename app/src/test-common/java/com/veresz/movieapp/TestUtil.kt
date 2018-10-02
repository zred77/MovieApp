package com.veresz.movieapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import org.hamcrest.*
import org.junit.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.SECONDS

fun waitForAdapterChange(recyclerView: RecyclerView) {
    val latch = CountDownLatch(1)
    InstrumentationRegistry.getInstrumentation().runOnMainSync {
        recyclerView.adapter!!.registerAdapterDataObserver(
                object : RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        latch.countDown()
                    }

                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        latch.countDown()
                    }
                })
    }
    if (recyclerView.adapter!!.itemCount > 0) {
        return
    }
    Assert.assertThat(latch.await(10, SECONDS), Matchers.`is`(true))
}