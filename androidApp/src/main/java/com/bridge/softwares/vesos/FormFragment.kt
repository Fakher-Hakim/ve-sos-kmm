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
import com.bridge.softwares.shared.DataProvider
import com.bridge.softwares.shared.FormDataModel
import com.bridge.softwares.vesos.databinding.FragmentFormBinding
import com.bridge.softwares.vesos.utils.ImageUtil
import com.bridge.softwares.vesos.utils.PermissionsUtils
import com.bridge.softwares.vesos.utils.shareEmail
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


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
                buildMailHtmlBody(),
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

    private fun buildMailHtmlBody(): String {
        val isPerson = binding.coordinateView.personnePhysiqueCheckBox.isChecked
        return DataProvider().getMailBodyHtml(
            FormDataModel(
                isPerson = isPerson,
                amount = binding.consentView.montant.editText?.text.toString(),
                amountLetter = binding.consentView.montantLettre.editText?.text.toString(),
                village = getSelectedVillage().orEmpty(),
                name = binding.coordinateView.name.editText?.text.toString(),
                address = if (isPerson) binding.coordinateView.adressPerson.editText?.text.toString() else binding.coordinateView.adressMoral.editText?.text.toString(),
                profession = binding.coordinateView.profession.editText?.text.toString(),
                email = if (isPerson) binding.coordinateView.mailPerson.editText?.text.toString() else binding.coordinateView.mailMoral.editText?.text.toString(),
                phone = binding.coordinateView.telephonePerson.editText?.text.toString(),
                raisonSocial = binding.coordinateView.raisonSocial.editText?.text.toString(),
                matricule = binding.coordinateView.matricule.editText?.text.toString(),
                premierResponsable = binding.coordinateView.responsable.editText?.text.toString(),
                bank = binding.bankingView.banqueTextLayout.editText?.text.toString(),
                agence = binding.bankingView.agenceTextLayout.editText?.text.toString(),
                rib = "${binding.bankingView.rib.editText?.text.toString()} / ${binding.bankingView.ribDivision.editText?.text.toString()}",
                month = binding.bankingView.monthSpinner.selectedItem.toString(),
                faitA = binding.signatureView.faitA.editText?.text.toString(),
                signatureBase64 = signatureBitmap?.let { ImageUtil.convert(it) }.orEmpty(),
            )
        )
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

        binding.signatureView.leEditText.setText(DataProvider().getToday())
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