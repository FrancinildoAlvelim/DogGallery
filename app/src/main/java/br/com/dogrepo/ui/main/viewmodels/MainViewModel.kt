package br.com.dogrepo.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.dogrepo.repository.main.MainRepository
import br.com.dogrepo.ui.main.state.MainStateEvent
import br.com.dogrepo.ui.main.state.MainStateEvent.*
import br.com.dogrepo.ui.main.state.MainViewState
import br.com.dogrepo.util.AbsentLiveData
import br.com.dogrepo.util.api.ApiRequestState
import org.koin.dsl.module

class MainViewModel : ViewModel() {
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private var _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<ApiRequestState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { _stateEvent ->
            handleStateChanges(_stateEvent)
        }

    private fun handleStateChanges(_stateEvent: MainStateEvent): LiveData<ApiRequestState<MainViewState>> =
        when (_stateEvent) {
            is GetDogsRandomly -> MainRepository.getDogsRandomly()
            is GetDogsByBreed -> MainRepository.getDogsByBreed(
                _stateEvent.breed
            )
            is GetAllBreeds -> MainRepository.getAllBreeds()
            is None -> AbsentLiveData.create()
        }

    fun setDogList(newDogList: List<String>) = setState {
        it.apply {
            dogList = newDogList
        }
    }

    fun setBreedList(newBreedList: List<String>) = setState {
        it.apply {
            breedList = newBreedList
        }
    }

    private fun setState(handler: (_viewState: MainViewState) -> MainViewState) {
        val newState = handler(getState())
        _viewState.value = newState
    }

    private fun getState(): MainViewState = viewState.value?.let {
        it
    } ?: MainViewState()

    fun getViewValue() = viewState.value

    fun dispatchEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}


val mainViewModelModule = module {
    single { MainViewModel() }
}
