@file:OptIn(ExperimentalMaterial3Api::class)

package mx.com.test.android.list.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import mx.com.test.android.list.screen.PokemonViewModel
import mx.com.test.android.list.theme.PokemonTheme
import mx.com.test.android.list.theme.pokemonTypography
import mx.com.test.android.list.ui.R
import timber.log.Timber

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel,
    addToFavorite: (PokemonItem) -> Unit = {},
    removeToFavorite: (PokemonItem) -> Unit = {},
    navigateToPokemonInfo: (Int) -> Unit = {},
) {
    val scrollState = rememberLazyListState()

    viewModel.uiState.collectAsLazyPagingItems().let { pokemonPagingItems ->
        PokemonScreenTest(
            pokemonPagingItems = pokemonPagingItems,
            scrollState = scrollState,
            addToFavorite = addToFavorite,
            removeToFavorite = removeToFavorite,
            navigateToPokemonInfo = navigateToPokemonInfo
        )
    }
}

@Composable
private fun PokemonScreenTest(
    pokemonPagingItems: LazyPagingItems<PokemonItem>,
    scrollState: LazyListState,
    addToFavorite: (PokemonItem) -> Unit,
    removeToFavorite: (PokemonItem) -> Unit,
    navigateToPokemonInfo: (Int) -> Unit = {},
) {


    Scaffold(
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.statusBars)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = scrollState,
            contentPadding = WindowInsets.systemBars
                .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                .asPaddingValues(),
            content = {
                items(pokemonPagingItems.itemCount) { index ->
                    PokemonCard(
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
private fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: PokemonItem,
    addToFavorite: (PokemonItem) -> Unit,
    removeToFavorite: (PokemonItem) -> Unit,
    navigateToPokemonInfo: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { navigateToPokemonInfo(pokemon.id) }),
        colors = CardDefaults.cardColors(containerColor = PokemonTheme.colorScheme.onSurface),
        elevation = CardDefaults.cardElevation(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(96.dp, 96.dp)
                    .clip(RoundedCornerShape(16.dp)),
                alignment = Alignment.Center,
                model = pokemon.imageUrl,
                contentDescription = "",
                loading = {}
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.align(Alignment.CenterVertically)) {

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = pokemon.name,
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    style = pokemonTypography.titleMedium,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        addToFavorite(pokemon.copy(isFavorite = pokemon.isFavorite.not()))
                    },
                ) {

                    Icon(
                        imageVector = if (pokemon.isFavorite) {
                            Icons.Rounded.Favorite
                        } else {
                            Icons.Rounded.FavoriteBorder
                        },
                        contentDescription = "Favorite",
                        tint = PokemonTheme.colorScheme.outline
                    )
                }
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
            painter = painterResource(id = R.drawable.pokeball),
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
