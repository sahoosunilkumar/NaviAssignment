package com.navi.assignment.github.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestedTeam {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("node_id")
    @Expose
    var nodeId: String? = null

    @SerializedName("slug")
    @Expose
    var slug: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("privacy")
    @Expose
    var privacy: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null

    @SerializedName("members_url")
    @Expose
    var membersUrl: String? = null

    @SerializedName("repositories_url")
    @Expose
    var repositoriesUrl: String? = null

    @SerializedName("permission")
    @Expose
    var permission: String? = null

    @SerializedName("parent")
    @Expose
    var parent: Any? = null

}