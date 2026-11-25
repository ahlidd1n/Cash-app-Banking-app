package com.example.cashapp.screens.HomeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CashAppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) { data ->
        CashAppSnackbarCard(data = data)
    }
}

@Composable
fun CashAppSnackbarCard(
    data: SnackbarData,
    modifier: Modifier = Modifier
) {
    val message = data.visuals.message
    val isError = message.contains("insufficient", ignoreCase = true) ||
            message.contains("invalid", ignoreCase = true) ||
            message.contains("valid", ignoreCase = true) ||
            message.contains("error", ignoreCase = true) ||
            message.contains("failed", ignoreCase = true) ||
            message.contains("must be", ignoreCase = true)

    val cardBg = if (isError) Color(0xFFFFF5F5) else Color.White
    val borderColor = if (isError) Color(0xFFFECACA) else Color(0xFFDCFCE7)
    val badgeBg = if (isError) Color(0xFFFEE2E2) else Color(0xFFDCFCE7)
    val iconTint = if (isError) Color(0xFFDC2626) else Color(0xFF16A34A)
    val textColor = if (isError) Color(0xFF7F1D1D) else Color(0xFF111827)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        color = cardBg,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(badgeBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isError) {
                        Icons.Outlined.ErrorOutline
                    } else {
                        Icons.Outlined.CheckCircle
                    },
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = message,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            val actionLabel = data.visuals.actionLabel
            if (actionLabel != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = actionLabel,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isError) Color(0xFFDC2626) else Color(0xFF15803D),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { data.performAction() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            } else {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .clickable { data.dismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Dismiss",
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}