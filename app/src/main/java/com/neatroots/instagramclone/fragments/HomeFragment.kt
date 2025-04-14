package com.neatroots.instagramclone.fragments

import Models.Post
import Models.User
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.neatroots.instagramclone.R
import com.neatroots.instagramclone.adapters.FollowAdapter
import com.neatroots.instagramclone.adapters.PostAdapter
import com.neatroots.instagramclone.databinding.FragmentHomeBinding
import com.neatroots.instagramclone.utils.FOLLOW
import com.neatroots.instagramclone.utils.POST
import com.neatroots.instagramclone.utils.USER_NODE
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var postList = ArrayList<Post>()
    private lateinit var adapter: PostAdapter
    private var followList = ArrayList<User>()
    private lateinit var followAdapter: FollowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Setup toolbar
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar3)

        // Setup Post RecyclerView
        adapter = PostAdapter(requireContext(), postList)
        binding.postRv.layoutManager = LinearLayoutManager(requireContext())
        binding.postRv.adapter = adapter

        // Setup Follow RecyclerView
        followAdapter = FollowAdapter(requireContext(), followList)
        binding.followRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.followRv.adapter = followAdapter

        // Load current user avatar
        Firebase.firestore.collection(USER_NODE)
            .document(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                if (user != null && !user.image.isNullOrEmpty()) {
                    Picasso.get()
                        .load(user.image)
                        .placeholder(R.drawable.user_icon)
                        .into(binding.imageView3)
                }
            }

        // Load follow list
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
            .get()
            .addOnSuccessListener {
                val tempList = ArrayList<User>()
                followList.clear()
                for (doc in it.documents) {
                    val user = doc.toObject<User>()
                    if (user != null) tempList.add(user)
                }
                followList.addAll(tempList)
                followAdapter.notifyDataSetChanged()
            }

        // Load posts
        Firebase.firestore.collection(POST)
            .get()
            .addOnSuccessListener {
                val tempList = ArrayList<Post>()
                postList.clear()
                for (doc in it.documents) {
                    val post = doc.toObject<Post>()
                    if (post != null) tempList.add(post)
                }
                postList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
