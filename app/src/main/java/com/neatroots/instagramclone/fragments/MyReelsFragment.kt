package com.neatroots.instagramclone.fragments

import Models.Post
import Models.Reel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.neatroots.instagramclone.R
import com.neatroots.instagramclone.adapters.MyPostRvAdapter
import com.neatroots.instagramclone.adapters.MyReelAdapter
import com.neatroots.instagramclone.databinding.ActivityReelBinding
import com.neatroots.instagramclone.databinding.FragmentMyReelsBinding
import com.neatroots.instagramclone.utils.REEL


class MyReelsFragment : Fragment() {
    private lateinit var binding: FragmentMyReelsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMyReelsBinding.inflate(inflater, container, false)
        var reelList=ArrayList<Reel>()
        var adapter= MyReelAdapter(requireContext(), reelList)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).get().addOnSuccessListener {
            var temList= arrayListOf<Reel>()
            for (i in it.documents){
                var reel: Reel = i.toObject<Reel>()!!
                temList.add(reel)
            }
            reelList.addAll(temList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}