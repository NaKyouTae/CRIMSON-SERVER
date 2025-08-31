package com.spectrum.crimson.config.datasource

import com.spectrum.crimson.config.datasource.ReadReplicaContext.withReadReplicaEnabled
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class ReadReplicaInterceptor : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? {
        return withReadReplicaEnabled { invocation.proceed() }
    }
}