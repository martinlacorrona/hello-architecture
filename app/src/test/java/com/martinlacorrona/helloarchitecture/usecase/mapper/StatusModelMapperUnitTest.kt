package com.martinlacorrona.helloarchitecture.usecase.mapper

import com.martinlacorrona.helloarchitecture.repository.util.Status
import com.martinlacorrona.helloarchitecture.ui.model.StatusModel
import org.junit.Assert.assertEquals
import org.junit.Test

class StatusModelMapperUnitTest {
    @Test
    fun testMapToStatusModel() {
        assertEquals(StatusModel.SUCCESS, StatusModelMapper.mapToStatusModel(Status.SUCCESS))
        assertEquals(StatusModel.LOADING, StatusModelMapper.mapToStatusModel(Status.LOADING))
        assertEquals(StatusModel.ERROR, StatusModelMapper.mapToStatusModel(Status.ERROR))
    }
}