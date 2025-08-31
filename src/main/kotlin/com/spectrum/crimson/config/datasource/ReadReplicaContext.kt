package com.spectrum.crimson.config.datasource

object ReadReplicaContext {
    val useReadReplica = ThreadLocal<Boolean?>()

    fun isReadReplicaEnabled(): Boolean = useReadReplica.get() == true

    inline fun <T> withReadReplicaEnabled(block: () -> T): T {
        val prev = useReadReplica.get()
        useReadReplica.set(true)

        try {
            return block()
        } finally {
            useReadReplica.set(prev)
        }
    }
}