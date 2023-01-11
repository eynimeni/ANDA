package at.fh.anda_contacts.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import at.fh.anda_contacts.Contact
import at.fh.anda_contacts.R

class ContactViewHolder(val listItemContactRootView: View): RecyclerView.ViewHolder(listItemContactRootView) {
    val contactNameTextView: TextView = listItemContactRootView.findViewById(R.id.tvListItemContactName)
}

class ContactsAdapter(var contacts: ArrayList<Contact>): RecyclerView.Adapter<ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItemContactRootView =
            layoutInflater.inflate(R.layout.list_item_contact, parent, false)
        return ContactViewHolder(listItemContactRootView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.contactNameTextView.text = contact.name
        holder.listItemContactRootView.setOnClickListener{
            val navHostFragment = holder.listItemContactRootView.findNavController()
            navHostFragment.navigate(ListFragmentDirections.actionOverviewFragmentToDetailFragment(contact))
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun updateContacts(contacts: ArrayList<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}