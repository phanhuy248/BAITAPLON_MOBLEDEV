package com.neatroots.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neatroots.instagramclone.Post.PostActivity
import com.neatroots.instagramclone.Post.ReelsActivity
import com.neatroots.instagramclone.R
import com.neatroots.instagramclone.databinding.FragmentAddBinding


class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAddBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener {
        activity?.startActivity(Intent(requireContext(), PostActivity::class.java))
            activity?.finish()
        }
        binding.Reel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), ReelsActivity::class.java))
        }
        return binding.root
    }

    companion object {

    }
}