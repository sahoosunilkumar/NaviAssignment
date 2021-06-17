package com.navi.assignment.github.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Base {
    @SerializedName("label")
    @Expose
    var label: String? = null

    @SerializedName("ref")
    @Expose
    var ref: String? = null

    @SerializedName("sha")
    @Expose
    var sha: String? = null

    @SerializedName("user")
    @Expose
    var user: User__2? = null

    @SerializedName("repo")
    @Expose
    var repo: Repo__1? = null

}