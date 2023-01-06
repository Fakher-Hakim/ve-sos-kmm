package com.bridge.softwares.vesos

import android.graphics.Bitmap
import android.os.Environment
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bridge.softwares.vesos.android.R
import com.bridge.softwares.vesos.layout.RadioButtonVELayout
import com.bridge.softwares.vesos.theme.VETheme
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun FormScreen() {

    lateinit var signatureView: SignaturePad

    var isConsent by rememberSaveable { mutableStateOf(false) }

    var village by rememberSaveable { mutableStateOf("") }
    var person by rememberSaveable { mutableStateOf("") }

    var amount by rememberSaveable { mutableStateOf("") }
    var amountLetter by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var bank by rememberSaveable { mutableStateOf("") }
    var agency by rememberSaveable { mutableStateOf("") }
    var rib by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
    ) {
        item {
            SectionHeaderLayout(R.string.consent_header)
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isConsent, colors = CheckboxDefaults.colors(
                    checkedColor = VETheme.colors.primary,
                ), onCheckedChange = {
                    isConsent = it
                })
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.consent_description)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Row(modifier = Modifier.fillMaxSize()) {
                TextField(modifier = Modifier.weight(1f), value = amount, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), colors = textFieldColors(), onValueChange = {
                    amount = it
                })
                Spacer(modifier = Modifier.width(8.dp))
                TextField(modifier = Modifier.weight(3f), value = amountLetter, colors = textFieldColors(), onValueChange = {
                    amountLetter = it
                })
            }
        }

        item {
            Text(text = "Je souhaite parrainer des enfants au village de:")
            Spacer(modifier = Modifier.height(16.dp))
            RadioButtonVELayout(modifier = Modifier.fillMaxWidth(), options = listOf("Gammarth", "Siliana", "Mahres", "Akouda"), onOptionChecked = {
                village = it
            })
        }
        item {
            SectionHeaderLayout(R.string.coordinates_header)
            Spacer(modifier = Modifier.height(24.dp))

            RadioButtonVELayout(
                modifier = Modifier.fillMaxWidth(),
                options = listOf(PARTICULIER, SOCIETE),
                onOptionChecked = {
                    person = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = name, colors = textFieldColors(), label = { Text(text = stringResource(id = R.string.name_lastname_label)) }, onValueChange = {
                name = it
            })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = address, colors = textFieldColors(), label = { Text(text = stringResource(id = R.string.address_label)) }, onValueChange = {
                address = it
            })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = email,
                colors = textFieldColors(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(text = stringResource(id = R.string.e_mail_label)) },
                onValueChange = {
                    email = it
                })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = phone,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = textFieldColors(),
                label = { Text(text = stringResource(id = R.string.telephone_label)) },
                onValueChange = {
                    if (it.length <= 8) phone = it
                })
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeaderLayout(titleRes = R.string.banking_header)
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(modifier = Modifier.weight(1f), value = bank, colors = textFieldColors(), label = { Text(text = stringResource(id = R.string.banque)) }, onValueChange = {
                    bank = it
                })
                Spacer(modifier = Modifier.width(16.dp))
                TextField(modifier = Modifier.weight(2f), value = agency, colors = textFieldColors(), label = { Text(text = stringResource(id = R.string.agence)) }, onValueChange = {
                    agency = it
                })
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = rib,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = textFieldColors(),
                label = { Text(text = stringResource(id = R.string.rib_hint)) },
                onValueChange = {
                    rib = it
                })
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.premier_mois_de_prelevement))
            Spacer(modifier = Modifier.height(16.dp)) //TODO month picker.
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeaderLayout(titleRes = R.string.signature_header)
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(8.dp)).border(width = 2.dp, shape = RoundedCornerShape(8.dp), color = VETheme.colors.primary)
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        signatureView = SignaturePad(context, null)
                        signatureView.setOnSignedListener(object : SignaturePad(context, null), SignaturePad.OnSignedListener {
                            override fun onStartSigning() {}
                            override fun onSigned() {}
                            override fun onClear() {}
                        })
                        signatureView
                    }
                )
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            signatureView.clearView()
                            signatureView.clear()
                        }
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_clear),
                    tint = VETheme.colors.primary,
                    contentDescription = null
                )
            }
        }

        item {
            val context = LocalContext.current
            Spacer(modifier = Modifier.height(48.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = VETheme.colors.primary),
                    onClick = {
                        val bitmap = signatureView.signatureBitmap
                        val filePath = saveBitmap(bitmap)
                        if (bitmap != null) {
                            shareEmail(
                                context = context,
                                subject = "SOS-VE formulaire",
                                addresses = listOf("hakim.fakher@gmail.com").toTypedArray(),
                                body = buildMailBody(),
                                attachmentPath = filePath,
                                onError = {
                                    //TODO show error.
                                }
                            )
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.envoyer), color = Color.White)
                }
            }
        }
    }
}

fun buildMailBody(
    village: String,
    person: String,
    amount: String,
    amountLetter: String,
    name: String,
    address: String,
    email: String,
    phone: String,
    bank: String,
    agency: String,
    rib: String,
    month: String,
    year: String,
    faitA: String,
    le: String,
): String {
    var body = ""

    body += if (person == SOCIETE) {
        "Type personne: Morale" + NEW_LINE +
                "Raison Social: ${raisonSocial.editText?.text}" + NEW_LINE +
                "Matricule fiscale: ${matricule.editText?.text}" + NEW_LINE +
                "Premier Responsable: ${responsable.editText?.text}" + NEW_LINE +
    } else {
        "Type personne: Physique" + NEW_LINE +
                "Nom, Prenom: $name" + NEW_LINE +
                "Profession: $profession" + NEW_LINE
    }

    body += "Adresse: $address" + NEW_LINE +
            "E-Mail: $email" + NEW_LINE +
            "Tel: $phone$NEW_LINE"

    body += "Banque: $bank" + NEW_LINE +
            "Agence: $agency" + NEW_LINE +
            "RIB: $rib" + NEW_LINE +
            "Mois de prelevement: $month" + NEW_LINE +
            "Annee de prelevement: $year" + NEW_LINE

    body += "Fait à: $faitA" + NEW_LINE +
            "Le: $le" + NEW_LINE

    body += "Montant: $amount" + NEW_LINE +
            "En lettre: $amountLetter" + NEW_LINE +
            "Village: $village"
}

private fun saveBitmap(
    bitmap: Bitmap,
): String {
    val externalStoragePath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
    val dir = File(externalStoragePath)
    dir.mkdirs()

    val file = File(dir, "ve_sos_${System.currentTimeMillis()}.png")
    if (file.exists()) file.delete()
    try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file.absolutePath
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return ""
}

@Composable
private fun textFieldColors() = TextFieldDefaults.textFieldColors(
    backgroundColor = VETheme.colors.highlights.copy(alpha = 0.38f),
    focusedIndicatorColor = VETheme.colors.highlights,
    unfocusedIndicatorColor = VETheme.colors.highlights,
    cursorColor = VETheme.colors.primary,
    focusedLabelColor = VETheme.colors.primary
)

@Composable
private fun SectionHeaderLayout(@StringRes titleRes: Int) {
    Box(modifier = Modifier.fillMaxWidth().background(VETheme.colors.primary), contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier.padding(all = 4.dp),
            text = stringResource(id = titleRes),
            color = Color.White,
            style = VETheme.typography.title1,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

const val NEW_LINE = "\n"
const val PARTICULIER = "Particulier"
const val SOCIETE = "Société"
