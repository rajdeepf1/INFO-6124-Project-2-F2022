package com.rajdeepsingh.birthdayreminderapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.project02.R
import com.example.project02.models.NearByPlacesDataModel

class NearByPlacesAdapter(
    private val context: Context,
    private val arrayList: MutableList<NearByPlacesDataModel>,
) :
    RecyclerView.Adapter<NearByPlacesAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewPlaceName: TextView
        var textViewAddress: TextView
        var cardView: CardView

        init {
            // Define click listener for the ViewHolder's View.
            textViewPlaceName = view.findViewById(R.id.textViewPlaceName)
            textViewAddress = view.findViewById(R.id.textViewAddress)
            cardView = view.findViewById(R.id.cardView)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val itemPosition = arrayList[position]
        viewHolder.textViewPlaceName.setText(itemPosition.placeName)
        viewHolder.textViewAddress.setText(itemPosition.address)

        viewHolder.cardView.animation =
            AnimationUtils.loadAnimation(viewHolder.itemView.context, R.anim.anim)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return arrayList.size
    }


}