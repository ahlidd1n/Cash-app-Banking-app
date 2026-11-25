package com.example.cashapp.screens.CardScreens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel
import java.util.Locale

@Composable
fun MainCardScreen(
    navigation: () -> Unit = {},
    viewModel: UserViewModel = viewModel(),
    onSeeAllActivity: () -> Unit = {}
){
    val state by viewModel.uiState.collectAsState()

    var isLocked by remember { mutableStateOf(false) }
    var hideDetails by remember { mutableStateOf(false) }

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
    )

    val sampleActivities = remember {
        listOf(
            CardActivityItem(
                initials = "BL",
                title = "Blue Bottle Coffee",
                subtitle = "Today, 9:42 AM",
                amountText = "-$6.80",
                isPositive = false
            )
        )
    }

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
                val prefix = "11112222"
                val apiCard = user.cardNumber.toString()
                val fullCard = prefix + apiCard

                val displayCardNumber = if (hideDetails) {
                    val lastFour = fullCard.takeLast(4).padStart(4, '0')
                    "••••  ••••  ••••  $lastFour"
                } else {
                    fullCard.chunked(4).joinToString(" ")
                }

                val initials = user.fullName
                    .trim()
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .take(2)
                    .joinToString("") { it.first().uppercase() }
                    .ifBlank { "U" }

                val spentThisMonth = 126.80
                val monthlyLimit = 300.00
                val progress = (spentThisMonth / monthlyLimit).toFloat().coerceIn(0f, 1f)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Header: YOUR WALLET / Card + Avatar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "YOUR WALLET",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                color = Color(0xFF6B7280)
                            )
                            Text(
                                text = "Card",
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

                    // Virtual Debit Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF2B2E29),
                                        Color(0xFF161715),
                                        Color(0xFF1E201C)
                                    )
                                )
                            )
                    ) {
                        // Lime green decorative circle on top-right
                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .align(Alignment.TopEnd)
                                .offset(x = 52.dp, y = (-48).dp)
                                .clip(CircleShape)
                                .background(Color(0xFFC6F455))
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Top row: Money + contactless icon
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Money",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Icon(
                                    imageVector = Icons.Outlined.Wifi,
                                    contentDescription = "Contactless",
                                    tint = Color(0xFF161813),
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            // Chip + Card number
                            Column {
                                Box(
                                    modifier = Modifier
                                        .width(38.dp)
                                        .height(28.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFFC5D68D))
                                        .border(1.dp, Color(0xFF9EB066), RoundedCornerShape(6.dp))
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = displayCardNumber,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.5.sp
                                )
                            }

                            // Bottom row: Cardholder, Valid , DEBIT / VISA
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column {
                                    Text(
                                        text = user.fullName.uppercase(),
                                        color = Color(0xFFD1D5DB),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        letterSpacing = 0.6.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "DEBIT",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }

                                Column(horizontalAlignment = Alignment.Start) {
                                    Text(
                                        text = "VALID THRU",
                                        color = Color(0xFF9CA3AF),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = if (hideDetails) "••/••" else "08/29",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Text(
                                    text = "VISA",
                                    color = Color(0xFF1E221A),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Card status indicator
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isLocked) Color(0xFFEF4444) else Color(0xFF84CC16)
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isLocked) "Card is locked" else "Card is active",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF4B5563)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action Tiles: Lock card, Hide details, Settings
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        CardActionTile(
                            title = if (isLocked) "Unlock card" else "Lock card",
                            icon = if (isLocked) Icons.Outlined.LockOpen else Icons.Outlined.Lock,
                            isActive = isLocked,
                            onClick = { isLocked = !isLocked },
                            modifier = Modifier.weight(1f)
                        )

                        CardActionTile(
                            title = if (hideDetails) "Show details" else "Hide details",
                            icon = if (hideDetails) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            isActive = hideDetails,
                            onClick = { hideDetails = !hideDetails },
                            modifier = Modifier.weight(1f)
                        )

                        CardActionTile(
                            title = "Settings",
                            icon = Icons.Outlined.Settings,
                            isActive = false,
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Spent This Month Card
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Spent this month",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280),
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", spentThisMonth)}",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF111827)
                                )

                                Text(
                                    text = "$${monthlyLimit.toInt()} limit",
                                    fontSize = 13.sp,
                                    color = Color(0xFF6B7280),
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(7.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                color = Color(0xFFA3E635),
                                trackColor = Color(0xFFE5E7EB),
                                strokeCap = StrokeCap.Round
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "$${String.format(Locale.US, "%.2f", user.balance)} available in Cash balance",
                                fontSize = 12.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Recent Activity Card
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
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
                                    text = "Recent activity",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111827)
                                )

                                Text(
                                    text = "See all",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF6B7280),
                                    modifier = Modifier.clickable(onClick = onSeeAllActivity)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            sampleActivities.forEach { item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFFF3F4F6)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = item.initials,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF374151)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.title,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF111827)
                                        )
                                        Text(
                                            text = item.subtitle,
                                            fontSize = 12.sp,
                                            color = Color(0xFF9CA3AF)
                                        )
                                    }

                                    Text(
                                        text = item.amountText,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.isPositive) Color(0xFF16A34A) else Color(0xFF111827)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}



data class CardActivityItem(
    val initials: String,
    val title: String,
    val subtitle: String,
    val amountText: String,
    val isPositive: Boolean = false
)

@Composable
private fun CardActionTile(
    title: String,
    icon: ImageVector,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(86.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = if (isActive) Color(0xFFF0FDF4) else Color.White,
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
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isActive) Color(0xFFDCFCE7) else Color(0xFFF3F4F6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isActive) Color(0xFF15803D) else Color(0xFF1F2937),
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isActive) Color(0xFF14532D) else Color(0xFF1F2937),
                maxLines = 1
            )
        }
    }
}