@file:OptIn(ExperimentalMaterial3Api::class)

package mx.com.test.android.list.screen

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import mx.com.test.android.list.PokemonItem
import mx.com.test.android.list.theme.PokemonTheme
import mx.com.test.android.list.ui.R
import timber.log.Timber

@Composable
fun PokemonScreen(
    viewModel: PokemonListViewModel,
    addToFavorite: (PokemonItem) -> Unit = {},
    removeToFavorite: (PokemonItem) -> Unit = {},
    navigateToPokemonInfo: (Int) -> Unit = {},
) {
    viewModel.uiState.collectAsLazyPagingItems().let { pokemonPagingItems ->
        PokemonScreenTest(
            pokemonPagingItems = pokemonPagingItems,
            addToFavorite = addToFavorite,
            removeToFavorite = removeToFavorite,
            navigateToPokemonInfo = navigateToPokemonInfo
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonScreenTest(
    pokemonPagingItems: LazyPagingItems<PokemonItem>,
    addToFavorite: (PokemonItem) -> Unit,
    removeToFavorite: (PokemonItem) -> Unit,
    navigateToPokemonInfo: (Int) -> Unit = {},
) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(flingAnimationSpec = decayAnimationSpec)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopAppBar(scrollBehavior) },

        ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(integerResource(id = R.integer.cells)),
            contentPadding = innerPadding,
            content = {
                items(pokemonPagingItems.itemCount) { index ->
                    PokeCard(
                        pokemon = pokemonPagingItems[index]!!,
                        addToFavorite = addToFavorite,
                        removeToFavorite = removeToFavorite,
                        navigateToPokemonInfo = navigateToPokemonInfo
                    )
                }
                pokemonPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }

                        loadState.refresh is LoadState.Error -> {

                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }

                        loadState.append is LoadState.Error -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }
                    }
                }
            }
        )
    }
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
    addToFavorite: (PokemonItem) -> Unit,
    removeToFavorite: (PokemonItem) -> Unit,
    navigateToPokemonInfo: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 4f / 5f)
            .padding(all = 8.dp)
            .clickable {
                navigateToPokemonInfo(pokemon.id)
            },
        shape = RoundedCornerShape(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            IconButton(
                onClick = {
                    if (pokemon.isFavorite) {
                        removeToFavorite(pokemon.copy(isFavorite = false))
                    } else {
                        addToFavorite(pokemon.copy(isFavorite = true))
                    }
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {

                Icon(
                    imageVector = if (pokemon.isFavorite) {
                        Icons.Rounded.Favorite
                    } else {
                        Icons.Rounded.FavoriteBorder
                    },
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()) {

    Box {
        LargeTopAppBar(
            modifier = Modifier.background(Color.Transparent),
            title = {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Pokedex",
                    color = Color.Black,
                    style = PokemonTheme.typography.titleLarge
                )
            },
            actions = {
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = stringResource(R.string.action_menu)
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
        Image(
            painter = painterResource(id = R.drawable.pokeball_1),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.TopEnd)
                .offset(x = 0.dp, y = (-64).dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Preview(device = Devices.PHONE)
@Composable
fun Preview() {
    Scaffold(
        modifier = Modifier
    ) { screen ->
        Timber.tag("TAG").e("PokemonScreen: %s", screen)
        LoadingNextPageItem(Modifier)
    }
}

fun getPokemonImage(id: String) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${id}.png"
