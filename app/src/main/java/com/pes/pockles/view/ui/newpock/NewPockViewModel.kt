package com.pes.pockles.view.ui.newpock

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.storage.StorageTask
import com.pes.pockles.data.storage.StorageTaskBitmap
import com.pes.pockles.domain.usecases.NewPockUseCase
import com.pes.pockles.model.Location
import com.pes.pockles.model.NewPock
import com.pes.pockles.model.Pock
import com.pes.pockles.util.livedata.AbsentLiveData
import java.io.InputStream
import javax.inject.Inject

class NewPockViewModel @Inject constructor(
    private var useCase: NewPockUseCase, private val storageTask: StorageTask
) : ViewModel() {

    private val _errorHandler = MutableLiveData<Boolean>()
    private val _chatEnabled = MutableLiveData<Boolean>()
    private val _pockToInsert = MutableLiveData<NewPock?>()
    private var _mediaURL = Array<String?>(4){null}
    private var imageBm = Array<Bitmap?>(4){null}
    val pockContent = MutableLiveData<String>()
    val pockCategory = MutableLiveData<String>()
    private var _mediaURL1: String? = null
    private var _mediaURL2: String? = null
    private var _mediaURL3: String? = null
    private var _mediaURL4: String? = null
    private var urlList: List<String>? = null

    private  val _image1 = MutableLiveData<Bitmap>()
    val image1: LiveData<Bitmap>
        get() = _image1

    private  val _image2 = MutableLiveData<Bitmap>()
    val image2: LiveData<Bitmap>
        get() = _image2

    private  val _image3 = MutableLiveData<Bitmap>()
    val image3: LiveData<Bitmap>
        get() = _image3

    private  val _image4 = MutableLiveData<Bitmap>()
    val image4: LiveData<Bitmap>
        get() = _image4
    /*
    private val _bmList = MutableLiveData<ArrayList<Bitmap>>()
    val bmList: LiveData<ArrayList<Bitmap>>
        get() = _bmList*/

    private val _nImg = MutableLiveData<Int>()
    val nImg: LiveData<Int>
        get() = _nImg

    private val _actImg = MutableLiveData<Int>()
    val actImg: LiveData<Int>
        get() = _actImg

    private val _goUploadImage = MutableLiveData<Boolean>()
    val goUploadImage: LiveData<Boolean>
        get() = _goUploadImage

    val networkCallback: LiveData<Resource<Pock>?>
        get() = Transformations.switchMap(_pockToInsert) { value: NewPock? ->
            if (value != null) useCase.execute(value) else AbsentLiveData.create()
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
        _chatEnabled.value = false
        _goUploadImage.value = false
    }

    fun insertPock(location: Location) {
        val category: String = if (pockCategory.value == null)
            "General"
        else pockCategory.value.toString()

        if (pockContent.value == null)
            _errorHandler.value = true
        else {
            _pockToInsert.value = NewPock(
                message = pockContent.value!!,
                category = category,
                chatAccess = _chatEnabled.value!!,
                location = location,
                media = urlList
            )
        }
    }

    fun onUploadImage1() {
        _actImg.value = 1
        if (_nImg.value == 0)  _nImg.value = 1
        _goUploadImage.value = true
    }

    fun onUploadImage2() {
        _actImg.value = 2
        if (_nImg.value == 1) _nImg.value = 2
        _goUploadImage.value = true
    }

    fun onUploadImage3() {
        _actImg.value = 3
        if (_nImg.value == 2) _nImg.value = 3
        _goUploadImage.value = true
    }

    fun onUploadImage4() {
        _actImg.value = 4
        if (_nImg.value == 3) _nImg.value = 4
        _goUploadImage.value = true
    }

    fun setBm(bm: Bitmap) {
        when (_actImg.value) {
            1 -> _image1.value = bm
            2 -> _image2.value = bm
            3 -> _image3.value = bm
            4 -> _image4.value = bm
        }
        //imageBm[_actImg.value!!] = bm
    }

    fun getBm(it: Int): Bitmap? {
        return imageBm[it]
    }

    fun uploadImages(): LiveData<Resource<List<String>>> {
        storageTask.addBitmap(StorageTaskBitmap(_image1.value!!))
        storageTask.addBitmap(StorageTaskBitmap(_image2.value!!))
        storageTask.addBitmap(StorageTaskBitmap(_image3.value!!))
        storageTask.addBitmap(StorageTaskBitmap(_image4.value!!))
        return storageTask.uploadAsLiveData("pockImages")
    }
/*
    fun uploadMedia(bitmap: Bitmap): LiveData<Resource<String>> {
        return storageManager.uploadMedia(bitmap, "pockImages")
    }*/
/*
    fun uploadGif(gif: InputStream): LiveData<Resource<String>> {
        return storageManager.uploadMediaGif(gif, "pockGifs")
    }*/

    fun setUrlList (data: List<String>) {
        urlList = data
        Log.i("Enter Set Photo", "List Set")
    }

    fun setImageUrl(data: String, k: Int) {
        when (k) {
            1 -> _mediaURL1 = data
            2 -> _mediaURL2 = data
            3 -> _mediaURL3 = data
            4 -> _mediaURL4 = data
        }

        //_mediaURL[k] = data
    }

}