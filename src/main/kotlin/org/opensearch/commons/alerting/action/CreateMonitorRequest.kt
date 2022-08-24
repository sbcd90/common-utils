/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.opensearch.commons.alerting.action

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.common.io.stream.Writeable
import org.opensearch.common.xcontent.ToXContent
import org.opensearch.common.xcontent.ToXContentObject
import org.opensearch.common.xcontent.XContentBuilder
import org.opensearch.common.xcontent.XContentParser
import org.opensearch.common.xcontent.XContentParserUtils
import org.opensearch.commons.alerting.model.Monitor
import org.opensearch.commons.notifications.NotificationConstants
import org.opensearch.commons.utils.fieldIfNotNull
import org.opensearch.commons.utils.logger
import org.opensearch.commons.utils.validateId
import java.io.IOException

class CreateMonitorRequest : ActionRequest, ToXContentObject {
    val configId: String?

    val monitor: Monitor?

    companion object {
        private val log by logger(CreateMonitorRequest::class.java)

        val reader = Writeable.Reader { CreateMonitorRequest(it) }

        @JvmStatic
        @Throws(IOException::class)
        fun parse(parser: XContentParser, id: String? = null): CreateMonitorRequest {
            var configId: String? = id

            XContentParserUtils.ensureExpectedToken(
                XContentParser.Token.START_OBJECT,
                parser.currentToken(),
                parser
            )
            while (parser.nextToken() != XContentParser.Token.END_OBJECT) {
                val fieldName = parser.currentName()
                parser.nextToken()
                when (fieldName) {
                    NotificationConstants.CONFIG_ID_TAG -> configId = parser.textOrNull()
                    else -> {
                        parser.skipChildren()
                        log.info("Unexpected field: $fieldName, while parsing CreateMonitorRequest")
                    }
                }
            }
            if (configId != null) {
                validateId(configId)
            }
            return CreateMonitorRequest(configId)
        }
    }

    override fun toXContent(builder: XContentBuilder?, params: ToXContent.Params?): XContentBuilder {
        builder!!
        return builder.startObject()
            .fieldIfNotNull(NotificationConstants.CONFIG_ID_TAG, configId)
            .endObject()
    }

    constructor(configId: String? = null, monitor: Monitor? = null) {
        this.configId = configId
        this.monitor = monitor
    }

    @Throws(IOException::class)
    constructor(input: StreamInput) : super(input) {
        configId = input.readOptionalString()
        monitor = Monitor.readFrom(input)!!
    }

    @Throws(IOException::class)
    override fun writeTo(output: StreamOutput) {
        super.writeTo(output)
        output.writeOptionalString(configId)
        monitor!!.writeTo(output)
    }

    override fun validate(): ActionRequestValidationException? {
        return null
    }

    fun getMonitor() {
        monitor
    }
}
