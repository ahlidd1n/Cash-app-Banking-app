package com.example.cashapp.screens.PayScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel
import java.util.Locale

private data class DayBarItem(
    val dayLabel: String,
    val fraction: Float,
    val isHighlighted: Boolean = false
)

@Composable
fun MainPayScreen(
    navigation: () -> Unit = {},
    viewModel: UserViewModel = viewModel(),
    onAddMoneyClick: () -> Unit = {},
    onCashOutClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onCardClick: () -> Unit = {},
    onViewReportClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF1F2F1)
        )
    )

    val weeklyBars = listOf(
        DayBarItem("M", 0.42f),
        DayBarItem("T", 0.58f),
        DayBarItem("W", 0.34f),
        DayBarItem("T", 0.76f),
        DayBarItem("F", 0.50f),
        DayBarItem("S", 0.88f, isHighlighted = true),
        DayBarItem("S", 0.56f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {
        when (val currentState = state) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    color = Color(0xFF16A34A),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Error -> {
                Text(
                    text = "Error: ${currentState.errorMassage}",
                    color = Color(0xFFB91C1C),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            is UiState.Success -> {
                val user = currentState.userInfo
                val totalMoney = user.balance + user.savings

                val initials = user.fullName
                    .trim()
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .take(2)
                    .joinToString("") { it.first().uppercase() }
                    .ifBlank { "U" }

                val rawCard = user.cardNumber.toString()
                val lastFour = rawCard.takeLast(4).padStart(4, '0')

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Header: FINANCIAL OVERVIEW / Money + Avatar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "FINANCIAL OVERVIEW",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                color = Color(0xFF6B7280)
                            )
                            Text(
                                text = "Money",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827),
                                letterSpacing = (-0.5).sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF18191B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = initials,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Dark Total Money Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(26.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF282B26),
                                        Color(0xFF171816),
                                        Color(0xFF1E201D)
                                    )
                                )
                            )
                    ) {
                        // Lime accent circle on top-right
                        Box(
                            modifier = Modifier
                                .size(210.dp)
                                .align(Alignment.TopEnd)
                                .offset(x = 54.dp, y = (-46).dp)
                                .clip(CircleShape)
                                .background(Color(0xFFC4F354))
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total money",
                                    color = Color(0xFFD1D5DB),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(11.dp))
                                        .background(Color(0xFF88AE3A))
                                        .clickable {},
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Settings,
                                        contentDescription = "Settings",
                                        tint = Color(0xFF141712),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "$${String.format(Locale.US, "%.2f", totalMoney)}",
                                color = Color.White,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-1).sp
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "Across all your accounts",
                                color = Color(0xFF9CA3AF),
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Sub-cards: Cash balance & Savings
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable(onClick = onAddMoneyClick),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFF232522)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(7.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFF38BDF8))
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "Cash balance",
                                                color = Color(0xFFD1D5DB),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "$${String.format(Locale.US, "%.2f", user.balance)}",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable(onClick = onSaveClick),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFF232522)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(7.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFFA3E635))
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "Savings",
                                                color = Color(0xFFD1D5DB),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "$${String.format(Locale.US, "%.2f", user.savings)}",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick Action Tiles: Add money, Cash out, Save
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MoneyActionTile(
                            title = "Add money",
                            icon = Icons.Outlined.Add,
                            onClick = onAddMoneyClick,
                            modifier = Modifier.weight(1f)
                        )
                        MoneyActionTile(
                            title = "Cash out",
                            icon = Icons.Outlined.ArrowUpward,
                            onClick = onCashOutClick,
                            modifier = Modifier.weight(1f)
                        )
                        MoneyActionTile(
                            title = "Save",
                            icon = Icons.Outlined.AccountBalanceWallet,
                            onClick = onSaveClick,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Money Insights Card
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "THIS MONTH",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.8.sp,
                                    color = Color(0xFF9CA3AF)
                                )
                                Text(
                                    text = "View report",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF6B7280),
                                    modifier = Modifier.clickable(onClick = onViewReportClick)
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "Money insights",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Money in
                                Surface(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFF6FAF4)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(Color(0xFFDCFCE7)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.ArrowUpward,
                                                contentDescription = "Money in",
                                                tint = Color(0xFF16A34A),
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = "Money in",
                                                fontSize = 11.sp,
                                                color = Color(0xFF6B7280)
                                            )
                                            Text(
                                                text = "$2,400.00",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF111827)
                                            )
                                        }
                                    }
                                }

                                // Money out
                                Surface(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFFCF7F6)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(Color(0xFFFEE2E2)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.ArrowDownward,
                                                contentDescription = "Money out",
                                                tint = Color(0xFFDC2626),
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = "Money out",
                                                fontSize = 11.sp,
                                                color = Color(0xFF6B7280)
                                            )
                                            Text(
                                                text = "$860.40",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF111827)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            // Bar chart
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(104.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                weeklyBars.forEach { bar ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Bottom,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(24.dp)
                                                .height(82.dp),
                                            contentAlignment = Alignment.BottomCenter
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(bar.fraction)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(
                                                        if (bar.isHighlighted) Color(0xFFA3E635)
                                                        else Color(0xFFE5E7EB)
                                                    )
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = bar.dayLabel,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF9CA3AF)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Your Accounts Card
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Your accounts",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111827)
                                )
                                Text(
                                    text = "+ Add",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF6B7280),
                                    modifier = Modifier.clickable(onClick = onAddMoneyClick)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            AccountRowItem(
                                icon = Icons.Outlined.AccountBalanceWallet,
                                iconBg = Color(0xFFE0F2FE),
                                iconTint = Color(0xFF0284C7),
                                title = "Cash balance",
                                subtitle = "Available now",
                                trailingText = "$${String.format(Locale.US, "%.2f", user.balance)}",
                                onClick = onAddMoneyClick
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = Color(0xFFF3F4F6)
                            )

                            AccountRowItem(
                                icon = Icons.Outlined.Shield,
                                iconBg = Color(0xFFECFCCB),
                                iconTint = Color(0xFF4D7C0F),
                                title = "Savings",
                                subtitle = "Rainy day fund",
                                trailingText = "$${String.format(Locale.US, "%.2f", user.savings)}",
                                onClick = onSaveClick
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = Color(0xFFF3F4F6)
                            )

                            AccountRowItem(
                                icon = Icons.Outlined.CreditCard,
                                iconBg = Color(0xFFEDE9FE),
                                iconTint = Color(0xFF6D28D9),
                                title = "Money card",
                                subtitle = "•••• $lastFour",
                                trailingText = "Active",
                                onClick = onCardClick
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }
}

@Composable
private fun MoneyActionTile(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(88.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(Color(0xFFECF5E3)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFF1F2937),
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1F2937),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun AccountRowItem(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    trailingText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color(0xFF9CA3AF)
            )
        }

        Text(
            text = trailingText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFF9CA3AF),
            modifier = Modifier.size(20.dp)
        )
    }
}


