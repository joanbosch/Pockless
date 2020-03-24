package com.pes.pockles.view.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.pes.pockles.R
import com.pes.pockles.databinding.FragmentUserProfileBinding
import com.pes.pockles.view.ui.newpock.NewPockActivity
import com.pes.pockles.view.ui.pockshistory.PocksHistoryActivity

/**
 * A simple [Fragment] subclass.
 */
class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.setContentView(activity!!, R.layout.fragment_user_profile)

        //Add Control for btnPocksHistory
        binding.btnPocksHistory.setOnClickListener {
            startActivity(Intent(context, NewPockActivity::class.java))
        }
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }


}
