package org.opensearch.commons.securityanalytics.action

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.commons.alerting.model.Finding
import java.io.IOException

class CorrelateFindingsRequest : ActionRequest {
    val finding: Finding
    val monitorId: String

    constructor(
        finding: Finding,
        monitorId: String
    ) : super() {
        this.finding = finding
        this.monitorId = monitorId
    }

    @Throws(IOException::class)
    constructor(sin: StreamInput) : this(
        finding = Finding.readFrom(sin),
        monitorId = sin.readString()
    )

    override fun validate(): ActionRequestValidationException? {
        return null
    }

    @Throws(IOException::class)
    override fun writeTo(out: StreamOutput) {
        finding.writeTo(out)
        out.writeString(monitorId)
    }
}
