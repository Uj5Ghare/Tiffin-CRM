package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.CustomerEntity
import com.example.data.viewmodel.TiffinViewModel
import com.example.ui.components.AddEditCustomerDialog
import com.example.ui.components.CustomerCard
import com.example.ui.components.RecordPaymentDialog
import com.example.ui.util.AppLanguage
import com.example.ui.util.tr

@Composable
fun CustomersScreen(
    viewModel: TiffinViewModel,
    language: AppLanguage = AppLanguage.ENGLISH,
    onCustomerClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val customers by viewModel.allCustomers.collectAsStateWithLifecycle()
    val subscriptions by viewModel.allSubscriptions.collectAsStateWithLifecycle()
    val searchQuery by viewModel.customerSearchQuery.collectAsStateWithLifecycle()
    val dietFilter by viewModel.customerDietFilter.collectAsStateWithLifecycle()

    var showAddDialog by remember { mutableStateOf(false) }
    var customerToRecordPayment by remember { mutableStateOf<CustomerEntity?>(null) }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                icon = { Icon(Icons.Default.PersonAdd, contentDescription = "add_customer".tr(language)) },
                text = { Text("add_customer".tr(language)) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.testTag("fab_add_customer")
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.customerSearchQuery.value = it },
                placeholder = { Text("search_customer".tr(language)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { viewModel.customerSearchQuery.value = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_customer_search")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Diet Filter Chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = dietFilter == "ALL",
                        onClick = { viewModel.customerDietFilter.value = "ALL" },
                        label = { Text("${"total_customers".tr(language)} (${customers.size})") }
                    )
                }
                item {
                    FilterChip(
                        selected = dietFilter == "VEG",
                        onClick = { viewModel.customerDietFilter.value = "VEG" },
                        label = { Text("diet_veg".tr(language)) }
                    )
                }
                item {
                    FilterChip(
                        selected = dietFilter == "NON_VEG",
                        onClick = { viewModel.customerDietFilter.value = "NON_VEG" },
                        label = { Text("diet_nonveg".tr(language)) }
                    )
                }
                item {
                    FilterChip(
                        selected = dietFilter == "JAIN",
                        onClick = { viewModel.customerDietFilter.value = "JAIN" },
                        label = { Text("diet_jain".tr(language)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Customer List
            if (customers.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = "No customers",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No customers match your search.",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(customers, key = { it.id }) { customer ->
                        val activeSub = subscriptions.firstOrNull {
                            it.customerId == customer.id && it.status == "ACTIVE"
                        }
                        CustomerCard(
                            customer = customer,
                            subscription = activeSub,
                            language = language,
                            onCardClick = {
                                viewModel.selectCustomer(customer.id)
                                onCustomerClick(customer.id)
                            },
                            onRecordPaymentClick = {
                                customerToRecordPayment = customer
                            },
                            onSendWhatsApp = { phone, msg ->
                                viewModel.sendWhatsAppMessage(context, phone, msg)
                            }
                        )
                    }
                }
            }
        }
    }

    // Add Customer Dialog
    if (showAddDialog) {
        AddEditCustomerDialog(
            customer = null,
            onDismiss = { showAddDialog = false },
            onSave = { _, name, phone, address, area, diet, notes ->
                viewModel.saveCustomer(
                    name = name,
                    phone = phone,
                    address = address,
                    area = area,
                    dietaryPreference = diet,
                    deliveryNotes = notes
                )
                showAddDialog = false
            }
        )
    }

    // Record Payment Dialog
    customerToRecordPayment?.let { customer ->
        val activeSub = subscriptions.firstOrNull { it.customerId == customer.id && it.status == "ACTIVE" }
        val suggestedDue = if (activeSub != null) (activeSub.price - activeSub.paidAmount).coerceAtLeast(0.0) else 0.0

        RecordPaymentDialog(
            customer = customer,
            suggestedAmount = suggestedDue,
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
