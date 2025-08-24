package com.spectrum.crimson.config.persistence

import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

class TransactionManager(
    private val manager: PlatformTransactionManager,
) : PlatformTransactionManager by manager {
    enum class State {
        NONE,
        ACTIVE,
        IN_AFTER_COMMIT,
    }

    companion object {
        val state = ThreadLocal.withInitial { State.NONE }!!
    }

    private fun copy(definition: TransactionDefinition?): DefaultTransactionDefinition {
        return if (definition != null) {
            DefaultTransactionDefinition(definition)
        } else {
            DefaultTransactionDefinition()
        }
    }

    override fun getTransaction(definitionParam: TransactionDefinition?): TransactionStatus {
        val parent = state.get()
        if (parent == State.ACTIVE) {
            return manager.getTransaction(definitionParam)
        }
        val definition = if (parent == State.IN_AFTER_COMMIT) {
            copy(definitionParam).apply {
                propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW
            }
        } else {
            definitionParam
        }
        val status = manager.getTransaction(definition)
        state.set(State.ACTIVE)
        TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
            override fun afterCompletion(status: Int) {
                state.set(parent)
            }
        })
        return status
    }
}