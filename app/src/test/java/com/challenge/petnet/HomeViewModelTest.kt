import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.petnet.core.ui.state.UiState
import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.domain.usecase.GetItemsUseCase
import com.challenge.petnet.presentation.home.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getItemsUseCase: GetItemsUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getItemsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `when ViewModel is initialized, should load items successfully`() = runTest {
        val mockItems = listOf(
            Item(
                1,
                "Ração Premium",
                BigDecimal(10),
                "https://images.petz.com.br/fotos/1666985549004.jpg"
            ),
            Item(
                2,
                "Coleira Antipulgas",
                BigDecimal(10),
                "https://images.petz.com.br/fotos/1666985549004.jpg"
            )
        )

        coEvery { getItemsUseCase() } returns Result.success(mockItems)

        viewModel = HomeViewModel(getItemsUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.itemsState.value
        Assert.assertTrue(state is UiState.Success)
        Assert.assertEquals(mockItems, (state as UiState.Success).data)
    }

    @Test
    fun `when ViewModel fails to load items, should emit error state`() = runTest {
        val exception = Exception("Falha de rede")
        coEvery { getItemsUseCase() } returns Result.failure(exception)

        viewModel = HomeViewModel(getItemsUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.itemsState.value
        Assert.assertTrue(state is UiState.Error)
        Assert.assertEquals("Falha de rede", (state as UiState.Error).message)
    }

    @Test
    fun `when searchItems is called with blank query, should return all items`() = runTest {
        val items = listOf(
            Item(
                1,
                "Ração Premium",
                BigDecimal(10),
                "https://images.petz.com.br/fotos/1666985549004.jpg"
            ),
            Item(
                2,
                "Coleira Antipulgas",
                BigDecimal(10),
                "https://images.petz.com.br/fotos/1666985549004.jpg"
            )
        )
        coEvery { getItemsUseCase() } returns Result.success(items)

        viewModel = HomeViewModel(getItemsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.searchItems("")

        val state = viewModel.itemsState.value
        Assert.assertTrue(state is UiState.Success)
        Assert.assertEquals(items, (state as UiState.Success).data)
    }

    @Test
    fun `when searchItems is called with a query, should return filtered items`() = runTest {
        val items = listOf(
            Item(
                1,
                "Ração Premium",
                BigDecimal(10),
                "https://images.petz.com.br/fotos/1666985549004.jpg"
            ),
            Item(
                2,
                "Coleira Antipulgas",
                BigDecimal(10),
                "https://images.petz.com.br/fotos/1666985549004.jpg"
            )
        )
        coEvery { getItemsUseCase() } returns Result.success(items)

        viewModel = HomeViewModel(getItemsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.searchItems("Coleira")

        val state = viewModel.itemsState.value
        Assert.assertTrue(state is UiState.Success)
        Assert.assertEquals(listOf(items[1]), (state as UiState.Success).data)
    }
}
