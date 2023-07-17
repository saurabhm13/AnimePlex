package com.example.animeplex.data

data class Data(
    val character: CharacterData,
    val favorites: Int,
    val role: String,
    val voice_actors: List<VoiceActor>
)