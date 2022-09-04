package com.lbc.data

import com.lbc.domain.utils.Reachability

class ReachabilityConnected : Reachability {
    override fun isConnectedToNetwork(): Boolean = true
}

class ReachabilityNotConnected : Reachability {
    override fun isConnectedToNetwork(): Boolean = false
}