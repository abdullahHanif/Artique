package com.artique.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.artique.DummyDataRepo
import com.artique.MainCoroutineRule
import com.artique.folderdetail.FetchFolderDetailsUseCase
import com.artique.folderdetail.FolderDetailsFetchFilter
import com.artique.folderdetail.FolderDetailsViewModel
import com.artique.models.AlbumContent
import com.artique.models.MimeType.Movies
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FolderDetailsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var sut: FolderDetailsViewModel
    private val fetchFoldersUseCase: FetchFolderDetailsUseCase = mockk()

    @Before
    fun setup() {
        sut = FolderDetailsViewModel(fetchFoldersUseCase)
    }

    @Test
    fun `fetchfolderDetails should update state`() {
        val data = DummyDataRepo.provideFoldersList()
        val filter = FolderDetailsFetchFilter.ByMimeType(Movies)
        coEvery { fetchFoldersUseCase.fetch(filter) } returns data

        sut.loadFolders(filter)

        assert(sut.folders.value.listing is List<AlbumContent>)
        assert((sut.folders.value.listing.size) == data.size)

    }
}