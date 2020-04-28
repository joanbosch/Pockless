package com.pes.pockles.view.bindings

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.data.loading
import com.pes.pockles.view.widget.ContentLoadingProgressBar

@BindingAdapter("app:loadingResource")
fun loadingResource(view: View, res: Resource<*>) {
    if (res.loading) {
        if (view is ContentLoadingProgressBar) {
            view.show()
        } else {
            view.visibility = View.VISIBLE
        }
    } else {
        if (view is ContentLoadingProgressBar) {
            view.hide()
        } else {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("app:srcUrl")
fun srcUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context).load(it).into(imageView)
    }
}

@BindingAdapter("app:resourceErrorHandler")
fun resourceErrorHandler(view: View, res: Resource<*>) {
    if (res is Resource.Error) {
        Snackbar.make(
            view,
            res.exception.localizedMessage ?: view.context.getString(R.string.error_try_later),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}