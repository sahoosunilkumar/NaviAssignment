package com.navi.assignment.github.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReviewComment {
    @SerializedName("href")
    @Expose
    var href: String? = null

}