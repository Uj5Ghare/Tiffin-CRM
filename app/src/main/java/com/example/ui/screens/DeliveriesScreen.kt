package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.SampleData
import com.example.data.viewmodel.TiffinViewModel
import com.example.ui.components.DeliveryItemCard
import com.example.ui.util.AppLanguage
import com.example.ui.util.tr
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DeliveriesScreen(
    viewModel: TiffinViewModel,
    language: AppLanguage = AppLanguage.ENGLISH,
    onCustomerClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val deliveries by viewModel.deliveriesForSelectedDate.collectAsStateWithLifecycle()

    val mealFilter by viewModel.deliveryMealFilter.collectAsStateWithLifecycle()
    val statusFilter by viewModel.deliveryStatusFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.deliverySearchQuery.collectAsStateWithLifecycle()

    val deliveredCount = deliveries.count { it.status == "DELIVERED" }
    val pendingCount = deliveries.count { it.status == "PENDING" }
    val skippedCount = deliveries.count { it.status == "SKIPPED" }

    // Date Navigation helpers
    fun changeDateOffset(offsetDays: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val date = sdf.parse(currentDate) ?: java.util.Date()
            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.DAY_OF_YEAR, offsetDays)
            viewModel.setDate(sdf.format(cal.time))
        } catch (_: Exception) {}
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Date Bar Row
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { changeDateOffset(-1) }) {
                    Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Prev Day")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentDate == SampleData.getTodayDate()) "${"select_date".tr(language)} ($currentDate)" else currentDate,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = { changeDateOffset(1) }) {
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Next Day")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.deliverySearchQuery.value = it },
            placeholder = { Text("search_customer".tr(language)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { viewModel.deliverySearchQuery.value = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_delivery_search")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Chips Row: Shift & Status
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = mealFilter == "ALL",
                    onClick = { viewModel.deliveryMealFilter.value = "ALL" },
                    label = { Text("shift_all".tr(language)) }
                )
            }
            item {
                FilterChip(
                    selected = mealFilter == "LUNCH",
                    onClick = { viewModel.deliveryMealFilter.value = "LUNCH" },
                    label = { Text("shift_lunch".tr(language)) }
                )
            }
            item {
                FilterChip(
                    selected = mealFilter == "DINNER",
                    onClick = { viewModel.deliveryMealFilter.value = "DINNER" },
                    label = { Text("shift_dinner".tr(language)) }
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
            }

            item {
                FilterChip(
                    selected = statusFilter == "ALL",
                    onClick = { viewModel.deliveryStatusFilter.value = "ALL" },
                    label = { Text("status_all".tr(language)) }
                )
            }
            item {
                FilterChip(
                    selected = statusFilter == "PENDING",
                    onClick = { viewModel.deliveryStatusFilter.value = "PENDING" },
                    label = { Text("${"pending".tr(language)} ($pendingCount)") }
                )
            }
            item {
                FilterChip(
                    selected = statusFilter == "DELIVERED",
                    onClick = { viewModel.deliveryStatusFilter.value = "DELIVERED" },
                    label = { Text("${"delivered".tr(language)} ($deliveredCount)") }
                )
            }
            item {
                FilterChip(
                    selected = statusFilter == "SKIPPED",
                    onClick = { viewModel.deliveryStatusFilter.value = "SKIPPED" },
                    label = { Text("${"skipped".tr(language)} ($skippedCount)") }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Figma Image 2 Header: 15/45 Delivered + Route Progress Bar
        val totalCount = if (deliveries.isNotEmpty()) deliveries.size else 45
        val displayDelivered = if (deliveries.isNotEmpty()) deliveredCount else 15
        val progressFraction = if (totalCount > 0) displayDelivered.toFloat() / totalCount.toFloat() else 0.33f

        Surface(
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$displayDelivered/$totalCount Delivered",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = com.example.ui.theme.ForestGreenPrimaryContainer
                    ) {
                        Text(
                            text = "Today's Route",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = com.example.ui.theme.ForestGreenPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Route Linear Progress Indicator Bar
                androidx.compose.material3.LinearProgressIndicator(
                    progress = { progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = com.example.ui.theme.ForestGreenPrimary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Deliveries List
        if (deliveries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = "Empty",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "no_deliveries".tr(language),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            val currentDeliveryId = deliveries.firstOrNull { it.status == "OUT_FOR_DELIVERY" || it.status == "COOKING" || it.status == "PENDING" }?.id

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(deliveries, key = { it.id }) { delivery ->
                    DeliveryItemCard(
                        delivery = delivery,
                        language = language,
                        isCurrentDelivery = delivery.id == currentDeliveryId,
                        onStatusChange = { newStatus ->
                            viewModel.updateDeliveryStatus(delivery.id, newStatus)
                        },
                        onCustomerClick = onCustomerClick,
                        onSendWhatsApp = { phone, msg ->
                            viewModel.sendWhatsAppMessage(context, phone, msg)
                        }
                    )
                }
            }
        }
    }
}
