package com.elliot.rickandmortyapp.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elliot.rickandmortyapp.OnBottomReachedListener
import com.elliot.rickandmortyapp.R
import com.elliot.rickandmortyapp.databinding.CardCharacterBinding
import com.elliot.rickandmortyapp.models.CharacterView
import com.elliot.rickandmortyapp.views.ActivityVerEpisodios


const val CIRCULAR_LOADING = 1
const val CARD_CHARACTER = 0

class CharacterAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val listCharacters = mutableListOf<CharacterView>()
    private var hasNextCharacters = false
    private var onBottomReachedListener: OnBottomReachedListener? = null




    fun addResults(data: List<CharacterView>, hasNextCharacters: Boolean) {
        listCharacters.addAll(data);
        this.hasNextCharacters = hasNextCharacters
    }

    fun setOnBottomReachedListener(onBottomReachedListener: OnBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener
    }

    override fun getItemViewType(position: Int): Int =
        if (position == listCharacters.size) {
            CIRCULAR_LOADING
        } else {
            CARD_CHARACTER
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        if (viewType == CARD_CHARACTER) {
            val cardDataBinding = CardCharacterBinding.inflate(layoutInflater, parent, false)
            return CharacterViewHolder(cardDataBinding)
        } else {
            val circularLoading = layoutInflater.inflate(R.layout.circular_loading_characters, parent, false)
            return CircularLoadingViewHolder(circularLoading)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            val result = listCharacters[position];
            holder.onBind(result);

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ActivityVerEpisodios::class.java)
                intent.putExtra("url",result.episodeUrl)
                holder.itemView.context.startActivity(intent)
            }


        }else {
                onBottomReachedListener?.onBottomReached()
            }

        }


    override fun getItemCount(): Int {
        val increaseSize = if (hasNextCharacters) 1 else 0
        return listCharacters.size + increaseSize;
    }

    inner class CircularLoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) { }

    inner class CharacterViewHolder(private val cardCharacterBinding: CardCharacterBinding)
        : RecyclerView.ViewHolder(cardCharacterBinding.root) {

        fun onBind(characterView: CharacterView) {
            cardCharacterBinding.character = characterView
            cardCharacterBinding.executePendingBindings()


        }

    }
}