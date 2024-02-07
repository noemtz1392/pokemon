package mx.com.test.android.list.screen.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.solver.widgets.Optimizer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import mx.com.test.android.list.PokemonItem
import mx.com.test.android.list.theme.PokemonTheme
import mx.com.test.android.list.ui.R
import mx.com.test.android.ui.utils.ColorBackground
import mx.com.test.android.ui.utils.ColorPokemonTypeMap
import mx.com.test.android.ui.utils.titleCase

@Composable
fun PokemonInfoScreen(
    viewModel: PokemonInfoViewModel,
    addToFavorite: (PokemonItem) -> Unit = {},
    removeToFavorite: (PokemonItem) -> Unit = {},
    navigateToBack: () -> Unit = {}
) {

    viewModel.uiState.collectAsStateWithLifecycle().let { uiState ->
        uiState.value.pokemonItem?.let { pokemonItem ->
            PokemonInfoScreen(
                pokemonItem = pokemonItem,
                addToFavorite = addToFavorite,
                removeToFavorite = removeToFavorite,
                navigateToBack = navigateToBack
            )
        }
    }
}

@Composable
private fun PokemonInfoScreen(
    pokemonItem: PokemonItem,
    addToFavorite: (PokemonItem) -> Unit = {},
    removeToFavorite: (PokemonItem) -> Unit = {},
    navigateToBack: () -> Unit = {},
) {

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth(),
        optimizationLevel = Optimizer.OPTIMIZATION_RATIO
    ) {
        val (header, image, info) = createRefs()
        PokemonInfoHeader(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(ColorPokemonTypeMap[pokemonItem.types[0]]!!)
                .constrainAs(header) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                },
            pokemonItem = pokemonItem,
            addToFavorite = addToFavorite,
            removeToFavorite = removeToFavorite,
            navigateToBack = navigateToBack
        )

        val center = createGuidelineFromBottom(0.64f)

        PokemonInfo(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.54f)
                .constrainAs(info) {
                    top.linkTo(center)
                    bottom.linkTo(parent.bottom)
                },
        )
        PokemonNetworkImage(
            imageUrl = pokemonItem.imageUrl,
            contentDescription = pokemonItem.name,
            modifier = Modifier
                .size(size = 200.dp)
                .constrainAs(image) {
                    centerHorizontallyTo(parent)
                    top.linkTo(center)
                    bottom.linkTo(center)
                },
        )
    }
}


@Composable
private fun PokemonInfoHeader(
    modifier: Modifier = Modifier,
    pokemonItem: PokemonItem,
    addToFavorite: (PokemonItem) -> Unit = {},
    removeToFavorite: (PokemonItem) -> Unit = {},
    navigateToBack: () -> Unit = {},
) {
    val dotted = rememberAsyncImagePainter(model = R.drawable.dotted)
    val pokeball = rememberAsyncImagePainter(model = R.drawable.pokeball)
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                pokemon = pokemonItem,
                addToFavorite = addToFavorite,
                removeToFavorite = removeToFavorite,
                onBackPressed = navigateToBack,
            )
            PokemonNameAndTypes(pokemonItem = pokemonItem)
        }
        Image(
            painter = dotted,
            contentDescription = null,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 72.dp),
            alignment = Alignment.Center,
            alpha = 0.2f
        )
        Image(
            painter = pokeball,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .aspectRatio(1f)
                .align(Alignment.BottomEnd),
            alignment = Alignment.Center,
            alpha = 0.2f
        )
    }
}

@Composable
private fun PokemonNameAndTypes(
    pokemonItem: PokemonItem
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Column {
            Text(
                text = pokemonItem.name.titleCase(),
                color = Color.White,
                style = PokemonTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                for (pokemonType in pokemonItem.types) {
                    Text(
                        modifier = Modifier
                            .background(
                                Color(0x33FFFFFF),
                                shape = RoundedCornerShape(36.dp)
                            )
                            .padding(top = 4.dp, bottom = 4.dp, start = 24.dp, end = 24.dp),
                        text = pokemonType.titleCase(),
                        color = ColorBackground,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            text = "#00${pokemonItem.id}",
            color = Color.White,
            style = PokemonTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun PokemonNetworkImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center
    )
}

@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier,
    pokemon: PokemonItem,
    addToFavorite: (PokemonItem) -> Unit = {},
    removeToFavorite: (PokemonItem) -> Unit = {},
    onBackPressed: () -> Unit,
) {
    val isFavorite by remember { mutableStateOf(pokemon.isFavorite) }

    val favoriteDescription = if (isFavorite) {
        stringResource(id = R.string.action_remove_to_favorite)
    } else {
        stringResource(id = R.string.action_add_to_favorite)
    }

    val favoriteIcon = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder

    Row(modifier = modifier) {
        IconButton(onClick = onBackPressed) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(R.string.action_back),
                tint = Color.White
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { addToFavorite(pokemon.copy(isFavorite = pokemon.isFavorite.not())) }) {
            Icon(
                imageVector = favoriteIcon,
                contentDescription = favoriteDescription,
                tint = Color.White
            )
        }
    }
}

@Composable
private fun PokemonInfo(
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        )
    ) {

    }
}

@Preview(Devices.NEXUS_5)
@Composable
fun Preview() {
    PokemonTheme {
        PokemonInfoScreen(
            pokemonItem = PokemonItem(
                1,
                "Pikachu",
                "",
                10,
                10,
                listOf("Fire", "Weather"),
                true
            )
        )
    }
}