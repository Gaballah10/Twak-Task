package com.example.tawktask.network.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import java.io.Serializable

@Parcel
@Entity(tableName = "github_noted_users_table", primaryKeys = ["login"])
data class GithubUser(
    @SerializedName("login") var login: String = "",
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("node_id") var node_id: String?= "",
    @SerializedName("avatar_url") var avatar_url: String?= "",
    @SerializedName("gravatar_id") var gravatarId: String?= "",
    @SerializedName("url") var url: String?= "",
    @SerializedName("html_url") var html_url: String?= "",
    @SerializedName("followers_url") var followers_url: String?= "",
    @SerializedName("following_url") var following_url: String?= "",
    @SerializedName("gists_url") var gists_url: String?= "",
    @SerializedName("starred_url") var starred_url: String?= "",
    @SerializedName("subscriptions_url") var subscriptions_url: String?= "",
    @SerializedName("organizations_url") var organizations_url: String?= "",
    @SerializedName("repos_url") var repos_url: String?= "",
    @SerializedName("events_url") var events_url: String?= "",
    @SerializedName("received_events_url") var received_events_url: String?= "",
    @SerializedName("type") var type: String?= "",
    @SerializedName("site_admin") var site_admin: Boolean? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("company") var company: String? = null,
    @SerializedName("blog") var blog: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("hireable") var hireable: Boolean? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("twitter_username") var twitter_username: String? = null,
    @SerializedName("public_repos") var public_repos: Int? = null,
    @SerializedName("public_gists") var public_gists: Int? = null,
    @SerializedName("followers") var followers: Int? = null,
    @SerializedName("following") var following: Int? = null,
    @SerializedName("created_at") var created_at: String? = null,
    @SerializedName("updated_at") var updated_at: String? = null,
    @SerializedName("note") var note: String? = null
) : Serializable

@Parcel
@Entity(tableName = "github_all_users_table", primaryKeys = ["login"])
data class GithubUserData(
    @SerializedName("login") var login: String = "",
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("node_id") var node_id: String?= "",
    @SerializedName("avatar_url") var avatar_url: String?= "",
    @SerializedName("gravatar_id") var gravatarId: String?= "",
    @SerializedName("url") var url: String?= "",
    @SerializedName("html_url") var html_url: String?= "",
    @SerializedName("followers_url") var followers_url: String?= "",
    @SerializedName("following_url") var following_url: String?= "",
    @SerializedName("gists_url") var gists_url: String?= "",
    @SerializedName("starred_url") var starred_url: String?= "",
    @SerializedName("subscriptions_url") var subscriptions_url: String?= "",
    @SerializedName("organizations_url") var organizations_url: String?= "",
    @SerializedName("repos_url") var repos_url: String?= "",
    @SerializedName("events_url") var events_url: String?= "",
    @SerializedName("received_events_url") var received_events_url: String?= "",
    @SerializedName("type") var type: String?= "",
    @SerializedName("site_admin") var site_admin: Boolean? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("company") var company: String? = null,
    @SerializedName("blog") var blog: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("hireable") var hireable: Boolean? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("twitter_username") var twitter_username: String? = null,
    @SerializedName("public_repos") var public_repos: Int? = null,
    @SerializedName("public_gists") var public_gists: Int? = null,
    @SerializedName("followers") var followers: Int? = null,
    @SerializedName("following") var following: Int? = null,
    @SerializedName("created_at") var created_at: String? = null,
    @SerializedName("updated_at") var updated_at: String? = null,
    @SerializedName("note") var note: String? = null
) : Serializable

