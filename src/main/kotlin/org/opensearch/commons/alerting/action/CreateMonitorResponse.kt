/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.opensearch.commons.alerting.action

import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.common.io.stream.Writeable
import org.opensearch.common.xcontent.ToXContent
import org.opensearch.common.xcontent.XContentBuilder
import org.opensearch.common.xcontent.XContentParser
import org.opensearch.common.xcontent.XContentParserUtils
import org.opensearch.commons.notifications.NotificationConstants
import org.opensearch.commons.utils.BaseResponse
import org.opensearch.commons.utils.logger
import java.io.IOException

class CreateMonitorResponse : BaseResponse {
    val configId: String

    companion object {
        private val log by logger(CreateMonitorResponse::class.java)

        val reader = Writeable.Reader { CreateMonitorResponse(it) }

        @JvmStatic
        @Throws(IOException::class)
        fun parse(parser: XContentParser): CreateMonitorResponse {
            var configId: String? = null

            XContentParserUtils.ensureExpectedToken(
                XContentParser.Token.START_OBJECT,
                parser.currentToken(),
                parser
            )
            while (parser.nextToken() != XContentParser.Token.END_OBJECT) {
                val fieldName = parser.currentName()
                parser.nextToken()
                when (fieldName) {
                    NotificationConstants.CONFIG_ID_TAG -> configId = parser.text()
                    else -> {
                        parser.skipChildren()
                        log.info("Unexpected field: $fieldName, while parsing CreateMonitorResponse")
                    }
                }
            }
            configId ?: throw IllegalArgumentException("${NotificationConstants.CONFIG_ID_TAG} field absent")
            return CreateMonitorResponse(configId)
        }
    }

    constructor(configId: String) {
        this.configId = configId
    }

    @Throws(IOException::class)
    constructor(input: StreamInput) : super(input) {
        configId = input.readString()
    }

    @Throws(IOException::class)
    override fun writeTo(output: StreamOutput) {
        output.writeString(configId)
    }

    override fun toXContent(builder: XContentBuilder?, params: ToXContent.Params?): XContentBuilder {
        builder!!
        return builder.startObject()
            .field(NotificationConstants.CONFIG_ID_TAG, configId)
            .endObject()
    }
}
