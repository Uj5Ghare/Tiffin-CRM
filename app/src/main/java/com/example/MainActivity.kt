package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.viewmodel.TiffinViewModel
import com.example.ui.screens.CustomerDetailScreen
import com.example.ui.screens.CustomersScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DeliveriesScreen
import com.example.ui.screens.MealPlansScreen
import com.example.ui.screens.PaymentsScreen
import com.example.ui.theme.TiffinTheme
import com.example.ui.util.AppLanguage
import com.example.ui.util.tr

class MainActivity : ComponentActivity() {

    private val viewModel: TiffinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            val currentLanguage by viewModel.currentLanguage.collectAsState()

            TiffinTheme(darkTheme = isDarkMode) {
                TiffinApp(
                    viewModel = viewModel,
                    isDarkMode = isDarkMode,
                    currentLanguage = currentLanguage
                )
            }
        }
    }
}

enum class Screen(val titleKey: String, val navKey: String, val icon: ImageVector) {
    DASHBOARD("title_crm", "nav_dashboard", Icons.Default.Dashboard),
    DELIVERIES("title_dispatch", "nav_deliveries", Icons.Default.LocalShipping),
    CUSTOMERS("title_directory", "nav_customers", Icons.Default.People),
    PAYMENTS("title_ledger", "nav_payments", Icons.Default.Payments),
    MEAL_PLANS("title_plans", "nav_plans", Icons.Default.RestaurantMenu)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiffinApp(
    viewModel: TiffinViewModel,
    isDarkMode: Boolean,
    currentLanguage: AppLanguage
) {
    var currentScreen by remember { mutableStateOf(Screen.DASHBOARD) }
    var viewingCustomerId by remember { mutableStateOf<Long?>(null) }
    var showLangMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.RestaurantMenu,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (viewingCustomerId != null) {
                                "title_customer_details".tr(currentLanguage)
                            } else {
                                currentScreen.titleKey.tr(currentLanguage)
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                },
                actions = {
                    // Language Translator Switcher Button
                    Box {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { showLangMenu = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("btn_language_selector")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Language,
                                    contentDescription = "Translate Language",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${currentLanguage.flag} ${currentLanguage.nativeName}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showLangMenu,
                            onDismissRequest = { showLangMenu = false }
                        ) {
                            AppLanguage.values().forEach { lang ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${lang.flag}  ${lang.nativeName}",
                                                fontWeight = if (lang == currentLanguage) FontWeight.Bold else FontWeight.Normal
                                            )
                                            if (lang == currentLanguage) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Selected",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        viewModel.setLanguage(lang)
                                        showLangMenu = false
                                    },
                                    modifier = Modifier.testTag("lang_option_${lang.code}")
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // One-Click Light/Dark Mode Toggle
                    IconButton(
                        onClick = { viewModel.toggleDarkMode() },
                        modifier = Modifier.testTag("btn_toggle_dark_mode")
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode",
                            tint = if (isDarkMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            if (viewingCustomerId == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    Screen.values().forEach { screen ->
                        val localizedLabel = screen.navKey.tr(currentLanguage)
                        NavigationBarItem(
                            selected = currentScreen == screen,
                            onClick = { currentScreen = screen },
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = localizedLabel
                                )
                            },
                            label = { Text(localizedLabel) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.testTag("nav_${screen.name.lowercase()}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (viewingCustomerId != null) {
                CustomerDetailScreen(
                    viewModel = viewModel,
                    language = currentLanguage,
                    onBackClick = {
                        viewingCustomerId = null
                        viewModel.selectCustomer(null)
                    }
                )
            } else {
                when (currentScreen) {
                    Screen.DASHBOARD -> DashboardScreen(
                        viewModel = viewModel,
                        language = currentLanguage,
                        onNavigateToDeliveries = { currentScreen = Screen.DELIVERIES },
                        onNavigateToCustomers = { currentScreen = Screen.CUSTOMERS },
                        onNavigateToPayments = { currentScreen = Screen.PAYMENTS },
                        onCustomerClick = { customerId ->
                            viewModel.selectCustomer(customerId)
                            viewingCustomerId = customerId
                        }
                    )

                    Screen.DELIVERIES -> DeliveriesScreen(
                        viewModel = viewModel,
                        language = currentLanguage,
                        onCustomerClick = { customerId ->
                            viewModel.selectCustomer(customerId)
                            viewingCustomerId = customerId
                        }
                    )

                    Screen.CUSTOMERS -> CustomersScreen(
                        viewModel = viewModel,
                        language = currentLanguage,
                        onCustomerClick = { customerId ->
                            viewModel.selectCustomer(customerId)
                            viewingCustomerId = customerId
                        }
                    )

                    Screen.PAYMENTS -> PaymentsScreen(
                        viewModel = viewModel,
                        language = currentLanguage,
                        onCustomerClick = { customerId ->
                            viewModel.selectCustomer(customerId)
                            viewingCustomerId = customerId
                        }
                    )

                    Screen.MEAL_PLANS -> MealPlansScreen(
                        viewModel = viewModel,
                        language = currentLanguage
                    )
                }
            }
        }
    }
}
