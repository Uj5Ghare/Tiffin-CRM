package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.JainPurple
import com.example.ui.theme.JainPurpleContainer
import com.example.ui.theme.NonVegRed
import com.example.ui.theme.NonVegRedContainer
import com.example.ui.theme.StatusDelivered
import com.example.ui.theme.StatusPending
import com.example.ui.theme.StatusSkipped
import com.example.ui.theme.VegGreen
import com.example.ui.theme.VegGreenContainer
import com.example.ui.util.AppLanguage
import com.example.ui.util.tr

@Composable
fun DietaryBadge(
    dietaryType: String,
    language: AppLanguage = AppLanguage.ENGLISH,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, key) = when (dietaryType.uppercase()) {
        "NON_VEG" -> Triple(NonVegRedContainer, NonVegRed, "diet_nonveg")
        "JAIN" -> Triple(JainPurpleContainer, JainPurple, "diet_jain")
        else -> Triple(VegGreenContainer, VegGreen, "diet_veg")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(textColor)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = key.tr(language),
                color = textColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DeliveryStatusBadge(
    status: String,
    language: AppLanguage = AppLanguage.ENGLISH,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, icon, key) = when (status.uppercase()) {
        "DELIVERED" -> Quadruple(
            StatusDelivered.copy(alpha = 0.18f),
            StatusDelivered,
            Icons.Default.Check,
            "delivered"
        )
        "SKIPPED" -> Quadruple(
            StatusSkipped.copy(alpha = 0.18f),
            StatusSkipped,
            Icons.Default.Close,
            "skipped"
        )
        else -> Quadruple(
            StatusPending.copy(alpha = 0.18f),
            StatusPending,
            Icons.Default.Schedule,
            "pending"
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = key.tr(language),
                tint = textColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = key.tr(language),
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun MealTimeBadge(
    mealTime: String,
    language: AppLanguage = AppLanguage.ENGLISH,
    modifier: Modifier = Modifier
) {
    val key = when (mealTime.uppercase()) {
        "LUNCH" -> "shift_lunch"
        "DINNER" -> "shift_dinner"
        else -> "shift_all"
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = key.tr(language),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
