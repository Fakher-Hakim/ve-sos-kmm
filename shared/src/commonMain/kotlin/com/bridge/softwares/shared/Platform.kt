package com.bridge.softwares.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform