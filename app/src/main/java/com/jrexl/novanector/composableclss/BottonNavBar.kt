package com.jrexl.novanector.composableclss


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun CustomBottomBar(
    navController: NavController,
    onCenterClick: () -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Product,
        BottomNavItem.QRScan,
        BottomNavItem.Withdrawal,
        BottomNavItem.Profile
    )

    Box {
        BottomAppBar(
            cutoutShape = CircleShape,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.navigationBarsPadding()

        ) {
            items.forEachIndexed { index, item ->

                BottomNavigationItem(
                    icon = {  Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    ) },
                    selected = currentRoute == item.route,
                    label = { Text(item.label) },
                    alwaysShowLabel = true,
                    onClick = {
                        val route = if (item is BottomNavItem.Product) "product/All" else item.route
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    ,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
            }
        }

    }
}

