package com.neatroots.instagramclone.fragments

import Models.Post
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.neatroots.instagramclone.adapters.MyPostRvAdapter
import com.neatroots.instagramclone.databinding.FragmentMyPostBinding

class MyPostFragment : Fragment() {

    private lateinit var binding: FragmentMyPostBinding
    private lateinit var adapter: MyPostRvAdapter
    private val postList = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPostBinding.inflate(inflater, container, false)

        adapter = MyPostRvAdapter(requireContext(), postList)
        binding.rv.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (this::adapter.isInitialized && isAdded) {
            loadMyPosts()
        }
    }


    // Gọi từ ProfileFragment hoặc onResume()
    fun reloadPosts() {
        if (this::adapter.isInitialized && isAdded) {
            loadMyPosts()
        }
    }


    private fun loadMyPosts() {
        val userId = Firebase.auth.currentUser?.uid ?: return

        Firebase.firestore.collection(userId).get()
            .addOnSuccessListener { result ->
                val tempList = ArrayList<Post>()
                for (document in result.documents) {
                    val post = document.toObject<Post>()
                    post?.let {
                        it.postId = document.id
                        tempList.add(it)
                    }
                }
                postList.clear()
                postList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                // TODO: xử lý lỗi nếu cần
            }
    }
}
