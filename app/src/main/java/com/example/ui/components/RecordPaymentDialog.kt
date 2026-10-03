package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.CustomerEntity

@Composable
fun RecordPaymentDialog(
    customer: CustomerEntity,
    suggestedAmount: Double = 0.0,
    onDismiss: () -> Unit,
    onSave: (amount: Double, method: String, note: String) -> Unit
) {
    var amountStr by remember { mutableStateOf(if (suggestedAmount > 0) suggestedAmount.toInt().toString() else "") }
    var method by remember { mutableStateOf("UPI") }
    var note by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Record Payment for ${customer.name}",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = amountStr,
                    onValueChange = { amountStr = it; amountError = false },
                    label = { Text("Amount Received (₹) *") },
                    isError = amountError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_payment_amount")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Payment Mode",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    FilterChip(
                        selected = method == "UPI",
                        onClick = { method = "UPI" },
                        label = { Text("UPI / GPay") }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    FilterChip(
                        selected = method == "CASH",
                        onClick = { method = "CASH" },
                        label = { Text("Cash") }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    FilterChip(
                        selected = method == "BANK_TRANSFER",
                        onClick = { method = "BANK_TRANSFER" },
                        label = { Text("Bank Transfer") }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Notes / Reference (Optional)") },
                    placeholder = { Text("e.g. Received via PhonePe tx 8923") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_payment_note")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = amountStr.toDoubleOrNull()
                    if (amount == null || amount <= 0) {
                        amountError = true
                    } else {
                        onSave(amount, method, note)
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("btn_confirm_payment")
            ) {
                Text("Record Payment")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
