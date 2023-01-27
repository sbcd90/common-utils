package org.opensearch.commons.securityanalytics.action

import org.opensearch.action.ActionType

object SecurityAnalyticsActions {
    const val CORRELATE_FINDING_ACTION_NAME = "cluster:admin/opensearch/securityanalytics/finding/correlate"

    @JvmField
    val CORRELATE_FINDING_ACTION_TYPE =
        ActionType(CORRELATE_FINDING_ACTION_NAME, ::CorrelateFindingsResponse)
}
