data class UserProfile(
    val id: String,
    val display_name: String,
    val email: String,
    val images: List<Image>
)

data class Image(
    val url: String
)
