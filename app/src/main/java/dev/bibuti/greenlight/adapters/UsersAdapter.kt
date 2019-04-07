package dev.bibuti.greenlight.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.bibuti.greenlight.R
import dev.bibuti.greenlight.models.Users
import kotlinx.android.synthetic.main.single_layout.view.*

class UsersAdapter(private var usersList: List<Users>, private var onUserItemClickListener: OnUserItemClickListener) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_layout, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val singleUser = usersList[position]

        holder.itemView.userFullNameTV.text = singleUser.name
        holder.itemView.usernameTV.text = "Username - @${singleUser.username}"
        holder.itemView.userEmailTV.text = "E: ${singleUser.email}"
        holder.itemView.userPhoneTV.text = "P: ${singleUser.phone}"
        holder.itemView.userWebsiteTV.text = "W: ${singleUser.website}"

        holder.itemView.userAddressTV.text = "Address: ${singleUser.address.suite}, Street - ${singleUser.address.street},\nCity - ${singleUser.address.city}, Zipcode - ${singleUser.address.zipcode}.\nGeoLocation - ( ${singleUser.address.geo.lat}, ${singleUser.address.geo.lng} )"

        holder.itemView.userCompanyTV.text = "Company Name: ${singleUser.company.companyName}\nCompany Phrase: ${singleUser.company.catchPhrase}"

        holder.itemView.userDeleteBTN.setOnClickListener {
            onUserItemClickListener.onDeleteButtonClicked(singleUser)
        }

    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    fun updateList(updatedList: List<Users>) {
        usersList = updatedList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnUserItemClickListener {
        fun onDeleteButtonClicked(users: Users)
    }
}
