package robert.pakpahan.jetpro1.data.datarepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import robert.pakpahan.jetpro1.data.datasource.local.entity.TvShowDataSource
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailGenreTvShowEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailTvShowResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailTvShowWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.TvShowWithGenre
import robert.pakpahan.jetpro1.data.datasource.remote.RemoteDataSource
import robert.pakpahan.jetpro1.utils.Executors
import robert.pakpahan.jetpro1.utils.LiveDataTestUtil
import robert.pakpahan.jetpro1.vo.Resource

@RunWith(MockitoJUnitRunner::class)
class TvShowRepositoryTest {
    private lateinit var repository: TvShowRepositoryFake

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var executors: Executors

    @Mock
    private lateinit var local: TvShowDataSource

    @Mock
    private lateinit var remote: RemoteDataSource

    @Mock
    lateinit var dataSourceFactory: DataSource.Factory<Int, TvShowWithGenre>

    @Before
    fun setUp() {
        repository = TvShowRepositoryFake(executors, local, remote)
    }

    @Test
    fun getTvShows() {
        val simpleSQLiteQuery = SimpleSQLiteQuery("")

        Mockito.`when`(local.getResultRawQuery(simpleSQLiteQuery)).thenReturn(dataSourceFactory)

        val value = LiveDataTestUtil.getValue(repository.getResult(simpleSQLiteQuery))

        verify(local).getResultRawQuery(simpleSQLiteQuery)
        assertNotNull(value)
    }

    @Test
    fun getDetailTvShow() {
        val pk = 100
        val entity = DetailTvShowResponseEntity(pk)
        val genre = listOf(DetailGenreTvShowEntity(102, 19, 10, "test"))

        Mockito.`when`(local.getDetail(pk)).thenReturn(MutableLiveData(DetailTvShowWithGenre(entity, genre)))

        val resource = LiveDataTestUtil.getValue(repository.getDetail(pk))
        verify(local).getDetail(pk)
        assertTrue(resource is Resource.Success)
        when (resource) {
            is Resource.Success -> {
                assertEquals(pk, resource.data.id)
                assertEquals(genre[0].genre_code, resource.data.genres[0].genre_code)
                assertEquals(genre[0].name, resource.data.genres[0].name)
            } else -> return
        }
    }

    @Test
    fun getFavoriteTvShows() {
        val simpleSQLiteQuery = SimpleSQLiteQuery("")

        Mockito.`when`(local.getFavorite(simpleSQLiteQuery)).thenReturn(dataSourceFactory)

        val value = LiveDataTestUtil.getValue(repository.getFavorite(simpleSQLiteQuery))

        verify(local).getFavorite(simpleSQLiteQuery)
        assertNotNull(value)
    }
}