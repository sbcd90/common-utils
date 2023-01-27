package org.opensearch.commons.securityanalytics.action

import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.common.xcontent.ToXContent
import org.opensearch.common.xcontent.XContentBuilder
import org.opensearch.commons.notifications.action.BaseResponse
import org.opensearch.rest.RestStatus
import java.io.IOException

class CorrelateFindingsResponse : BaseResponse {
    private var status: RestStatus

    constructor(status: RestStatus) : super() {
        this.status = status
    }

    @Throws(IOException::class)
    constructor(sin: StreamInput) {
        this.status = sin.readEnum(RestStatus::class.java)
    }

    @Throws(IOException::class)
    override fun writeTo(out: StreamOutput) {
        out.writeEnum(status)
    }

    override fun toXContent(builder: XContentBuilder, params: ToXContent.Params): XContentBuilder {
        builder.startObject()
            .field("status", status.status)
        return builder.endObject()
    }

    override fun getStatus(): RestStatus {
        return this.status
    }
}
