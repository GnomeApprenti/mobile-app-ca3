package com.stu74529.mobile_app_ca3

sealed class Routes(val route: String) {
    object LoginScreen : Routes("login_screen")
    object HomeScreen : Routes("home_screen")
    object RegistrationScreen : Routes("registration_screen")
    object CategoryScreen : Routes("category_screen")
}