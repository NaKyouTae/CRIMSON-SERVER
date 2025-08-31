package com.spectrum.crimson.config.datasource

import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.sql.DataSource

class DataSourceFactory(
    private val mainDataSource: DataSource,
    private val replicaDataSource: DataSource,
) {
    fun createInstance(): DataSource {
        if (mainDataSource === replicaDataSource) {
            return mainDataSource
        }
        val replicaDataSources: Map<Any, Any> = mapOf(
            "replica" to replicaDataSource,
        )

        val replicaRoutingDataSource = ReadOnlyRoutingDataSource()
        replicaRoutingDataSource.setTargetDataSources(replicaDataSources)
        replicaRoutingDataSource.setDefaultTargetDataSource(mainDataSource)
        replicaRoutingDataSource.afterPropertiesSet()
        return LazyConnectionDataSourceProxy(replicaRoutingDataSource)
    }
}