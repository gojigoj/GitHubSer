package com.gojigo.githubser.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide.with
import com.gojigo.githubser.R

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun SearchView.setTypeFace(context: Context) {
    val font = ResourcesCompat.getFont(context, R.font.raleway_medium)
    val searchText = this.findViewById<View>(androidx.appcompat.R.id.search_src_text) as TextView
    searchText.typeface = font
}

fun ImageView.setRoundImage(imageUrl: String) {
    GlideApp.with(this.context)
        .load(imageUrl)
        .placeholder(ContextCompat.getDrawable(this.context, R.drawable.ic_base_image))
        .error(ContextCompat.getDrawable(this.context, R.drawable.ic_broken_image))
        .circleCrop()
        .into(this)
}