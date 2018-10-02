package com.veresz.movieapp.ui.movielist

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.veresz.movieapp.FakeTmdbApi
import com.veresz.movieapp.R
import com.veresz.movieapp.api.LiveDataResource
import com.veresz.movieapp.api.NetworkStatus
import com.veresz.movieapp.getMovieList
import com.veresz.movieapp.model.Movie
import com.veresz.movieapp.repository.IMovieRepository
import com.veresz.movieapp.repository.datasource.MovieListDataSourceFactory
import com.veresz.movieapp.utils.ServiceFactory
import com.veresz.movieapp.waitForAdapterChange
import org.hamcrest.Matchers.*
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class MovieListActivityTest {

    private val MOVIES_SIZE = 10
    var networkStatus = MutableLiveData<NetworkStatus>()
    var refreshStatus = MutableLiveData<NetworkStatus>()

    @Before
    fun init() {
        val fakeApi = FakeTmdbApi()
        fakeApi.movies = getMovieList(MOVIES_SIZE)
        ServiceFactory.changeInstance(object : ServiceFactory {
            override fun getRepository(): IMovieRepository {
                return object : IMovieRepository {

                    override fun movieList(query: String?, page: Int): LiveDataResource<PagedList<Movie>> {
                        val config = PagedList.Config.Builder()
                                .setPageSize(20)
                                .setInitialLoadSizeHint(20)
                                .setEnablePlaceholders(true)
                                .build()
                        val sourceFactory = MovieListDataSourceFactory(fakeApi, MutableLiveData())
                        val livePagedList = LivePagedListBuilder(sourceFactory, config)
                                .build()
                        return LiveDataResource(livePagedList,
                                networkStatus,
                                refreshStatus,
                                {},
                                {})
                    }

                    override fun setQueryFilter(queryFilter: String) {
                    }
                }
            }
        })
    }

    @Test
    @Throws(InterruptedException::class, TimeoutException::class)
    fun showResults() {
        val context = InstrumentationRegistry.getTargetContext()
        val intent = Intent(context, MovieListActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
        assertThat(recyclerView.adapter, notNullValue())
        waitForAdapterChange(recyclerView)
        assertThat(recyclerView.adapter!!.itemCount, `is`(MOVIES_SIZE))
    }
}