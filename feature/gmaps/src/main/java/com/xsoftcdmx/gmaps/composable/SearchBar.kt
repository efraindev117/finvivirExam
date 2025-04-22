package com.xsoftcdmx.gmaps.composable

import android.R
import android.content.Context
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest

@Composable
fun GMapsSearchBar(
    modifier: Modifier = Modifier,
    onPlaceSelected: (String) -> Unit
) {
    AndroidView(
        factory = { context ->
            AutoCompleteTextView(context).apply {
                hint = "Buscar un lugar "
                threshold = 1
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                val autocompleteAdapter =
                    ArrayAdapter<String>(context, R.layout.simple_dropdown_item_1line)
                val placesClient = Places.createClient(context)
                val autocompleteSessionToken = AutocompleteSessionToken.newInstance()

                addTextChangedListener { editable ->
                    val query = editable?.toString() ?: ""
                    if (query.isNotEmpty()) {
                        val request = FindAutocompletePredictionsRequest.builder()
                            .setSessionToken(autocompleteSessionToken)
                            .setQuery(query)
                            .build()

                        placesClient.findAutocompletePredictions(request)
                            .addOnSuccessListener { response ->
                                autocompleteAdapter.clear()
                                response.autocompletePredictions.forEach { prediction ->
                                    autocompleteAdapter.add(prediction.getFullText(null).toString())
                                }
                                autocompleteAdapter.notifyDataSetChanged()
                            }
                    }
                }
                setAdapter(autocompleteAdapter)
                setOnItemClickListener { _, _, pos, _ ->
                    val place = autocompleteAdapter.getItem(pos) ?: return@setOnItemClickListener
                    onPlaceSelected(place)
                    clearFocus()
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)
                }
                setOnEditorActionListener { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE
                    ) {
                        val text = text.toString().trim()
                        if (text.isNotEmpty()) {
                            onPlaceSelected(text)
                        }
                        clearFocus()
                        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                                as InputMethodManager
                        imm.hideSoftInputFromWindow(windowToken, 0)
                        true
                    } else {
                        false
                    }
                }
            }
        },
        modifier = modifier // Apply the passed modifier to the AutoCompleteTextView
            .fillMaxWidth() // Make sure the composable fills the maximum width available
            .padding(16.dp) //  Add padding to the search bar
    )
}