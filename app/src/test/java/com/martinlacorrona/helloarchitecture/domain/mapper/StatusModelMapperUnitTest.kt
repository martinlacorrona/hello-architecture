package com.martinlacorrona.helloarchitecture.domain.mapper

import com.martinlacorrona.helloarchitecture.data.mapper.StatusModelMapper
import com.martinlacorrona.helloarchitecture.data.util.Status
import com.martinlacorrona.helloarchitecture.domain.model.StatusModel
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