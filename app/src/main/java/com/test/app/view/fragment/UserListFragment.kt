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
import com.test.app.databinding.UserListFragmentBinding
import com.test.app.util.ViewModelFactory
import com.test.app.view.adapter.UserlistAdapter
import com.test.app.viewmodel.UserViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class UserListFragment : Fragment() {

    var navController: NavController? = null

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var toolbar: Toolbar

    lateinit var userlistAdapter: UserlistAdapter

    lateinit var viewModel: UserViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var userListFragmentBinding: UserListFragmentBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return if (::userListFragmentBinding.isInitialized) {
            userListFragmentBinding.root
        } else {
            userListFragmentBinding =
                DataBindingUtil.inflate(inflater, R.layout.user_list_fragment, container, false)

            progressBar = userListFragmentBinding.progressBar
            recyclerView = userListFragmentBinding.usersRecyclerview

            val view = userListFragmentBinding.root

            toolbar = view.findViewById(R.id.toolbar) as Toolbar
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.title =
                getString(R.string.user_list_title)
            return view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        userlistAdapter = UserlistAdapter()

        with(recyclerView) {
            adapter = userlistAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel = ViewModelProvider(
            activity as AppCompatActivity,
            viewModelFactory
        ).get(UserViewModel::class.java)

        initViewModelObservers()
        if (viewModel.userListObservable.value == null) {
            viewModel.fetchUserList()
        }

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

        viewModel.userListObservable.observe(this, Observer {
            userlistAdapter.setItemsAndListener(it, object : UserlistAdapter.OnItemClickListener {
                override fun onItemClick(userId: Int?) {
                    val bundle = bundleOf("userId" to userId.toString())
                    navController?.navigate(
                        R.id.action_userListFragment_to_albumListFragment,
                        bundle
                    )
                }

            })
        })
    }
}
