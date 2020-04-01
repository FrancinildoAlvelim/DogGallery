package br.com.doggallery.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.doggallery.repository.main.MainRepository
import br.com.doggallery.ui.main.state.MainStateEvent
import br.com.doggallery.ui.main.state.MainStateEvent.*
import br.com.doggallery.ui.main.state.MainViewState
import br.com.doggallery.util.AbsentLiveData
import br.com.doggallery.util.api.DataState
import org.koin.dsl.module

class MainViewModel : ViewModel() {
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private var _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { _stateEvent ->
            handleStateChanges(_stateEvent)
        }

    private fun handleStateChanges(_stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> =
        when (_stateEvent) {
            is GetDogsRandomly -> MainRepository.getDogsRandomly()
            is GetDogsByBreed -> MainRepository.getDogsByBreed(
                _stateEvent.breed
            )
            is SetDogBreed -> object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    value = DataState.data(
                        message = null,
                        data = MainViewState(
                            selectedBreed = _stateEvent.selectedBreed
                        )
                    )
                }
            }
            is GetAllBreeds -> MainRepository.getAllBreeds()
            is None -> AbsentLiveData.create()
        }

    fun setDogList(newDogList: List<String>) = setState {
        it.apply {
            dogList = newDogList
        }
    }

    fun setSelectedBreed(newBreed: String) = setState {
        it.apply {
            selectedBreed = newBreed
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

    private fun getState(): MainViewState = viewState.value ?: MainViewState()

    fun getViewValue() = viewState.value

    fun dispatchEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}


val mainViewModelModule = module {
    single { MainViewModel() }
}
