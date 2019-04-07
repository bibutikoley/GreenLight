package dev.bibuti.greenlight

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bibuti.greenlight.adapters.UsersAdapter
import dev.bibuti.greenlight.database.UserViewModel
import dev.bibuti.greenlight.models.Users
import dev.bibuti.greenlight.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_error_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(),
        UsersAdapter.OnUserItemClickListener {

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProviders.of(this@MainActivity).get(UserViewModel::class.java)

        val usersAdapter = UsersAdapter(mutableListOf(), this@MainActivity)

        usersRV.setHasFixedSize(true)
        usersRV.layoutManager = LinearLayoutManager(this@MainActivity)
        usersRV.adapter = usersAdapter

        userViewModel.getAllUser().observe(this@MainActivity, Observer {

            usersAdapter.updateList(it)

            if (it.isNullOrEmpty()) {
                error_empty_layout.visibility = View.VISIBLE
                usersRV.visibility = View.INVISIBLE

                error_image.setImageDrawable(resources.getDrawable(R.drawable.ic_box, theme))
                error_text.text = "You don't have any Users.."
                error_btn.text = "Refresh from Server"
                error_btn.setOnClickListener {
                    makeNetworkRequest()
                }
            } else {
                usersRV.visibility = View.VISIBLE
                error_empty_layout.visibility = View.INVISIBLE
            }

        })

        //makeNetworkRequest()

    }

    private fun makeNetworkRequest() {

        if (isNetworkAvailable()) {
            RetrofitClient.endpoints.users.enqueue(object : Callback<List<Users>?> {
                override fun onFailure(call: Call<List<Users>?>, t: Throwable) {
                    //Handle Local Errors..
                }

                override fun onResponse(call: Call<List<Users>?>, response: Response<List<Users>?>) {
                    if (response.isSuccessful) {
                        if (response.body()?.isNotEmpty()!!) {
                            userViewModel.insertUsers(response.body()!!)
                        } else {
                            //List was empty..
                        }
                    } else {
                        //Handle API Error..
                    }
                }
            })
        } else {
            //Show No Network ...
            error_empty_layout.visibility = View.VISIBLE
            usersRV.visibility = View.INVISIBLE

            error_image.setImageDrawable(resources.getDrawable(R.drawable.no_internet, theme))
            error_text.text = "It seems you don't have a working Internet Connection.."
            error_btn.text = "Retry"
            error_btn.setOnClickListener {
                makeNetworkRequest()
            }
        }

    }

    override fun onDeleteButtonClicked(users: Users) {
//        if (isNetworkAvailable()) {
//            //Show you cannot delete in online mode/..
//        } else {
//            userViewModel.deleteUser(users)
//        }
        userViewModel.deleteUser(users)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
