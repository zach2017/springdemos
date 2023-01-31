package zac.graphqldemo.graphql

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import zac.graphql.api.ZacTestQueryResolver
@Component
class ZacResolver : ZacTestQueryResolver {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(ZacResolver::class.java)
    }

    override fun zacTest(): List<String> {
        LOG.info("HERE")
        return listOf<String>("works")
    }
}