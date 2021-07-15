package com.example.quran.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import com.example.quran.R
import com.squareup.picasso.Picasso


class VersetImageFragment(val page:Int) : Fragment(R.layout.verset_image_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = "https://quran-images-api.herokuapp.com/show/page/"+page.toString()
        val img = view.findViewById<ImageView>(R.id.image_verset)
        Picasso.get().load(url).into(img)


    }
}