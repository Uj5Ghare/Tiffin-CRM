package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.viewmodel.TiffinViewModel
import com.example.ui.components.AddEditCustomerDialog
import com.example.ui.components.AddPauseDialog
import com.example.ui.components.AddSubscriptionDialog
import com.example.ui.components.DietaryBadge
import com.example.ui.components.RecordPaymentDialog
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.NonVegRedContainer
import com.example.ui.theme.StatusDelivered
import com.example.ui.theme.VegGreen
import com.example.ui.theme.VegGreenContainer
import com.example.ui.util.AppLanguage
import com.example.ui.util.tr

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(
    viewModel: TiffinViewModel,
    language: AppLanguage = AppLanguage.ENGLISH,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val customer by viewModel.selectedCustomer.collectAsStateWithLifecycle()
    val subscriptions by viewModel.selectedCustomerSubscriptions.collectAsStateWithLifecycle()
    val payments by viewModel.selectedCustomerPayments.collectAsStateWithLifecycle()
    val pauses by viewModel.selectedCustomerPauses.collectAsStateWithLifecycle()
    val allMealPlans by viewModel.allMealPlans.collectAsStateWithLifecycle()

    var showEditDialog by remember { mutableStateOf(false) }
    var showSubscriptionDialog by remember { mutableStateOf(false) }
    var showPaymentDialog by remember { mutableStateOf(false) }
    var showPauseDialog by remember { mutableStateOf(false) }

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    if (customer == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Customer not found.")
        }
        return
    }

    val currentCustomer = customer!!
    val activeSub = subscriptions.firstOrNull { it.status == "ACTIVE" }
    val pendingDue = if (activeSub != null) (activeSub.price - activeSub.paidAmount).coerceAtLeast(0.0) else 0.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentCustomer.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "edit".tr(language))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Customer Header Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = currentCustomer.phone,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Area",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${currentCustomer.area} • ${currentCustomer.address}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        DietaryBadge(dietaryType = currentCustomer.dietaryPreference, language = language)
                    }

                    if (currentCustomer.deliveryNotes.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${"notes".tr(language)}: ${currentCustomer.deliveryNotes}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Action Buttons Row: WhatsApp, Call, Record Payment, Add Subscription, Pause
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { showPaymentDialog = true },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Payments, contentDescription = "Pay", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("action_record_payment".tr(language), fontSize = 11.sp)
                        }

                        OutlinedButton(
                            onClick = { showSubscriptionDialog = true },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Subscribe", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("add_subscription".tr(language), fontSize = 11.sp)
                        }

                        OutlinedButton(
                            onClick = { showPauseDialog = true },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.PauseCircle, contentDescription = "Pause", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("pause_tiffin".tr(language), fontSize = 11.sp)
                        }

                        IconButton(
                            onClick = {
                                val msg = "Hello ${currentCustomer.name}, regarding your Tiffin service..."
                                viewModel.sendWhatsAppMessage(context, currentCustomer.phone, msg)
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .background(StatusDelivered.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "WhatsApp", tint = StatusDelivered, modifier = Modifier.size(18.dp))
                        }

                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${currentCustomer.phone}"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp))
                        ) {
                            Icon(Icons.Default.Call, contentDescription = "Call", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Active Subscription Progress Card
            if (activeSub != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = activeSub.planName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${activeSub.startDate} to ${activeSub.endDate}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            if (pendingDue > 0) {
                                Surface(shape = RoundedCornerShape(6.dp), color = NonVegRedContainer) {
                                    Text(
                                        text = "${"pending".tr(language)} ₹${pendingDue.toInt()}",
                                        color = NonVegRed,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            } else {
                                Surface(shape = RoundedCornerShape(6.dp), color = VegGreenContainer) {
                                    Text(
                                        text = "Paid",
                                        color = VegGreen,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Progress Bar
                        val progress = if (activeSub.totalMeals > 0) activeSub.consumedMeals.toFloat() / activeSub.totalMeals.toFloat() else 0f
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${activeSub.consumedMeals} / ${activeSub.totalMeals} Meals Served",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${activeSub.totalMeals - activeSub.consumedMeals} Left",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tabs Header
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("${"nav_plans".tr(language)} (${subscriptions.size})") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("${"title_ledger".tr(language)} (${payments.size})") }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    text = { Text("${"pause_schedules".tr(language)} (${pauses.size})") }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tab Content List
            when (selectedTabIndex) {
                0 -> {
                    // Subscriptions List
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 32.dp)
                    ) {
                        items(subscriptions, key = { it.id }) { sub ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = sub.planName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(text = "${sub.startDate} - ${sub.endDate}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text(text = "Meals: ${sub.consumedMeals}/${sub.totalMeals} • Status: ${sub.status}", fontSize = 11.sp)
                                    }
                                    Text(
                                        text = "₹${sub.price.toInt()}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Payments Ledger List
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 32.dp)
                    ) {
                        items(payments, key = { it.id }) { pay ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = "₹${pay.amount.toInt()} (${pay.paymentMethod})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(text = pay.date, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        if (pay.note.isNotBlank()) {
                                            Text(text = pay.note, fontSize = 11.sp)
                                        }
                                    }
                                    Surface(shape = RoundedCornerShape(6.dp), color = VegGreenContainer) {
                                        Text(text = "Received", color = VegGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                    }
                                }
                            }
                        }
                    }
                }
                2 -> {
                    // Pause Schedule List
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 32.dp)
                    ) {
                        items(pauses, key = { it.id }) { pause ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = "Paused: ${pause.startDate} to ${pause.endDate}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(text = "Reason: ${pause.reason}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    IconButton(onClick = { viewModel.deletePauseSchedule(pause.id) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialogs
    if (showEditDialog) {
        AddEditCustomerDialog(
            customer = currentCustomer,
            onDismiss = { showEditDialog = false },
            onSave = { id, name, phone, address, area, diet, notes ->
                viewModel.saveCustomer(id, name, phone, address, area, diet, notes)
                showEditDialog = false
            }
        )
    }

    if (showSubscriptionDialog) {
        AddSubscriptionDialog(
            customer = currentCustomer,
            mealPlans = allMealPlans,
            onDismiss = { showSubscriptionDialog = false },
            onSave = { planId, planName, startDate, endDate, totalMeals, price, paidAmount, mealTime, diet ->
                viewModel.addSubscription(
                    customerId = currentCustomer.id,
                    planId = planId,
                    planName = planName,
                    startDate = startDate,
                    endDate = endDate,
                    totalMeals = totalMeals,
                    price = price,
                    paidAmount = paidAmount,
                    mealTime = mealTime,
                    dietaryType = diet
                )
                showSubscriptionDialog = false
            }
        )
    }

    if (showPaymentDialog) {
        RecordPaymentDialog(
            customer = currentCustomer,
            suggestedAmount = pendingDue,
            onDismiss = { showPaymentDialog = false },
            onSave = { amount, method, note ->
                viewModel.recordPayment(currentCustomer.id, currentCustomer.name, amount, method, note)
                showPaymentDialog = false
            }
        )
    }

    if (showPauseDialog) {
        AddPauseDialog(
            customer = currentCustomer,
            onDismiss = { showPauseDialog = false },
            onSave = { startDate, endDate, reason ->
                viewModel.addPauseSchedule(currentCustomer.id, startDate, endDate, "BOTH", reason)
                showPauseDialog = false
            }
        )
    }
}
