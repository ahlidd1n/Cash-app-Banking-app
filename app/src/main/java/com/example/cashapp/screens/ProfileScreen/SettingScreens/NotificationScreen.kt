package com.example.cashapp.screens.ProfileScreen.SettingScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    viewModel: UserViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var pushNotifications by remember { mutableStateOf(false) }
    var smsAlerts by remember { mutableStateOf(true) }
    var emailAlerts by remember { mutableStateOf(false) }

    var cashTeamNotifications by remember { mutableStateOf(false) }
    var offersAndRewards by remember { mutableStateOf(false) }
    var stockNotifications by remember { mutableStateOf(true) }
    var bitcoinNotifications by remember { mutableStateOf(false) }

    var activeManageTopic by remember { mutableStateOf<String?>(null) }
    val manageSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
    )

    val phoneLabel = when (val s = state) {
        is UiState.Success -> s.userInfo.phone.ifBlank { "SMS Alerts" }
        else -> "SMS Alerts"
    }

    val emailLabel = when (val s = state) {
        is UiState.Success -> s.userInfo.email.ifBlank { "Email Alerts" }
        else -> "Email Alerts"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .statusBarsPadding()
    ) {
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
                        text = "Notifications",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )

                    Spacer(modifier = Modifier.size(44.dp))
                }
            }

            // Group 1: Push, Phone, Email
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        NotificationCheckRow(
                            title = "Push Notifications",
                            checked = pushNotifications,
                            onCheckedChange = { checked ->
                                pushNotifications = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Push notifications enabled" else "Push notifications disabled"
                                    )
                                }
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        NotificationCheckRow(
                            title = phoneLabel,
                            checked = smsAlerts,
                            onCheckedChange = { checked ->
                                smsAlerts = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "SMS alerts enabled" else "SMS alerts disabled"
                                    )
                                }
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        NotificationCheckRow(
                            title = emailLabel,
                            checked = emailAlerts,
                            onCheckedChange = { checked ->
                                emailAlerts = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Email alerts enabled" else "Email alerts disabled"
                                    )
                                }
                            }
                        )
                    }
                }
            }

            // Group 2: Cash Team, Offers, Stock, Bitcoin
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        NotificationCheckRow(
                            title = "Cash Team Notifications",
                            checked = cashTeamNotifications,
                            onCheckedChange = { checked ->
                                cashTeamNotifications = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Cash Team updates enabled" else "Cash Team updates disabled"
                                    )
                                }
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        NotificationCheckRow(
                            title = "Square Offers and Rewards",
                            checked = offersAndRewards,
                            onCheckedChange = { checked ->
                                offersAndRewards = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Offers & rewards alerts enabled" else "Offers & rewards alerts disabled"
                                    )
                                }
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        NotificationCheckRow(
                            title = "Stock",
                            checked = stockNotifications,
                            actionText = "Manage",
                            isActionEnabled = true,
                            onCheckedChange = { checked ->
                                stockNotifications = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Stock alerts enabled" else "Stock alerts disabled"
                                    )
                                }
                            },
                            onActionClick = { activeManageTopic = "Stock" }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        NotificationCheckRow(
                            title = "Bitcoin",
                            checked = bitcoinNotifications,
                            actionText = "Manage",
                            isActionEnabled = bitcoinNotifications,
                            onCheckedChange = { checked ->
                                bitcoinNotifications = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Bitcoin alerts enabled" else "Bitcoin alerts disabled"
                                    )
                                }
                            },
                            onActionClick = {
                                if (bitcoinNotifications) {
                                    activeManageTopic = "Bitcoin"
                                }
                            }
                        )
                    }
                }
            }
        }

        val topic = activeManageTopic
        if (topic != null) {
            ModalBottomSheet(
                onDismissRequest = { activeManageTopic = null },
                sheetState = manageSheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "$topic Alert Preferences",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Receive price movement and order status notifications for $topic.",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                activeManageTopic = null
                                scope.launch {
                                    snackBarHostState.showSnackbar("Saved $topic alert settings")
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF141714)
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Done",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

    }
}

@Composable
private fun NotificationCheckRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    actionText: String? = null,
    isActionEnabled: Boolean = false,
    onActionClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(if (checked) Color(0xFF00C244) else Color.Transparent)
                .border(
                    width = if (checked) 0.dp else 2.dp,
                    color = if (checked) Color(0xFF00C244) else Color(0xFFD1D5DB),
                    shape = RoundedCornerShape(7.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF111827),
            modifier = Modifier.weight(1f)
        )

        if (actionText != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = actionText,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isActionEnabled) Color(0xFF00C244) else Color(0xFFD1D5DB),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(enabled = isActionEnabled, onClick = onActionClick)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}