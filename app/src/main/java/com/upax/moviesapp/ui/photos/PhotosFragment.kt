package com.upax.moviesapp.ui.photos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.upax.moviesapp.R
import com.upax.moviesapp.databinding.FragmentLocationsBinding
import com.upax.moviesapp.databinding.FragmentPhotosBinding
import com.upax.moviesapp.utils.showSimpleAlert
import com.upax.moviesapp.utils.toast
import kotlin.math.roundToInt

class PhotosFragment : Fragment() {

    private lateinit var binding: FragmentPhotosBinding
    lateinit var storageRef: StorageReference

    private var itemsSelected: Int = 0
    private var itemsUpluaded: Int = 0

    val GALERY_INTENT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storageRef = FirebaseStorage.getInstance().reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }


    fun setupListeners(){
        binding.btnUpluad.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
                .putExtra(Intent.ACTION_PICK,true)
                .apply {
                startActivityForResult(this,GALERY_INTENT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALERY_INTENT && data != null){
            val clipData = data.clipData
            if (clipData != null){
                itemsSelected = clipData.itemCount
                initViewUplouading()
                for (i in 0 until itemsSelected){
                    val uri = clipData.getItemAt(i).uri
                    fileUpload(uri)
                }
                itemsUpluaded = 0
            }
        }
    }

    fun initViewUplouading(){
        binding.btnUpluad.text = "Upload Photos 1/$itemsSelected"
        binding.progressBar.progress = 2
        binding.progressPercent.text = "2%"
    }

    private fun fileUpload(uri: Uri) {
        val filePath = storageRef.child("Photos").child(uri?.lastPathSegment.toString())
        filePath.putFile(uri).addOnSuccessListener { task ->
            if (itemsSelected > 1){
                itemsUpluaded += 1
                binding.btnUpluad.text = "Upload Photos ${itemsUpluaded}/$itemsSelected"
                val percent = (100.0 * itemsUpluaded) / itemsSelected;
                binding.progressBar.progress = percent.roundToInt()
                binding.progressPercent.text = "${percent.roundToInt()}%"
                if (itemsSelected == itemsUpluaded){
                    resetViewUploding()
                }
            }else{
                resetViewUploding()
            }
        }.addOnProgressListener {
            if (itemsSelected <= 1){
                val percent = (100.0 * it.bytesTransferred) / it.totalByteCount;
                binding.progressBar.progress = percent.roundToInt()
                binding.progressPercent.text = "${percent.roundToInt()}%"
            }
        }.addOnFailureListener{
            Log.e("Stora", it.message.toString())
        }
    }

    fun resetViewUploding(){
        context?.let { showSimpleAlert(it,"Images uploaded successfully") }
        binding.progressBar.progress = 0
        binding.progressPercent.text = "0%"
        binding.btnUpluad.text = "Upload Photos"
    }


}