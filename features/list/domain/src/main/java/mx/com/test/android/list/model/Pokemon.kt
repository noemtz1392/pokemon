package mx.com.test.android.list.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val isFavorite: Boolean = false
)