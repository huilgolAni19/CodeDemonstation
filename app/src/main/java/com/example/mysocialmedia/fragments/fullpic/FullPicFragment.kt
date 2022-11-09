package com.example.mysocialmedia.fragments.fullpic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.FragmentFullPicBinding
import com.example.mysocialmedia.model.PhotosItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.json.JSONObject

class FullPicFragment : Fragment() {

    private lateinit var viewModel: FullPicViewModel
    private lateinit var binding: FragmentFullPicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_full_pic, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle = requireArguments()
        val gson = Gson()

        val photosItem: PhotosItem = gson.fromJson(bundle.getString("PhotoItem"), PhotosItem::class.java)

        (requireActivity() as AppCompatActivity).supportActionBar!!.title = "Photo ${photosItem.id}"

        Picasso.get().load(photosItem.url).apply {
            placeholder(R.drawable.ic_placeholder)
            into(binding.fullPhotoView)
        }
    }
}