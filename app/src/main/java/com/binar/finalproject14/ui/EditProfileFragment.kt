package com.binar.finalproject14.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.finalproject14.MainActivity
import com.binar.finalproject14.R
import com.binar.finalproject14.databinding.FragmentEditProfileBinding
import com.binar.finalproject14.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : DialogFragment() {
    private var image_uri: Uri? = null
    private var imageFile: File? = null
    private var imageMultiPart: MultipartBody.Part? = null
    private lateinit var viewModel: ProfileViewModel
    private lateinit var analytics: FirebaseAnalytics
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val PERMISSION_CODE = 100
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        analytics = Firebase.analytics

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).binding.navHome.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getUserProfile("Bearer $it")
        }
        binding.btnUpdate.setOnClickListener {

            val username = binding.etFirstName.text.toString()
            val fName = binding.etFirstName.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val lName = binding.etLastName.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val address = binding.etAddress.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val phone = binding.etPhone.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                if (imageMultiPart == null){
                    viewModel.updateUser(fName, lName, address,phone,"Bearer $it")
                } else{
                    viewModel.updateUser(fName, lName, address,phone,"Bearer $it")
                    viewModel.updateImage(imageMultiPart!!,"Bearer $it")
                }
            }
//            viewModel.saveImage(image_uri.toString())

            viewModel.saveUsername(username)
            Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    etFirstName.setText(it.data?.firstName)
                    etLastName.setText(it.data?.lastName)
                    etAddress.setText(it.data?.address)
                    etPhone.setText(it.data?.phoneNumber)
                }
            }
        }
        binding.btnSelectImage.setOnClickListener(){
            checkingPermission()
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App settings.")
            .setPositiveButton(
                "App Settings"
            ){
                    _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun isGranted(activity: Activity, permission: String, permissions: Array<String>, request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else{
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        }
        else{
            true
        }
    }

    private fun checkingPermission(){
        if (isGranted(
                requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_CODE,
            )
        ){
            chooseImageDialog()
        }
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(context)
            .setMessage("Pilih gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private lateinit var uri: Uri

    private fun handleCameraImage(uri: Uri) {
//        Glide.with(this).load(uri).into(binding.ivEditImage)
        image_uri = uri

        val contentResolver: ContentResolver = requireContext().contentResolver
        val type = contentResolver.getType(uri)

        val fileNameimg = "${System.currentTimeMillis()}.png"
        binding.ivEditImage.setImageURI(uri)
        Toast.makeText(requireContext(), "$image_uri", Toast.LENGTH_SHORT).show()

        val tempFile = File.createTempFile("and1-", fileNameimg, null)
        imageFile = tempFile
        val inputstream = contentResolver.openInputStream(uri)
        tempFile.outputStream().use { result ->
            inputstream?.copyTo(result)
        }
        val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
        imageMultiPart =
            MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result){
                handleCameraImage(uri)
            }
        }

    private fun openCamera() {
        val photoFile = File.createTempFile(
            "IMG_",
            ".jpg",
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )

        uri = FileProvider.getUriForFile(
            requireContext(),
            "${context?.packageName}.provider",
            photoFile
        )
        cameraResult.launch(uri)
    }

    fun openGallery() {
        getContent.launch("image/*")
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = requireContext().contentResolver
                val type = contentResolver.getType(it)
                image_uri = it

                val fileNameimg = "${System.currentTimeMillis()}.png"
                binding.ivEditImage.setImageURI(it)
                Toast.makeText(requireContext(), "$image_uri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("and1-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                imageMultiPart =
                    MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}