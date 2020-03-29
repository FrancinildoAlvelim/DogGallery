package br.com.dogrepo.ui.main.state

sealed class MainStateEvent {
    class GetDogsRandomly: MainStateEvent()
    class GetDogsByBreed(val breed: String): MainStateEvent()
    class GetAllBreeds: MainStateEvent()
    class None: MainStateEvent()
}