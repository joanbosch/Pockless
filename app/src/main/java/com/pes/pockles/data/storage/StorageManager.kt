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
     * @param bitmap            The bitmap to upload
     * @param childReference    The path to upload the data on the server. Optional
     */
    fun uploadMedia(
        bitmap: Bitmap,
        fileExtension: String = "png",
        childReference: String = "other"
    ): LiveData<Resource<String>> {
        val result = MediatorLiveData<Resource<String>>()
        result.value = Resource.Loading

        uploadMedia(bitmap, {
            result.value = Resource.Success(it)
        }, {
            result.value = Resource.Error(it)
        }, fileExtension, childReference)

        return result
    }

    /**
     * The same as [uploadMedia] but it emits the results on the given callbacks
     */
    fun uploadMedia(
        bitmap: Bitmap,
        success: (String) -> Unit,
        failure: ((Throwable) -> Unit)? = null,
        fileExtension: String = "png",
        childReference: String = "other"
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val storageRef = storage.reference

            val date = SimpleDateFormat("dd/MM/yyyy-hh:mm:sss", Locale.getDefault())
                .format(Calendar.getInstance().time)
            val imageRef = storageRef.child("$childReference/${it.uid}_$date.$fileExtension")

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data = baos.toByteArray()

            val uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    success(uri.toString())
                }.addOnFailureListener { e ->
                    failure?.let { f -> f(e) }
                }
            }.addOnFailureListener { e ->
                failure?.let { f -> f(e) }
            }
        }

        if (user == null) {
            failure?.let { f -> f(RuntimeException("User not logged")) }
        }
    }

}