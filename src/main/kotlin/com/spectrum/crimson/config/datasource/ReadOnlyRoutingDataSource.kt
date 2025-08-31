package com.spectrum.crimson.config.datasource

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import java.util.ArrayList

class ReadOnlyRoutingDataSource : AbstractRoutingDataSource() {
    private val dataSources = ArrayList<Any>()
    private var replicaDataSourceKeys: List<Any>? = null

    // register read-only replica
    override fun setTargetDataSources(targetDataSources: Map<Any, Any>) {
        super.setTargetDataSources(targetDataSources)
        this.replicaDataSourceKeys = ArrayList(targetDataSources.keys)
        this.dataSources.addAll(targetDataSources.values)
    }

    // register main
    override fun setDefaultTargetDataSource(defaultTargetDataSource: Any) {
        super.setDefaultTargetDataSource(defaultTargetDataSource)
        this.dataSources.add(defaultTargetDataSource)
    }

    override fun determineCurrentLookupKey(): Any? {
        return when {
            isCurrentTransactionReadOnly() && this.replicaDataSourceKeys!!.isNotEmpty() -> {
                return this.replicaDataSourceKeys!![getRandom(this.replicaDataSourceKeys!!.size)]
            }
            else -> null
        }
    }

    private fun getRandom(high: Int): Int {
        return (Math.random() * high).toInt()
    }

    private fun isCurrentTransactionReadOnly(): Boolean {
        return ReadReplicaContext.isReadReplicaEnabled()
    }
}