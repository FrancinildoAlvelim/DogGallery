package br.com.dogrepo.api.models

data class DogApiResponse(
    val message: List<String>,
    val status: String
)