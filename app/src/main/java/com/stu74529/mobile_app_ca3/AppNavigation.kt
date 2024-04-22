package com.stu74529.mobile_app_ca3

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(auth: FirebaseAuth) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LoginScreen.route,
    ) {
        composable(route = Routes.LoginScreen.route)
        {
            LoginScreen(navController = navController, auth = auth)
        }

        composable(route = Routes.HomeScreen.route)
        {
            HomeScreen(navController = navController)
        }

        composable(route = Routes.RegistrationScreen.route)
        {
            RegistrationScreen(navController = navController, auth=auth)
        }

        composable(route = Routes.CategoryScreen.route + "/{categoryName}",
            arguments = listOf(
                navArgument("categoryName"){
                    type = NavType.StringType
                    defaultValue = ""
                }
            )){ entry ->
            entry.arguments?.let { CategoryScreen(categoryName = "" + it.getString("categoryName"),navController = navController, auth=auth) }
        }

    }
}