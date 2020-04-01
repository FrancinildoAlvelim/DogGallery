package br.com.doggallery.api.models

data class DogApiResponse(
    val message: List<String>,
    val status: String
)