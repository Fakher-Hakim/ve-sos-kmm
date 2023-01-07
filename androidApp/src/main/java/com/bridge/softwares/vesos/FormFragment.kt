package com.bridge.softwares.vesos

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.bridge.softwares.vesos.databinding.FragmentFormBinding
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var signatureBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PermissionsUtils.checkAndRequestPermissions(requireActivity())

        setEventsCoordinateLayout()

        setEventsSignatureLayout()

        binding.submit.setOnClickListener {
            if (!checkInputValid()) {
                Snackbar.make(binding.root, "Veuillez saisie tous les champs obligatoire", LENGTH_LONG).show()
                return@setOnClickListener
            }
            PermissionsUtils.checkAndRequestPermissions(requireActivity())
            if (PermissionsUtils.hasPermissions(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                submitForm()
            } else {
                PermissionsUtils.requestWriteExternalPermission(requireActivity())
            }
        }
    }

    private fun submitForm() {
        signatureBitmap?.let { bitmap ->
            val filePath = saveBitmap(bitmap, "${System.currentTimeMillis()}.png")
            binding.signatureView.signaturePad.clear()
            shareEmail(
                requireActivity(),
                "SOS-VE formulaire",
                listOf("parrainage.enfants@sos-tunisie.org").toTypedArray(),
                null,
                buildBody(),
                filePath
            ) {
                Snackbar.make(binding.root, "Une erreur est survenue, veuillez reessayer", LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun checkInputValid(): Boolean {
        var coordinateValid: Boolean
        var bankingValid: Boolean
        var consentValid: Boolean
        var signatureValid: Boolean
        resetError()
        with(binding.coordinateView) {
            coordinateValid = if (personneMoraleCheckBox.isChecked) {
                checkInputError(raisonSocial) &&
                        checkInputError(matricule) &&
                        checkInputError(responsable) &&
                        checkInputError(adressMoral) &&
                        checkMailInputError(mailMoral) &&
                        checkInputError(telMoral)
            } else {
                checkInputError(name) &&
                        checkInputError(profession) &&
                        checkInputError(adressPerson) &&
                        checkMailInputError(mailPerson) &&
                        checkInputError(telephonePerson)
            }
        }
        with(binding.bankingView) {
            bankingValid =
                checkInputError(banqueTextLayout, 3) &&
                        checkInputError(agenceTextLayout, 3) &&
                        checkInputError(rib, 18) &&
                        checkInputError(ribDivision, 2) &&
                        checkInputError(year, 4)
        }
        with(binding.consentView) {
            consentValid =
                checkInputError(montant, 2) &&
                        checkInputError(montantLettre, 8) &&
                        consentCheckBox.isChecked
        }
        with(binding.signatureView) {
            signatureValid =
                checkInputError(faitA, 3) &&
                        checkInputError(le, 8) &&
                        signatureBitmap != null
        }
        return /*coordinateValid && bankingValid && consentValid && signatureValid &&*/ getSelectedVillage() != null
    }

    private fun resetError() {
        with(binding.coordinateView) {
            matricule.error = null
            responsable.error = null
            adressMoral.error = null
            mailMoral.error = null
            telMoral.error = null

            raisonSocial.error = null
            name.error = null
            profession.error = null
            adressPerson.error = null
            mailPerson.error = null
            telephonePerson.error = null
        }
        with(binding.bankingView) {
            banqueTextLayout.error = null
            agenceTextLayout.error = null
            rib.error = null
            ribDivision.error = null
            year.error = null
        }
        with(binding.consentView) {
            montant.error = null
            montantLettre.error = null
        }
        with(binding.signatureView) {
            faitA.error = null
            le.error = null
        }
    }

    private fun checkInputError(
        inputLayout: TextInputLayout,
        length: Int = 5,
        @StringRes errorMessage: Int = R.string.form_input_error,
    ): Boolean {
        inputLayout.editText?.text?.length?.let {
            if (it < length) {
                inputLayout.error = getString(errorMessage)
                return false
            }
            return true
        } ?: return false
    }

    private fun checkMailInputError(inputLayout: TextInputLayout): Boolean {
        inputLayout.editText?.text?.let {
            val isValid = !TextUtils.isEmpty(it.toString()) && Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()
            if (!isValid) {
                inputLayout.error = getString(R.string.form_input_error)
                return false
            }
            return true
        } ?: return false
    }

    private fun buildBody(): String {
        var body = ""

        with(binding.coordinateView) {
            if (personneMoraleCheckBox.isChecked) {
                body += "Type personne: Morale" + NEW_LINE +
                        "Raison Social: ${raisonSocial.editText?.text}" + NEW_LINE +
                        "Matricule fiscale: ${matricule.editText?.text}" + NEW_LINE +
                        "Premier Responsable: ${responsable.editText?.text}" + NEW_LINE +
                        "Adresse: ${adressMoral.editText?.text}" + NEW_LINE +
                        "E-Mail: ${mailMoral.editText?.text}" + NEW_LINE
                "Tel: ${telMoral.editText?.text}" + NEW_LINE
            } else {
                body += "Type personne: Physique" + NEW_LINE +
                        "Nom, Prenom: ${name.editText?.text}" + NEW_LINE +
                        "Profession: ${profession.editText?.text}" + NEW_LINE +
                        "Adresse: ${adressPerson.editText?.text}" + NEW_LINE +
                        "E-Mail: ${mailPerson.editText?.text}" + NEW_LINE
                "Tel: ${telephonePerson.editText?.text}" + NEW_LINE
            }
        }
        with(binding.bankingView) {
            body += "Banque: ${banqueTextLayout.editText?.text}" + NEW_LINE +
                    "Agence: ${agenceTextLayout.editText?.text}" + NEW_LINE +
                    "RIB: ${rib.editText?.text}" + NEW_LINE +
                    "RIB / division: ${ribDivision.editText?.text}" + NEW_LINE +
                    "Mois de prelevement: ${monthSpinner.selectedItem}" + NEW_LINE +
                    "Annee de prelevement: ${year.editText?.text}" + NEW_LINE
        }
        with(binding.signatureView) {
            body += "Fait à: ${faitA.editText?.text}" + NEW_LINE +
                    "Le: ${le.editText?.text}" + NEW_LINE
        }
        with(binding.consentView) {
            body += "Montant: ${montant.editText?.text}" + NEW_LINE +
                    "En lettre: ${montantLettre.editText?.text}" + NEW_LINE +
                    "Village: ${getSelectedVillage()}"
        }
        return body
    }

    private fun setEventsSignatureLayout() {
        binding.signatureView.clearSignature.setOnClickListener {
            signatureBitmap = null
            binding.signatureView.signaturePad.clear()
        }
        binding.signatureView.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {

            }

            override fun onSigned() {
                signatureBitmap = binding.signatureView.signaturePad.signatureBitmap
            }

            override fun onClear() {

            }
        })

        val calendar = Calendar.getInstance()
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.signatureView.leEditText.setText(dateFormat.format(calendar.time))
    }

    private fun getSelectedVillage(): String? {
        with(binding.consentView.checkBoxVille) {
            val idSelected = checkedRadioButtonId
            return if (idSelected > 0) {
                val radioButton: View = findViewById(idSelected)
                val idx: Int = indexOfChild(radioButton)

                (getChildAt(idx) as RadioButton).text.toString()
            } else {
                null
            }
        }
    }

    private fun setEventsCoordinateLayout() {
        binding.coordinateView.personneMoraleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.coordinateView.personneMoraleCoordinateForm.visibility = View.VISIBLE
            } else {
                binding.coordinateView.personneMoraleCoordinateForm.visibility = View.GONE
            }
        }

        binding.coordinateView.personnePhysiqueCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.coordinateView.personnePhysiqueCoordinateForm.visibility = View.VISIBLE
            } else {
                binding.coordinateView.personnePhysiqueCoordinateForm.visibility = View.GONE
            }
        }
    }

    private fun saveBitmap(
        bitmap: Bitmap,
        fileNameWithExtension: String,
    ): String {
        val externalStoragePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
        val dir = File(externalStoragePath)
        dir.mkdirs()

        val file = File(dir, fileNameWithExtension)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NEW_LINE = "\n"
    }
}