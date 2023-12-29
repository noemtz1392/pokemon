package mx.com.test.android.list.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import mx.com.test.android.list.Pokemon
import mx.com.test.android.list.ui.R
import timber.log.Timber
import java.util.UUID

@Composable
fun PokemonScreen(
    viewModel: PokemonListViewModel
) {
    PokemonScreenTest(pokemonList = listOf(pokemon1, pokemon2, pokemon3, pokemon4, pokemon5))
}

@Composable
private fun PokemonScreenTest(
    pokemonList: List<Pokemon>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(integerResource(id = R.integer.cells)),
        contentPadding = PaddingValues(
            horizontal = 4.dp,
            vertical = 2.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        content = {
            items(items = pokemonList, key = { it.id }) { pokemon ->
                PokeCard(
                    pokemon = pokemon,
                    navigateToPokeDetail = {

                    }
                )
            }
        }
    )
}


@Composable
private fun PokeCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    navigateToPokeDetail: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 4f / 5f)
            .padding(all = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp,
                            vertical = 8.dp
                        )
                        .fillMaxWidth()
                        .height(130.dp),
                    alignment = Alignment.Center,
                    model = "",
                    contentDescription = "",
                    loading = {}
                )
                Text(text = pokemon.name)
            }
        }

    }
}

@Preview(device = Devices.PHONE)
@Composable
fun Preview() {
    Scaffold(
        modifier = Modifier
    ) { screen ->
        Timber.tag("TAG").e("PokemonScreen: %s", screen)
        PokemonScreenTest(pokemonList = listOf(pokemon1, pokemon2, pokemon3, pokemon4, pokemon5))
    }
}

val pokemon1 = Pokemon(
    id = UUID.randomUUID().toString(),
    name = "Pikachú",
    spriteUrl = null
)
val pokemon2 = Pokemon(
    id = UUID.randomUUID().toString(),
    name = "Pikachú",
    spriteUrl = null
)
val pokemon3 = Pokemon(
    id = UUID.randomUUID().toString(),
    name = "Pikachú",
    spriteUrl = null
)
val pokemon4 = Pokemon(
    id = UUID.randomUUID().toString(),
    name = "Pikachú",
    spriteUrl = null
)
val pokemon5 = Pokemon(
    id = UUID.randomUUID().toString(),
    name = "Pikachú",
    spriteUrl = null
)