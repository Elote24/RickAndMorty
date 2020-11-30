package com.elliot.rickandmortyapp.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elliot.rickandmortyapp.OnBottomReachedListener
import com.elliot.rickandmortyapp.R
import com.elliot.rickandmortyapp.adapters.CharacterAdapter
import com.elliot.rickandmortyapp.models.CharacterView
import com.elliot.rickandmortyapp.viewmodels.MainActivityViewModel


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("statusColor")
fun changeColorFromStatus(textView: TextView, status: String) {
    when (status) {
        "Alive" -> {
            textView.setTextColor(Color.GREEN)
        }
        "Dead" -> {
            textView.setTextColor(Color.RED)
        }
        else -> {
            textView.setTextColor(Color.GRAY)
        }
    }
}

class MainActivity : AppCompatActivity() {
    val mainActivityViewModel : MainActivityViewModel by viewModels()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewData = findViewById<RecyclerView>(R.id.recyclerViewData)
        val characterAdapter = CharacterAdapter()

        recyclerViewData.layoutManager = LinearLayoutManager(this)
        recyclerViewData.adapter = characterAdapter


        mainActivityViewModel.rickAndMortyListLiveData.observe(this,
            Observer<List<CharacterView>> {
                characterAdapter.addResults(it, mainActivityViewModel.hasNextCharacters)
                characterAdapter.notifyDataSetChanged()
            })

        characterAdapter.setOnBottomReachedListener(object: OnBottomReachedListener {
            override fun onBottomReached() {
                mainActivityViewModel.getCharacters()
            }
        })

        mainActivityViewModel.getCharacters()


    }
}