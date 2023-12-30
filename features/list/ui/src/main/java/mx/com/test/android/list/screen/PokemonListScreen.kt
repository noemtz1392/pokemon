package mx.com.test.android.list.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import mx.com.test.android.list.PokemonItem
import mx.com.test.android.list.ui.R
import timber.log.Timber

@Composable
fun PokemonScreen(
    viewModel: PokemonListViewModel
) {
    viewModel.uiState.collectAsLazyPagingItems().let { pokemonPagingItems ->
        PokemonScreenTest(pokemonPagingItems = pokemonPagingItems)
    }
}

@Composable
private fun PokemonScreenTest(
    pokemonPagingItems: LazyPagingItems<PokemonItem>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(integerResource(id = R.integer.cells)),
        content = {
            items(pokemonPagingItems.itemCount) { index ->
                PokeCard(
                    pokemon = pokemonPagingItems[index]!!,
                    navigateToPokeDetail = {

                    }
                )
            }
            pokemonPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {

                    }

                    loadState.refresh is LoadState.Error -> {

                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }

                    }

                    loadState.append is LoadState.Error -> {

                    }
                }
            }
        }
    )
}

@Composable
private fun LoadingNextPageItem(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}


@Composable
private fun PokeCard(
    modifier: Modifier = Modifier,
    pokemon: PokemonItem,
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
                            vertical = 16.dp
                        )
                        .fillMaxWidth()
                        .height(128.dp),
                    alignment = Alignment.Center,
                    model = getPokemonImage(pokemon.id.toString()),
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
    }
}

fun getPokemonImage(id: String) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${id}.png"
