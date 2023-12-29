package mx.com.test.android.list.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.com.test.android.list.interactor.GetPokemonListUseCase
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

}