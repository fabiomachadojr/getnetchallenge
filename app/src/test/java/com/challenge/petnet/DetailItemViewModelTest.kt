package com.challenge.petnet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.petnet.core.ui.state.UiState
import com.challenge.petnet.domain.model.DetailItem
import com.challenge.petnet.domain.usecase.GetDetailItemUseCase
import com.challenge.petnet.presentation.detail.viewmodel.DetailItemViewModel
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

@ExperimentalCoroutinesApi
class DetailItemViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getDetailItemUseCase: GetDetailItemUseCase
    private lateinit var viewModel: DetailItemViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getDetailItemUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `when loadItem is called, should emit Success state with item detail`() = runTest {
        val mockDetailItem = DetailItem(
            id = 1,
            name = "Ração",
            description = "Comida saudável para cães",
            price = "10",
            weight = "10",
            dimensions = "10",
            imageUrl = "https://images.petz.com.br/fotos/1666985549004.jpg"
        )
        coEvery { getDetailItemUseCase("1") } returns Result.success(mockDetailItem)

        viewModel = DetailItemViewModel(getDetailItemUseCase)

        viewModel.loadItem("1")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.itemState.value
        Assert.assertTrue(state is UiState.Success)
        Assert.assertEquals(mockDetailItem, (state as UiState.Success).data)
    }

    @Test
    fun `when loadItem fails, should emit Error state`() = runTest {
        val exception = Exception("Falha ao carregar detalhes")
        coEvery { getDetailItemUseCase("2") } returns Result.failure(exception)

        viewModel = DetailItemViewModel(getDetailItemUseCase)

        viewModel.loadItem("2")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.itemState.value
        Assert.assertTrue(state is UiState.Error)
        Assert.assertEquals("Falha ao carregar detalhes", (state as UiState.Error).message)
    }

    @Test
    fun `when loadItem is called, should emit Loading state first`() = runTest {
        coEvery { getDetailItemUseCase("3") } returns Result.success(
            DetailItem(
                id = 3,
                name = "Ração",
                description = "Comida saudável para cães",
                price = "10",
                weight = "10",
                dimensions = "10",
                imageUrl = "https://images.petz.com.br/fotos/1666985549004.jpg"
            )
        )

        viewModel = DetailItemViewModel(getDetailItemUseCase)

        viewModel.loadItem("3")

        Assert.assertEquals(UiState.Loading, viewModel.itemState.value)

        testDispatcher.scheduler.advanceUntilIdle()
    }
}
