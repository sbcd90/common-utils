package org.opensearch.commons.alerting.action

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.core.common.io.stream.StreamInput
import org.opensearch.core.common.io.stream.StreamOutput
import java.io.IOException

class PublishAdRequest : ActionRequest {

    val monitorId: String

    constructor(
        monitorId: String
    ) : super() {
        this.monitorId = monitorId
    }

    @Throws(IOException::class)
    constructor(sin: StreamInput) : this(
        monitorId = sin.readString()
    )

    override fun validate(): ActionRequestValidationException? {
        return null
    }

    override fun writeTo(out: StreamOutput) {
        out.writeString(monitorId)
    }
}
