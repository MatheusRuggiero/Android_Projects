package br.com.tecnomotor.thanos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.util.Slides

/** Created by Andreo 15/08/22 **/

class IntroSliderAdapter(private val introSlides: List<Slides>)
    : RecyclerView.Adapter<IntroSliderAdapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.slide_item_container,parent,false)
        )
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    inner class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)
        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)


        fun bind(introSlides: Slides){

            textTitle.text = introSlides.titulo
            textDescription.text = introSlides.descricao
            imageIcon.setImageResource(introSlides.icone)

        }
    }

}