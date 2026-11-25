package com.example.cashapp.screens.ProfileScreen.SettingScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cashapp.screens.HomeScreens.CashAppSnackbarHost
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun LimitsScreen(
    viewModel: UserViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val spentThisMonth = 126.80
    val spentToday = 24.50

    var monthlyLimit by remember { mutableFloatStateOf(300f) }
    var dailyLimit by remember { mutableFloatStateOf(100f) }
    var savedMonthlyLimit by remember { mutableFloatStateOf(300f) }
    var savedDailyLimit by remember { mutableFloatStateOf(100f) }
    var isSaving by remember { mutableStateOf(false) }

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
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
                val monthlyProgress = (spentThisMonth / savedMonthlyLimit.toDouble())
                    .toFloat()
                    .coerceIn(0f, 1f)
                val dailyProgress = (spentToday / savedDailyLimit.toDouble())
                    .toFloat()
                    .coerceIn(0f, 1f)

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 28.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Top Bar
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(44.dp),
                                shape = CircleShape,
                                color = Color.White,
                                shadowElevation = 2.dp
                            ) {
                                IconButton(onClick = onBackClick) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = Color(0xFF111827)
                                    )
                                }
                            }

                            Text(
                                text = "Limits",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )

                            Spacer(modifier = Modifier.size(44.dp))
                        }
                    }

                    // Monthly Spending Overview Card
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            shadowElevation = 2.dp
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "MONTHLY SPENDING LIMIT",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp,
                                        color = Color(0xFF16A34A)
                                    )

                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xFFF0FDF4)
                                    ) {
                                        Text(
                                            text = "${(monthlyProgress * 100).roundToInt()}% used",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF15803D),
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Text(
                                        text = "$${String.format(Locale.US, "%.2f", spentThisMonth)}",
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF111827)
                                    )

                                    Text(
                                        text = "of $${savedMonthlyLimit.roundToInt()} limit",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF6B7280)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                LinearProgressIndicator(
                                    progress = { monthlyProgress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    color = Color(0xFFA3E635),
                                    trackColor = Color(0xFFE5E7EB),
                                    strokeCap = StrokeCap.Round
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.AccountBalanceWallet,
                                        contentDescription = null,
                                        tint = Color(0xFF6B7280),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "$${String.format(Locale.US, "%.2f", user.balance)} available in Cash balance",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }
                            }
                        }
                    }

                    // Adjust Monthly & Daily Limits Card
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            shadowElevation = 2.dp
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "CARD SPENDING CONTROLS",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp,
                                    color = Color(0xFF6B7280)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Customize your limits",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111827)
                                )

                                Spacer(modifier = Modifier.height(18.dp))

                                // Monthly Limit Slider
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.CalendarMonth,
                                            contentDescription = null,
                                            tint = Color(0xFF15803D),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Monthly card limit",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF111827)
                                        )
                                    }

                                    Text(
                                        text = "$${monthlyLimit.roundToInt()}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF111827)
                                    )
                                }

                                Slider(
                                    value = monthlyLimit,
                                    onValueChange = { monthlyLimit = it },
                                    valueRange = 100f..2000f,
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFF141714),
                                        activeTrackColor = Color(0xFF22C55E),
                                        inactiveTrackColor = Color(0xFFE5E7EB)
                                    )
                                )

                                // Quick presets for monthly limit
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf(300, 500, 1000, 1500).forEach { preset ->
                                        val isSelected = monthlyLimit.roundToInt() == preset
                                        Surface(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(12.dp))
                                                .clickable { monthlyLimit = preset.toFloat() },
                                            shape = RoundedCornerShape(12.dp),
                                            color = if (isSelected) Color(0xFFDCFCE7) else Color(0xFFF3F4F6)
                                        ) {
                                            Box(
                                                modifier = Modifier.padding(vertical = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "$$preset",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (isSelected) Color(0xFF14532D) else Color(0xFF374151)
                                                )
                                            }
                                        }
                                    }
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 18.dp),
                                    color = Color(0xFFF3F4F6)
                                )

                                // Daily Limit Slider
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.Today,
                                            contentDescription = null,
                                            tint = Color(0xFF6D28D9),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Daily spending limit",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF111827)
                                        )
                                    }

                                    Text(
                                        text = "$${dailyLimit.roundToInt()}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF111827)
                                    )
                                }

                                Slider(
                                    value = dailyLimit,
                                    onValueChange = { dailyLimit = it },
                                    valueRange = 25f..500f,
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFF141714),
                                        activeTrackColor = Color(0xFF22C55E),
                                        inactiveTrackColor = Color(0xFFE5E7EB)
                                    )
                                )

                                Text(
                                    text = "Today's spend: $${String.format(Locale.US, "%.2f", spentToday)} (${(dailyProgress * 100).roundToInt()}% of daily limit)",
                                    fontSize = 12.sp,
                                    color = Color(0xFF6B7280)
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    onClick = {
                                        isSaving = true
                                        savedMonthlyLimit = monthlyLimit
                                        savedDailyLimit = dailyLimit
                                        isSaving = false
                                        scope.launch {
                                            snackBarHostState.showSnackbar(
                                                "Limits updated: $${monthlyLimit.roundToInt()}/mo · $${dailyLimit.roundToInt()}/day"
                                            )
                                        }
                                    },
                                    enabled = !isSaving,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF141714),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        text = "Save Limits",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Card Reference Info
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White,
                            shadowElevation = 1.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFEDE9FE)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.CreditCard,
                                        contentDescription = null,
                                        tint = Color(0xFF6D28D9),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Applies to Cash Card",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF111827)
                                    )
                                    Text(
                                        text = "Card ending in ${user.cardNumber.toString().takeLast(4).padStart(4, '0')}",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        CashAppSnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}