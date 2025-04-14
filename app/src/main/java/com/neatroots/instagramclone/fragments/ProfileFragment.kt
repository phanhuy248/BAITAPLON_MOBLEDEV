package com.neatroots.instagramclone.fragments

import Models.User
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.neatroots.instagramclone.SignUpActivity
import com.neatroots.instagramclone.adapters.ViewPagerAdapter
import com.neatroots.instagramclone.databinding.FragmentProfileBinding
import com.neatroots.instagramclone.utils.USER_NODE
import com.squareup.picasso.Picasso
import androidx.viewpager.widget.ViewPager

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var myPostFragment: MyPostFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Nút sửa hồ sơ
        binding.editProfile.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        // Khởi tạo Fragment
        myPostFragment = MyPostFragment()

        // Setup ViewPager
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragment(myPostFragment, "My Post")
        viewPagerAdapter.addFragment(MyReelsFragment(), "My Reels")
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        // Reload post mỗi khi chọn lại tab 0
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    myPostFragment.reloadPosts()
                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Load thông tin người dùng
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                val user: User = it.toObject<User>()!!
                binding.name.text = user.name
                binding.bio.text = user.email
                if (!user.image.isNullOrEmpty()) {
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }
}
