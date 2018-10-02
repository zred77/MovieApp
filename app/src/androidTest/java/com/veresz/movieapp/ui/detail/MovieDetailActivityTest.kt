package com.veresz.movieapp.ui.detail

import android.content.Intent
import android.widget.TextView
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.veresz.movieapp.R
import com.veresz.movieapp.getRandomMovie
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest {

    @Test
    @Throws(InterruptedException::class, TimeoutException::class)
    fun displaysText() {
        val context = InstrumentationRegistry.getTargetContext().applicationContext
        val movie = getRandomMovie()
        val intent = MovieDetailActivity.newIntent(context, movie)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        val title = activity.findViewById<TextView>(R.id.title)
        val overview = activity.findViewById<TextView>(R.id.overview)
        assertEquals(title.text, movie.title)
        assertEquals(overview.text, movie.overview)
    }
}