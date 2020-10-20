package com.github.lipen.multiarray.internal

internal interface WithDomains {
    val domains: List<IntRange>
}

internal class WithDomainsImpl(
    override val domains: List<IntRange>
) : WithDomains
