package com.test.app.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.app.R
import com.test.app.databinding.AlbumListFragmentBinding
import com.test.app.model.Album
import com.test.app.view.adapter.AlbumListAdapter
import com.test.app.viewmodel.UserViewModel
import com.test.app.util.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AlbumListFragment : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var toolbar: Toolbar

    lateinit var albumListAdapter: AlbumListAdapter

    var navController: NavController? = null

    lateinit var albumListFragmentBinding: AlbumListFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: UserViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userId = arguments?.getString("userId")

        return if (::albumListFragmentBinding.isInitialized) {
            albumListFragmentBinding.root
        } else {
            albumListFragmentBinding =
                DataBindingUtil.inflate(inflater, R.layout.album_list_fragment, container, false)
            progressBar = albumListFragmentBinding.progressBar
            recyclerView = albumListFragmentBinding.albumRecyclerview

            albumListAdapter = AlbumListAdapter()

            with(recyclerView) {
                adapter = albumListAdapter
                layoutManager = LinearLayoutManager(activity)
            }

            toolbar = albumListFragmentBinding.toolbar as Toolbar

            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            with((activity as AppCompatActivity).supportActionBar) {
                this?.setDisplayHomeAsUpEnabled(true)
                this?.title = getString(R.string.album_list_title)
            }
            toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

            viewModel = ViewModelProvider(
                activity as AppCompatActivity,
                viewModelFactory
            ).get(UserViewModel::class.java)

            initViewModelObservers()
            viewModel.fetchAlbumList(userId)

            return albumListFragmentBinding.root

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }



    fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun initViewModelObservers() {
        viewModel.loadingProgressDialogObservable.observe(this, Observer {
            showProgressBar(it)
        })

        viewModel.errorObservable.observe(this, Observer {
            Toast.makeText(activity, "Error in loading", Toast.LENGTH_LONG).show()
        })

        viewModel.albumListObservable.observe(this, Observer {
            albumListAdapter.setItemsAndListener(it, object : AlbumListAdapter.OnItemClickListener {
                override fun onItemClick(item: Album) {

                    val bundle = bundleOf("url" to item.url, "title" to item.title)
                    navController?.navigate(
                        R.id.action_albumListFragment_to_albumDetailFragment,
                        bundle
                    )
                }
            })
        })
    }

}
