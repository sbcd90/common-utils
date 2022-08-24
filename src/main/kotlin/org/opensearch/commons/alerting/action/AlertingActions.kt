/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.opensearch.commons.alerting.action

import org.opensearch.action.ActionType

object AlertingActions {

    const val CREATE_ALERTING_CONFIG_NAME = "cluster:admin/opensearch/alerting/configs/create"

    val CREATE_ALERTING_CONFIG_ACTION_TYPE =
        ActionType(AlertingActions.CREATE_ALERTING_CONFIG_NAME, ::CreateMonitorResponse)

    const val INDEX_MONITOR_ACTION_NAME = "cluster:admin/opendistro/alerting/monitor/write"

    val INDEX_MONITOR_ACTION_TYPE =
        ActionType(AlertingActions.INDEX_MONITOR_ACTION_NAME, ::IndexMonitorResponse)
}
