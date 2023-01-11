package at.fh.anda_contacts.detail

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import at.fh.anda_contacts.R
import com.google.android.material.textfield.TextInputEditText

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val argumentsFromOverviewScreen: DetailFragmentArgs by navArgs()
    private val viewModel : DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contact = argumentsFromOverviewScreen.contact

        val etContactName = view.findViewById<TextInputEditText>(R.id.etContactName)
        etContactName.setText(contact.name)

        val etContactTelephoneNumber: TextInputEditText =
            view.findViewById(R.id.etContactTelephoneNumber)
        etContactTelephoneNumber.setText(contact.telephoneNumber)

        val etContactAge = view.findViewById<TextInputEditText>(R.id.etContactAge)
        etContactAge.setText(contact.age.toString())

        etContactName.doAfterTextChanged { newName ->
            viewModel.onNameChanged(newName.toString())
        }

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            viewModel.save()
            findNavController().popBackStack()
        }

        val btnDelete = view.findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            viewModel.delete()
            findNavController().popBackStack()
        }
    }
}