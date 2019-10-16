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
import com.fernandez.rico.jetpatck.ui.adapter.PostRecyclerAdapter
import com.fernandez.rico.jetpatck.ui.main.PostActivityViewModel
import com.fernandez.rico.jetpatck.ui.main.PostDataScreenState
import kotlinx.android.synthetic.main.fragment_post.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostFragment: Fragment()
{

    private val mViewModel: PostActivityViewModel by viewModel()
    private lateinit var mAdapter: PostRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_post,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUI()

        mViewModel.postScreenState.observe(
            viewLifecycleOwner,
            Observer {
                renderScreen(it)
            }
        )

    }

    private fun configureUI()
    {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        rcvPost.addItemDecoration(decoration)

        mAdapter = PostRecyclerAdapter()

        rcvPost.adapter = mAdapter

    }

    private fun renderScreen(screenState: ScreenState<PostDataScreenState>)
    {
        when(screenState)
        {
            is ScreenState.Loading -> showLoader()
            is ScreenState.Render -> updateUI(screenState.screenState)
        }
    }

    private fun updateUI(screenState: PostDataScreenState)
    {
        hideLoader()

        when(screenState)
        {
            is PostDataScreenState.PostScreen -> {
                mAdapter.submitList(screenState.data)
            }

            is PostDataScreenState.PostDataError -> {
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