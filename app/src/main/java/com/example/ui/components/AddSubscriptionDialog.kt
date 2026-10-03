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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.example.data.SampleData
import com.example.data.local.CustomerEntity
import com.example.data.local.MealPlanEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSubscriptionDialog(
    customer: CustomerEntity,
    mealPlans: List<MealPlanEntity>,
    onDismiss: () -> Unit,
    onSave: (planId: Long, planName: String, startDate: String, endDate: String, totalMeals: Int, price: Double, paidAmount: Double, mealTime: String, diet: String) -> Unit
) {
    var selectedPlan by remember { mutableStateOf(mealPlans.firstOrNull()) }
    var expanded by remember { mutableStateOf(false) }

    var startDate by remember { mutableStateOf(SampleData.getTodayDate()) }
    var endDate by remember { mutableStateOf(SampleData.getDateOffset(30)) }
    var priceStr by remember { mutableStateOf(selectedPlan?.price?.toInt()?.toString() ?: "3000") }
    var paidStr by remember { mutableStateOf(selectedPlan?.price?.toInt()?.toString() ?: "3000") }
    var mealTime by remember { mutableStateOf(selectedPlan?.mealTime ?: "BOTH") }
    var diet by remember { mutableStateOf(customer.dietaryPreference) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Subscribe ${customer.name}",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Select Meal Plan",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    OutlinedTextField(
                        value = selectedPlan?.name ?: "Select a Plan",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        mealPlans.forEach { plan ->
                            DropdownMenuItem(
                                text = { Text("${plan.name} - ₹${plan.price.toInt()}") },
                                onClick = {
                                    selectedPlan = plan
                                    priceStr = plan.price.toInt().toString()
                                    paidStr = plan.price.toInt().toString()
                                    mealTime = plan.mealTime
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text("Start Date") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = { Text("End Date") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = priceStr,
                        onValueChange = { priceStr = it },
                        label = { Text("Plan Price (₹)") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = paidStr,
                        onValueChange = { paidStr = it },
                        label = { Text("Amount Paid Now") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Service Shift", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    FilterChip(
                        selected = mealTime == "LUNCH",
                        onClick = { mealTime = "LUNCH" },
                        label = { Text("Lunch") }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    FilterChip(
                        selected = mealTime == "DINNER",
                        onClick = { mealTime = "DINNER" },
                        label = { Text("Dinner") }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    FilterChip(
                        selected = mealTime == "BOTH",
                        onClick = { mealTime = "BOTH" },
                        label = { Text("Both") }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Diet Type", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val plan = selectedPlan ?: return@Button
                    val price = priceStr.toDoubleOrNull() ?: plan.price
                    val paid = paidStr.toDoubleOrNull() ?: price
                    onSave(
                        plan.id,
                        plan.name,
                        startDate,
                        endDate,
                        plan.totalMeals,
                        price,
                        paid,
                        mealTime,
                        diet
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("btn_save_subscription")
            ) {
                Text("Start Subscription")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
