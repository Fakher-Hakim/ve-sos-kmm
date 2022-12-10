package com.bridge.softwares.vesos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform