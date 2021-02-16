package com.example.android.roomwordsample.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.network.apiMars.MarsProperty

class MarsDetailViewModel(
    @Suppress("UNUSED_PARAMETER") marsProperty: MarsProperty,
    app: Application
) : AndroidViewModel(app) {

    // MutableLiveData to hold the MarsProperty itself
    private val _selectedProperty = MutableLiveData<MarsProperty>()

    // Expose an immutable public LiveData property
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = marsProperty
    }

    val displayPropertyPrice = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            when (it.isRental) {
                true -> R.string.display_price_monthly_rental
                false -> R.string.display_price
            }, it.price
        )
    }

    val displayPropertyType = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            R.string.display_type,
            app.applicationContext.getString(
                when (it.isRental) {
                    true -> R.string.type_rent
                    false -> R.string.type_sale
                }
            )
        )
    }

}

class MarsDetailViewModelFactory(
    private val marsProperty: MarsProperty,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarsDetailViewModel::class.java)) {
            return MarsDetailViewModel(marsProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
