package com.paxet.evoapp.lesson7.ui.fragments.moviedetails

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paxet.evoapp.lesson7.R
import com.paxet.evoapp.lesson7.data.Actor

class ActorViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val avatar : ImageView = itemView.findViewById(R.id.avatar)
    private val actor_name : TextView = itemView.findViewById(R.id.actor_name)
    private val view = view

    fun bind(actor : Actor) {
        Glide.with(view).load(actor.picture).into(avatar)
        actor_name.text = actor.name
    }
}