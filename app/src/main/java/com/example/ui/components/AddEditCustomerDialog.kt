package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.CustomerEntity

@Composable
fun AddEditCustomerDialog(
    customer: CustomerEntity? = null,
    onDismiss: () -> Unit,
    onSave: (id: Long, name: String, phone: String, address: String, area: String, diet: String, notes: String) -> Unit
) {
    var name by remember { mutableStateOf(customer?.name ?: "") }
    var phone by remember { mutableStateOf(customer?.phone ?: "") }
    var address by remember { mutableStateOf(customer?.address ?: "") }
    var area by remember { mutableStateOf(customer?.area ?: "") }
    var diet by remember { mutableStateOf(customer?.dietaryPreference ?: "VEG") }
    var notes by remember { mutableStateOf(customer?.deliveryNotes ?: "") }

    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (customer == null) "Add New Customer" else "Edit Customer",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; nameError = false },
                    label = { Text("Full Name *") },
                    isError = nameError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_customer_name")
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it; phoneError = false },
                    label = { Text("Phone Number *") },
                    isError = phoneError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_customer_phone")
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text("Area / Route (e.g. Indiranagar)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_customer_area")
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Full Delivery Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_customer_address")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Dietary Preference",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    FilterChip(
                        selected = diet == "VEG",
                        onClick = { diet = "VEG" },
                        label = { Text("Pure Veg") }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    FilterChip(
                        selected = diet == "NON_VEG",
                        onClick = { diet = "NON_VEG" },
                        label = { Text("Non-Veg") }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    FilterChip(
                        selected = diet == "JAIN",
                        onClick = { diet = "JAIN" },
                        label = { Text("Jain") }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Delivery Instructions / Notes") },
                    placeholder = { Text("e.g. Leave at door, less spicy") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_customer_notes")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isBlank()) nameError = true
                    if (phone.isBlank()) phoneError = true
                    if (!nameError && !phoneError) {
                        onSave(
                            customer?.id ?: 0L,
                            name,
                            phone,
                            address,
                            area,
                            diet,
                            notes
                        )
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("btn_save_customer")
            ) {
                Text("Save Customer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
