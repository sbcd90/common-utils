package org.opensearch.commons.securityanalytics

import org.opensearch.action.ActionListener
import org.opensearch.action.ActionResponse
import org.opensearch.client.node.NodeClient
import org.opensearch.common.io.stream.Writeable
import org.opensearch.commons.notifications.action.BaseResponse
import org.opensearch.commons.securityanalytics.action.CorrelateFindingsRequest
import org.opensearch.commons.securityanalytics.action.CorrelateFindingsResponse
import org.opensearch.commons.securityanalytics.action.SecurityAnalyticsActions
import org.opensearch.commons.utils.recreateObject

object SecurityAnalyticsPluginInterface {

    fun correlateFinding(
        client: NodeClient,
        request: CorrelateFindingsRequest,
        listener: ActionListener<CorrelateFindingsResponse>
    ) {
        client.execute(
            SecurityAnalyticsActions.CORRELATE_FINDING_ACTION_TYPE,
            request,
            wrapActionListener(listener) { response ->
                recreateObject(response) {
                    CorrelateFindingsResponse(
                        it
                    )
                }
            }
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <Response : BaseResponse> wrapActionListener(
        listener: ActionListener<Response>,
        recreate: (Writeable) -> Response
    ): ActionListener<Response> {
        return object : ActionListener<ActionResponse> {
            override fun onResponse(response: ActionResponse) {
                val recreated = response as? Response ?: recreate(response)
                listener.onResponse(recreated)
            }

            override fun onFailure(exception: java.lang.Exception) {
                listener.onFailure(exception)
            }
        } as ActionListener<Response>
    }
}
