package com.bridge.softwares.shared

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class DataProvider {

    fun getMailBodyHtml(formDataModel: FormDataModel): String {
        var body = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                addHtmlLine("Montant", formDataModel.amount) +
                addHtmlLine("Montant Lettre", formDataModel.amountLetter) +
                addHtmlLine("Village", formDataModel.village) +
                addHtmlLine("Bank", formDataModel.bank) +
                addHtmlLine("Agence", formDataModel.agence) +
                addHtmlLine("Rib", formDataModel.rib) +
                addHtmlLine("Mois prelevement", formDataModel.month) +
                addHtmlLine("Fait a", formDataModel.faitA) +
                addHtmlLine("Le", getToday()) +
                getCoordonateData(formDataModel) +
                "<br>" +
                "<br>" +
                "<h1>Signature:</h1>" +
                "<br>" +
                "<img src=data:image/png;base64,${formDataModel.signatureBase64}>" +
                "</body>\n" +
                "</html>\n"
        return body
    }

    private fun getCoordonateData(formDataModel: FormDataModel): String {
        var data = ""
        data = addHtmlLine("Addresse", formDataModel.address) +
                addHtmlLine("Email", formDataModel.email) +
                addHtmlLine("Phone", formDataModel.phone)
        if (formDataModel.isPerson) {
            data += addHtmlLine("Nom et Prenom", formDataModel.name) +
                    addHtmlLine("Profession", formDataModel.profession)
        } else {
            data += addHtmlLine("Raison Sociale", formDataModel.raisonSocial) +
                    addHtmlLine("Matricule Fiscale", formDataModel.matricule) +
                    addHtmlLine("1er Responsable", formDataModel.premierResponsable)
        }

        return data
    }

    private fun addHtmlLine(title: String, value: String) =
        "<h1>$title : </h1> <p>$value</p> <br>"

    fun getToday(): String {
        return Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
    }
}