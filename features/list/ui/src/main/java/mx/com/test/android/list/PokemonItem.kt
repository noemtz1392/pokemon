package mx.com.test.android.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val isFavorite: Boolean = false
) : Parcelable