package com.pes.pockles.data.storage

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.pes.pockles.data.Resource
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Singleton
class StorageManager {

    private val storage: FirebaseStorage = Firebase.storage

    /**
     * Uploads the given [bitmap] to the given [childReference] path to the firebase storage
     * and returns the public link in a [Resource] wrapper.
     *
     * Images uploaded are bounded to the user as the id of the user is in the name of the image,
     * with the date uploaded. There is no check for duplicate name, but is very strange that
     * there could be a duplicate name.
     *
     * Usage:
     *
     * To upload a file just treat this like a backend api call, observe the changes in the resource
     * and when it is success, the public URL of the uploaded image will be in the data.
     *
     * TODO: Resize image, for not uploading full 4k hd knife at 1kºC images that could take
     * all the available storage.
     *
     * @param bitmap            The bitmap to upload
     * @param childReference    The path to upload the data on the server. Optional
     */
    fun uploadMedia(bitmap: Bitmap, childReference: String = "other"): LiveData<Resource<String>> {
        val result = MediatorLiveData<Resource<String>>()

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            result.value = Resource.Loading
            val storageRef = storage.reference
            val name = it.uid + "_" + SimpleDateFormat(
                "dd/MM/yyyy-hh:mm:sss",
                Locale.getDefault()
            ).format(Calendar.getInstance().time)

            val imageRef = storageRef.child("$childReference/$name.png")

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data = baos.toByteArray()

            val uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    result.value = Resource.Success(uri.toString())
                }.addOnFailureListener { e ->
                    result.value = Resource.Error(e)
                }
            }.addOnFailureListener { e ->
                result.value = Resource.Error(e)
            }

        }

        if (user == null) {
            result.value = Resource.Error(RuntimeException("User not logged"))
        }

        return result
    }
}