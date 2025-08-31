package com.spectrum.crimson.config.datasource

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut
import org.springframework.stereotype.Component

@Component
class ReadReplicaBeanPostProcessor : AbstractAdvisingBeanPostProcessor() {
    init {
        val advisor = DefaultBeanFactoryPointcutAdvisor()
        advisor.advice = ReadReplicaInterceptor()
        advisor.setPointcut(
            AnnotationMatchingPointcut(
                null,
                UseReadReplica::class.java,
                true,
            ),
        )
        this.advisor = advisor
    }
}