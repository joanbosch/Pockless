package com.pes.pockles.view.ui.editpock

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.storage.StorageManager
import com.pes.pockles.data.storage.StorageTask
import com.pes.pockles.data.storage.StorageTaskBitmap
import com.pes.pockles.domain.usecases.EditPockUseCase
import com.pes.pockles.model.EditedPock
import com.pes.pockles.model.Pock
import com.pes.pockles.util.livedata.AbsentLiveData
import javax.inject.Inject

class EditPockViewModel @Inject constructor(
    private var useCase: EditPockUseCase,
    private val storageManager: StorageManager
) : ViewModel() {

    private val _errorHandler = MutableLiveData<Boolean>()
    val chatEnabled = MutableLiveData<Boolean>()
    private val _pockToUpdate = MutableLiveData<EditedPock?>()
    val pockContent = MutableLiveData<String>()
    val pockCategory = MutableLiveData<String>()
    private var pockId: String = ""
    private var hasImages = false

    private val _image1 = MutableLiveData<Bitmap>()
    private val _image2 = MutableLiveData<Bitmap>()
    private val _image3 = MutableLiveData<Bitmap>()
    private val _image4 = MutableLiveData<Bitmap>()

    //Number of images that the pock has
    private val _nImg = MutableLiveData<Int>()
    val nImg: LiveData<Int>
        get() = _nImg

    //Image that the user wants to act on
    private val _actImg = MutableLiveData<Int>()
    val actImg: LiveData<Int>
        get() = _actImg

    private val _errorSavingImages = MutableLiveData<Boolean>()
    val errorSavingImages: LiveData<Boolean>
        get() = _errorSavingImages

    val networkCallback: LiveData<Resource<Pock>?>
        get() = Transformations.switchMap(_pockToUpdate) { value: EditedPock? ->
            if (value != null) useCase.execute(pockId, value) else AbsentLiveData.create()
        }

    val errorHandlerCallback: LiveData<Boolean>
        get() = _errorHandler

    init {
        _image1.value = null
        _image2.value = null
        _image3.value = null
        _image4.value = null
        _actImg.value = 0
        _nImg.value = 0
        chatEnabled.value = false
        _errorSavingImages.value = false
    }

    fun updatePock() {
        val category: String = if (pockCategory.value == null)
            "General"
        else pockCategory.value.toString()

        if (pockContent.value == null)
            _errorHandler.value = true
        else {
            if (hasImages) {
                //Store in storageTask the images saved locally
                val storageTask = StorageTask.create(storageManager)

                _image1.value?.let {
                    storageTask.addBitmap(StorageTaskBitmap(_image1.value!!))
                }
                _image2.value?.let {
                    storageTask.addBitmap(StorageTaskBitmap(_image2.value!!))
                }
                _image3.value?.let {
                    storageTask.addBitmap(StorageTaskBitmap(_image3.value!!))
                }
                _image4.value?.let {
                    storageTask.addBitmap(StorageTaskBitmap(_image4.value!!))
                }

                //Try to insert a pock when the images are upload in firebase
                storageTask.upload({
                    _pockToUpdate.value = EditedPock(
                        message = pockContent.value!!,
                        category = category,
                        chatAccess = chatEnabled.value!!,
                        media = it
                    )
                }, {
                    _errorSavingImages.value = true
                }, "pockImages")
            }
            else {
                _pockToUpdate.value = EditedPock(
                    message = pockContent.value!!,
                    category = category,
                    chatAccess = chatEnabled.value!!,
                    media = null
                )
            }
        }
    }

    fun onSaveImage(k: Int) {
        _actImg.value = k
        if (_nImg.value == k - 1) _nImg.value = k
        hasImages = true
    }

    //Local storage of pock images
    fun setBm(bm: Bitmap) {
        when (_actImg.value) {
            1 -> _image1.value = bm
            2 -> _image2.value = bm
            3 -> _image3.value = bm
            4 -> _image4.value = bm
        }
    }

    fun fillFieldsIfEmpty(id: String, editableContent: EditedPock) {
        if (pockId == "") {
            pockId = id
            pockContent.value = editableContent.message
            pockCategory.value = editableContent.category
            chatEnabled.value = editableContent.chatAccess
        }
    }

    fun setCategory(cat: String) {
        pockCategory.value = cat
    }

    fun getCategory(): String {
        return pockCategory.value!!
    }
}