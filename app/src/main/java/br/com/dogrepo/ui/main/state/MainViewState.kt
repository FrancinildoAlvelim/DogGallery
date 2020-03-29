package br.com.dogrepo.ui.main.state

data class MainViewState(
    var dogList: List<String>? = null,
    var breedList: List<String>? = null
)