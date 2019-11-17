package fr.imt.vrignaud

data class OffersWrapper(val offers: List<Offer>)

data class Offer(val type: String, val sliceValue: Int = -1, val value: Int)
