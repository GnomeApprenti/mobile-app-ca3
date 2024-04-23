package com.stu74529.mobile_app_ca3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

@Composable
fun HomeScreen(navController : NavController) {
    CategoriesScreen(navController)
}

val cart = Cart()

interface FakeStoreApiService {
    @GET("products/categories")
    suspend fun getCategories(): List<String>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://fakestoreapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

class CategoriesViewModel : ViewModel() {
    val categories = mutableStateOf<List<String>>(emptyList())

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val service = retrofit.create(FakeStoreApiService::class.java)
                // Fetch categories from the API
                categories.value = service.getCategories()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

@Composable
fun CategoriesScreen(navController : NavController, viewModel: CategoriesViewModel = viewModel()) {
    val categories = viewModel.categories.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){
        Text(text = "Categories:")
        for(category in categories){
            Text(text = category,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.CategoryScreen.route+"/"+category)
                })
        }
    }
}