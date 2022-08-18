package com.example.eis.ui.api

interface OnApiRequestListener {

    fun onApiRequestStart(apiAction : ApiAction)

    fun onApiRequestFailed(apiAction: ApiAction, t: Throwable)

    fun onApiRequestSuccess(apiAction: ApiAction, result: Any)
}