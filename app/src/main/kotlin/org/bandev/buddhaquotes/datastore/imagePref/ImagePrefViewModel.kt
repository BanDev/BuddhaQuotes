package org.bandev.buddhaquotes.datastore.imagePref

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ImagePrefViewModel(context: Context) : ViewModel() {

    private val repository = ImagePrefRepository(context.imagePrefStore)

    val imagePref = repository.settings.asLiveData()

    fun setImage(image: Int) {
        viewModelScope.launch {
            repository.setImage(image)
        }
    }
}
