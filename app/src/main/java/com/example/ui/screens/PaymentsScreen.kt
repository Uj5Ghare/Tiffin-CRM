package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.CustomerEntity
import com.example.data.viewmodel.TiffinViewModel
import com.example.ui.components.RecordPaymentDialog
import com.example.ui.components.StatCard
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.EmeraldSecondaryContainer
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.NonVegRedContainer
import com.example.ui.theme.StatusDelivered
import com.example.ui.theme.VegGreen
import com.example.ui.theme.VegGreenContainer
import com.example.ui.util.AppLanguage
import com.example.ui.util.tr

@Composable
fun PaymentsScreen(
    viewModel: TiffinViewModel,
    language: AppLanguage = AppLanguage.ENGLISH,
    onCustomerClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val payments by viewModel.allPayments.collectAsStateWithLifecycle()
    val subscriptions by viewModel.allSubscriptions.collectAsStateWithLifecycle()
    val customers by viewModel.allCustomers.collectAsStateWithLifecycle()

    val totalCollected = payments.sumOf { it.amount }
    val totalPendingDues = subscriptions.sumOf { (it.price - it.paidAmount).coerceAtLeast(0.0) }

    // Customers with pending balance
    val customersWithDues = customers.mapNotNull { customer ->
        val activeSub = subscriptions.firstOrNull { it.customerId == customer.id && it.status == "ACTIVE" }
        val due = if (activeSub != null) (activeSub.price - activeSub.paidAmount).coerceAtLeast(0.0) else 0.0
        if (due > 0) Pair(customer, due) else null
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    var customerToRecordPayment by remember { mutableStateOf<Pair<CustomerEntity, Double>?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "title_ledger".tr(language),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Financial Summary Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "total_collected".tr(language),
                value = "₹${totalCollected.toInt()}",
                subtitle = "${payments.size} ${"payments".tr(language)}",
                icon = Icons.Default.AccountBalanceWallet,
                iconColor = EmeraldSecondary,
                iconBgColor = EmeraldSecondaryContainer,
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "pending_payments".tr(language),
                value = "₹${totalPendingDues.toInt()}",
                subtitle = "${customersWithDues.size} ${"pending".tr(language)}",
                icon = Icons.Default.MoneyOff,
                iconColor = NonVegRed,
                iconBgColor = NonVegRedContainer,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs Header
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("${"pending_payments".tr(language)} (${customersWithDues.size})") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("${"title_ledger".tr(language)} (${payments.size})") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (selectedTab) {
            0 -> {
                // Pending Dues List
                if (customersWithDues.isEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = VegGreenContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "🎉 All customer balances are clear!",
                            color = VegGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 32.dp)
                    ) {
                        items(customersWithDues, key = { it.first.id }) { (customer, due) ->
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
                                        Column {
                                            Text(
                                                text = customer.name,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "${customer.area} • ${customer.phone}",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = NonVegRedContainer
                                        ) {
                                            Text(
                                                text = "${"pending".tr(language)} ₹${due.toInt()}",
                                                color = NonVegRed,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                customerToRecordPayment = Pair(customer, due)
                                            },
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(Icons.Default.Payments, contentDescription = "Pay", modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("action_record_payment".tr(language), fontSize = 12.sp)
                                        }

                                        OutlinedButton(
                                            onClick = {
                                                val msg = "Hello ${customer.name}, friendly reminder that your Tiffin subscription payment of ₹${due.toInt()} is due. Please pay via UPI or cash. Thank you! 🍲"
                                                viewModel.sendWhatsAppMessage(context, customer.phone, msg)
                                            },
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusDelivered),
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Icon(Icons.Default.Send, contentDescription = "Send Reminder", modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("send_reminder".tr(language), fontSize = 12.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                // Payment Transactions History
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    items(payments, key = { it.id }) { payment ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = payment.customerName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = "${payment.date} • ${payment.paymentMethod}",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    if (payment.note.isNotBlank()) {
                                        Text(
                                            text = payment.note,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Text(
                                    text = "+ ₹${payment.amount.toInt()}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = VegGreen
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Record Payment Modal
    customerToRecordPayment?.let { (customer, due) ->
        RecordPaymentDialog(
            customer = customer,
            suggestedAmount = due,
            onDismiss = { customerToRecordPayment = null },
            onSave = { amount, method, note ->
                viewModel.recordPayment(
                    customerId = customer.id,
                    customerName = customer.name,
                    amount = amount,
                    paymentMethod = method,
                    note = note
                )
                customerToRecordPayment = null
            }
        )
    }
}
