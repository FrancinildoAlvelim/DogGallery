package br.com.doggallery.ui.main.state

data class MainViewState(
    var dogList: List<String>? = null,
    var breedList: List<String>? = null,
    var selectedBreed: String? = null
)