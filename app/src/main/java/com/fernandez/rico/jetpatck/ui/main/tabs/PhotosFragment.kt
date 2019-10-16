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
import com.fernandez.rico.jetpatck.ui.adapter.PhotoRecyclerAdapter
import com.fernandez.rico.jetpatck.ui.main.PhotoActivityViewModel
import com.fernandez.rico.jetpatck.ui.main.PhotoDataScreenState
import kotlinx.android.synthetic.main.fragment_photo.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosFragment: Fragment()
{

    private val mViewModel: PhotoActivityViewModel by viewModel()

    private lateinit var mAdapter: PhotoRecyclerAdapter

    companion object
    {
        fun newInstance()= PhotosFragment().apply {

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
    ): View? = inflater.inflate(R.layout.fragment_photo,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUI()


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.photoScreenState.observe(
            viewLifecycleOwner,
            Observer {
                renderScreen(it)
            }
        )

    }

    private fun configureUI()
    {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        rcvPhotos.addItemDecoration(decoration)

        mAdapter = PhotoRecyclerAdapter()

        rcvPhotos.adapter = mAdapter

    }

    private fun renderScreen(screenState: ScreenState<PhotoDataScreenState>)
    {
        when(screenState)
        {
            is ScreenState.Loading -> showLoader()
            is ScreenState.Render -> updateUI(screenState.screenState)
        }
    }

    private fun updateUI(screenState: PhotoDataScreenState)
    {
        hideLoader()
        when(screenState)
        {
            is PhotoDataScreenState.PhotoScreen -> {
                mAdapter.submitList(screenState.data)
            }

            is PhotoDataScreenState.PhotoDataError -> {
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

