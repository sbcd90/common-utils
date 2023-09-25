/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.commons.alerting.action

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.core.common.io.stream.StreamInput
import org.opensearch.core.common.io.stream.StreamOutput
import java.io.IOException

class EventListenerRequest : ActionRequest {

    val listener: String

    val topic: String

    constructor(listener: String, topic: String) : super() {
        this.listener = listener
        this.topic = topic
    }

    @Throws(IOException::class)
    constructor(sin: StreamInput) : this(
        sin.readString(),
        sin.readString()
    )

    override fun validate(): ActionRequestValidationException? {
        return null
    }

    @Throws(IOException::class)
    override fun writeTo(out: StreamOutput) {
        out.writeString(listener)
        out.writeString(topic)
    }
}
