package com.jrexl.novanector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jrexl.novanector.composableclss.BottomNavItem
import com.jrexl.novanector.composableclss.CustomBottomBar
import com.jrexl.novanector.screen.HomeContent
import com.jrexl.novanector.screen.ProductScreen
import com.jrexl.novanector.screen.ProfileScreen
import com.jrexl.novanector.screen.QRScanScreen
import com.jrexl.novanector.screen.WithdrawalScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {

            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.White, darkIcons = true)
    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            CustomBottomBar(navController) {
                navController.navigate(BottomNavItem.QRScan.route)
            }
        },     contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0)

    ) { innerPadding ->
        Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
            NavHost(navController, startDestination = BottomNavItem.Home.route) {
                composable(BottomNavItem.Home.route) { HomeContent(
                    navController = navController
                ) }
                composable(
                    route = "product/{categoryName}",
                    arguments = listOf(navArgument("categoryName") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = ""
                    })
                ) { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                    ProductScreen(categoryName = categoryName)
                }
                composable(BottomNavItem.QRScan.route) { QRScanScreen() }
                composable(BottomNavItem.Withdrawal.route) { WithdrawalScreen() }
                composable(BottomNavItem.Profile.route) { ProfileScreen() }
            }
        }
    }
}










