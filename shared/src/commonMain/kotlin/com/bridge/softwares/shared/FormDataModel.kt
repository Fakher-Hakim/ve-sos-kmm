package com.bridge.softwares.shared

data class FormDataModel(
    val isPerson: Boolean,
    val amount: String,
    val amountLetter: String,
    val village: String,
    val name: String,
    val address: String,
    val profession: String,
    val email: String,
    val phone: String,
    val raisonSocial: String,
    val matricule: String,
    val premierResponsable: String,
    val bank: String,
    val agence: String,
    val rib: String,
    val month: String,
    val faitA: String,
    val signatureBase64: String,
)