package com.stu74529.mobile_app_ca3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProductScreen(navController : NavController, auth: FirebaseAuth, productId : String) {


    Column {
        Text(text = "Go back", modifier = Modifier.clickable {
        navController.navigate(Routes.HomeScreen.route)
    })
        ProductDisplay(productId = productId)

    }

}


interface FakeStoreApiServiceProduct {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): Product
}


private val retrofitProduct = Retrofit.Builder()
    .baseUrl("https://fakestoreapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

suspend fun getProductDetails(productId: String): Product {
    val service = retrofitProduct.create(FakeStoreApiServiceProduct::class.java)
    return service.getProductById(productId)
}

val quantity = mutableStateOf(0)

@Composable
fun ProductDisplay(productId: String) {
    val product = remember { 
        mutableStateOf<Product?>(null)
    }

    LaunchedEffect(productId) {
        product.value = getProductDetails(productId)
    }
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {


        product.value?.let { product ->
            // Display product details
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                AsyncImage(
                    modifier = Modifier
                        .padding(50.dp, 0.dp),

                    model = product.image,
                    contentDescription = "Translated description of what the image contains"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = product.title)
                Text(text = "Price: $${product.price}")
                Text(text = product.description)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp)
                        .background(Color.Black),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    //quantity.value = cart.content[productId]
                    Text("quantity : " + quantity.value,
                        color = Color.White)
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center
                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .height(50.dp)
                                    .clickable(onClick = {
                                        cart.content.put(productId, 1)
                                        quantity.value += 1
                                    }),
                                model = "https://img.icons8.com/ios/50/FFFFFF/plus--v1.png",
                                contentDescription = "Translated description of what the image contains"
                            )
                            AsyncImage(
                                modifier = Modifier
                                    .height(50.dp),
                                model = "https://img.icons8.com/ios-filled/50/FFFFFF/shopping-cart.png",
                                contentDescription = "Translated description of what the image contains"
                            )
                            AsyncImage(
                                modifier = Modifier
                                    .height(50.dp)
                                    .clickable(onClick = {

                                    }),
                                model = "https://img.icons8.com/ios/50/FFFFFF/minus.png",
                                contentDescription = "Translated description of what the image contains"
                            )
                        }
                    }

                }
            }
        }

    }
}
