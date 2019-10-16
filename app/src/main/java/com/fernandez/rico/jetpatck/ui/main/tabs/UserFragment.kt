package com.fernandez.rico.jetpatck.ui.main.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.fernandez.rico.jetpatck.R
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.core.ScreenState
import com.fernandez.rico.jetpatck.core.extensions.hide
import com.fernandez.rico.jetpatck.core.extensions.show
import com.fernandez.rico.jetpatck.ui.adapter.UserRecyclerAdapter
import com.fernandez.rico.jetpatck.ui.main.UserActivityViewModel
import com.fernandez.rico.jetpatck.ui.main.UserDataScreenState
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment: Fragment()
{
    private val mViewModel: UserActivityViewModel by viewModel()
    private lateinit var mAdapter: UserRecyclerAdapter

    companion object
    {
        fun newInstance()= UserFragment().apply {

            val args = Bundle()

            arguments = args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        configureUI()


        mViewModel.userScreenState.observe(
            this,
            Observer {
                renderScreen(it)
            }
        )

    }

    private fun configureUI()
    {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        rcvUser.addItemDecoration(decoration)

        mAdapter = UserRecyclerAdapter()

        rcvUser.adapter = mAdapter

    }

    private fun renderScreen(screenState: ScreenState<UserDataScreenState>)
    {
        when(screenState)
        {
            is ScreenState.Loading -> showLoader()
            is ScreenState.Render -> updateUI(screenState.screenState)
        }
    }



    private fun updateUI(screenState: UserDataScreenState)
    {
        hideLoader()
        when(screenState)
        {
            is UserDataScreenState.UserScreen -> {
                mAdapter.submitList(screenState.data)
            }

            is UserDataScreenState.UserDataError -> {
                handleErrors(screenState.error)
            }
        }
    }

    private fun handleErrors(failure: Failure)
    {
        when(failure)
        {
            is Failure.ConnectivityError -> {
                Toast.makeText(requireContext(),getString(R.string.connectivity_error), Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
    }

    private fun showLoader(){
        progressBar.show()
    }

    private fun hideLoader(){
        progressBar.hide()
    }

}