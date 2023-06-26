package com.artique.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.artique.DummyDataRepo
import com.artique.MainCoroutineRule
import com.artique.folders.FetchFoldersUseCase
import com.artique.folders.FoldersViewModel
import com.artique.models.AlbumContent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FoldersViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var sut: FoldersViewModel
    private val fetchFoldersUseCase: FetchFoldersUseCase = mockk()

    @Before
    fun setup() {
        sut = FoldersViewModel(fetchFoldersUseCase)
    }

    @Test
    fun `fetchfolders should update state`() {
        val data = DummyDataRepo.provideFoldersList()
        coEvery { fetchFoldersUseCase.fetch() } returns data

        sut.loadFolders()

        assert(sut.folders.value.listing is List<AlbumContent>)
        assert((sut.folders.value.listing.size) == data.size)

    }
}