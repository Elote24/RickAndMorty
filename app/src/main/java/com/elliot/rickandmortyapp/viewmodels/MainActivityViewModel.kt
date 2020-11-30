package com.elliot.rickandmortyapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.elliot.rickandmortyapp.models.CharacterView
import com.elliot.rickandmortyapp.repositories.RickAndMortyRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val rickAndMortyRepository = RickAndMortyRepository();
    val rickAndMortyListLiveData = MutableLiveData<List<CharacterView>>()
    private var page = 0;
    private var isLoadingCharacters = false
    var hasNextCharacters = true

    fun getCharacters() {
        if (!isLoadingCharacters && hasNextCharacters) {
            isLoadingCharacters = true
            page++;
            viewModelScope.launch {
                val characterPageRequest = rickAndMortyRepository.getPage(page)

                val listCharactersView = characterPageRequest.results.map { result ->
                    var firstEpisode= rickAndMortyRepository.getEpisodeByUrl(result.episode.first())
                    CharacterView(
                        result.image,
                        result.name,
                        result.status,
                        result.species,
                        result.location.name,
                        firstEpisode.name,
                        firstEpisode.url
                    )
                }

                rickAndMortyListLiveData.postValue(listCharactersView)

                hasNextCharacters = characterPageRequest.info.next.isNotEmpty()
                isLoadingCharacters = false
            }
        }
    }

}