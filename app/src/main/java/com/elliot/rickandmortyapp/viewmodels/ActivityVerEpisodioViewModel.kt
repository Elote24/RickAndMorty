package com.elliot.rickandmortyapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.elliot.rickandmortyapp.models.CharacterView
import com.elliot.rickandmortyapp.models.Episode
import com.elliot.rickandmortyapp.repositories.RickAndMortyRepository
import kotlinx.coroutines.launch

class ActivityVerEpisodioViewModel (application: Application) : AndroidViewModel(application) {
    private val rickAndMortyRepository = RickAndMortyRepository();
    val episodeLiveData = MutableLiveData<Episode>()
    val characterListLiveData = MutableLiveData<List<CharacterView>>()

    fun getEpisode(episodeUrl:String) {
        viewModelScope.launch {
            val episode=rickAndMortyRepository.getEpisodeByUrl(episodeUrl)
            episodeLiveData.postValue(episode)
        }
    }
    fun getCharacter(characterUrl:List<String>) {
        viewModelScope.launch {
            val characterList=characterUrl.map { url->rickAndMortyRepository.getCharacterByUrl(characterUrl)}
            characterListLiveData.postValue(characterList)
        }
    }


}