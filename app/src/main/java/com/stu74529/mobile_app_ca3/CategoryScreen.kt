package com.stu74529.mobile_app_ca3

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import retrofit2.http.GET
import retrofit2.http.Path
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.itemsIndexed
import coil.compose.AsyncImage

@Composable
fun CategoryScreen(navController : NavController, auth: FirebaseAuth, categoryName : String) {

    Text(text = "Go back", modifier = Modifier.clickable {
        navController.navigate(Routes.HomeScreen.route)
    })
    ProductsByCategoryScreen(categoryName)
}

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)

interface FakeStoreApiServiceProducts {
    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): List<Product>
}

class ProductsByCategoryViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val products = mutableStateOf<List<Product>>(emptyList())

    suspend fun fetchProductsByCategory(categoryName: String): List<Product> {
        return try {
            val service = retrofit.create(FakeStoreApiServiceProducts::class.java)
            val fetchedProducts = service.getProductsByCategory(categoryName)
            products.value = fetchedProducts // Update state
            fetchedProducts
        } catch (e: Exception) {
            // Handle error
            emptyList()
        }
    }
}


@Composable
fun ProductsByCategoryScreen(categoryName: String, viewModel: ProductsByCategoryViewModel = viewModel()) {
    val productsState = remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(categoryName) {
        val products = viewModel.fetchProductsByCategory(categoryName)
        productsState.value = products
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Products in $categoryName:")
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            itemsIndexed(productsState.value) { index, product ->
                ProductItem(product = product)
                Divider()
            }
        }
    }
}





@Composable
fun ProductItem(product: Product) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(modifier = Modifier
            .height(50.dp)
            .padding(50.dp,0.dp),

            model = product.image,
            contentDescription = "Translated description of what the image contains"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = product.title, fontWeight = FontWeight.Bold)
            Text(text = "$${product.price}")
            Text(text = product.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

