package br.com.doggallery.ui.main.state

sealed class MainStateEvent {
    class GetDogsRandomly: MainStateEvent()
    class SetDogBreed(val selectedBreed: String?): MainStateEvent()
    class GetDogsByBreed(val breed: String): MainStateEvent()
    class GetAllBreeds: MainStateEvent()
    class None: MainStateEvent()
}