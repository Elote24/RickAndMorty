package com.elliot.rickandmortyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elliot.rickandmortyapp.R
import com.elliot.rickandmortyapp.databinding.ActivityVerEpisodiosBinding
import com.elliot.rickandmortyapp.databinding.CardCharacterBinding
import com.elliot.rickandmortyapp.databinding.CardCharacterByEpisodeBinding
import com.elliot.rickandmortyapp.models.CharacterView


class CharacterEpisodeAdapter(private val characters:List<CharacterView>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
            val episodeinfobinding = CardCharacterByEpisodeBinding.inflate(layoutInflater, parent, false)
            return EpisodeViewHolder(episodeinfobinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EpisodeViewHolder) {
            val characterinfo = characters[position];
            holder.onBind(characterinfo);

        }
    }


    override fun getItemCount(): Int {
        return characters.size
    }

    inner class EpisodeViewHolder(private val episodeinfobinding:CardCharacterByEpisodeBinding ):
        RecyclerView.ViewHolder(episodeinfobinding.root){

        fun onBind(Character:CharacterView){
            episodeinfobinding.character=Character
            episodeinfobinding.executePendingBindings()
        }
    }

}