package org.bandev.buddhaquotes.datastore.imagePref

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagePrefViewModel @Inject constructor(private val repository: ImagePrefRepository) : ViewModel() {
    val imagePref = repository.settings.asLiveData()

    fun setImage(image: Int) {
        viewModelScope.launch {
            repository.setImage(image)
        }
    }
}
