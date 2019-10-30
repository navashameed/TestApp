package com.test.app.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.test.app.R
import com.squareup.picasso.Picasso


class AlbumDetailFragment : Fragment() {

    lateinit var imageView: ImageView
    lateinit var titleText: TextView
    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.album_detail_fragment, container, false)
        imageView = view.findViewById(R.id.image)
        titleText = view.findViewById(R.id.title)

        val title = arguments?.getString("title")
        val url = arguments?.getString("url")

        titleText.text = title
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder_big)
            .into(imageView)

        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        with((activity as AppCompatActivity).supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            this?.title = getString(R.string.album_detail_title)
        }
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
