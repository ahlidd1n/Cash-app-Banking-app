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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashapp.screens.HomeScreens.CashAppSnackbarHost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityPrivacyScreen(
    onBackClick: () -> Unit = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var biometricEnabled by remember { mutableStateOf(true) }
    var twoFactorEnabled by remember { mutableStateOf(true) }
    var loginAlertsEnabled by remember { mutableStateOf(true) }
    var hideBalancesInFeed by remember { mutableStateOf(false) }

    var showChangePinSheet by remember { mutableStateOf(false) }
    var showDevicesSheet by remember { mutableStateOf(false) }

    val pinSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val devicesSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
    )

    val enabledCount = listOf(
        biometricEnabled,
        twoFactorEnabled,
        loginAlertsEnabled
    ).count { it }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
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
                        text = "Security & Privacy",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )

                    Spacer(modifier = Modifier.size(44.dp))
                }
            }

            // Protection Status Card
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0xFFDCFCE7)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Shield,
                                        contentDescription = null,
                                        tint = Color(0xFF15803D),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "ACCOUNT PROTECTION",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp,
                                        color = Color(0xFF16A34A)
                                    )
                                    Text(
                                        text = if (enabledCount == 3) "Strong protection" else "Standard protection",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF111827)
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF0FDF4)
                            ) {
                                Text(
                                    text = "$enabledCount/3 active",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Manage sign-in verification, biometric unlock, and privacy preferences for your account.",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }

            // Security Toggles Card
            item {
                Text(
                    text = "SECURITY CONTROLS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        SecuritySwitchRow(
                            icon = Icons.Outlined.Fingerprint,
                            iconBg = Color(0xFFDCFCE7),
                            iconTint = Color(0xFF15803D),
                            title = "Biometric unlock",
                            subtitle = "Use fingerprint or face to approve actions",
                            checked = biometricEnabled,
                            onCheckedChange = { checked ->
                                biometricEnabled = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Biometric unlock enabled" else "Biometric unlock disabled"
                                    )
                                }
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        SecuritySwitchRow(
                            icon = Icons.Outlined.Lock,
                            iconBg = Color(0xFFEDE9FE),
                            iconTint = Color(0xFF6D28D9),
                            title = "Two-factor authentication",
                            subtitle = "Require verification code on new sign-ins",
                            checked = twoFactorEnabled,
                            onCheckedChange = { checked ->
                                twoFactorEnabled = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "2FA enabled" else "2FA disabled"
                                    )
                                }
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        SecuritySwitchRow(
                            icon = Icons.Outlined.NotificationsActive,
                            iconBg = Color(0xFFE0F2FE),
                            iconTint = Color(0xFF0284C7),
                            title = "Sign-in & security alerts",
                            subtitle = "Get notified about logins and card changes",
                            checked = loginAlertsEnabled,
                            onCheckedChange = { checked ->
                                loginAlertsEnabled = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Security alerts enabled" else "Security alerts muted"
                                    )
                                }
                            }
                        )
                    }
                }
            }

            // Privacy & Access Actions
            item {
                Text(
                    text = "PRIVACY & ACCESS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        SecuritySwitchRow(
                            icon = Icons.Outlined.VisibilityOff,
                            iconBg = Color(0xFFFEF3C7),
                            iconTint = Color(0xFFB45309),
                            title = "Mask balances on lock screen",
                            subtitle = "Hide sensitive amounts when away",
                            checked = hideBalancesInFeed,
                            onCheckedChange = { checked ->
                                hideBalancesInFeed = checked
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        if (checked) "Balance masking enabled" else "Balance masking disabled"
                                    )
                                }
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        SecurityActionRow(
                            icon = Icons.Outlined.Key,
                            iconBg = Color(0xFFF3F4F6),
                            iconTint = Color(0xFF1F2937),
                            title = "Change passcode / PIN",
                            subtitle = "Update your 6-digit security passcode",
                            onClick = { showChangePinSheet = true }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFFF3F4F6)
                        )

                        SecurityActionRow(
                            icon = Icons.Outlined.Devices,
                            iconBg = Color(0xFFF3F4F6),
                            iconTint = Color(0xFF1F2937),
                            title = "Active devices",
                            subtitle = "1 active device · Dushanbe",
                            onClick = { showDevicesSheet = true }
                        )
                    }
                }
            }
        }

        // Change Passcode Sheet
        if (showChangePinSheet) {
            var currentPin by remember { mutableStateOf("") }
            var newPin by remember { mutableStateOf("") }

            ModalBottomSheet(
                onDismissRequest = { showChangePinSheet = false },
                sheetState = pinSheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Change Passcode",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "Choose a new numeric passcode to protect sensitive actions.",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = currentPin,
                        onValueChange = { if (it.length <= 6) currentPin = it },
                        label = { Text("Current passcode") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF16A34A),
                            unfocusedBorderColor = Color(0xFFE5E7EB)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = newPin,
                        onValueChange = { if (it.length <= 6) newPin = it },
                        label = { Text("New passcode (4–6 digits)") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF16A34A),
                            unfocusedBorderColor = Color(0xFFE5E7EB)
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            if (newPin.length < 4) {
                                scope.launch {
                                    snackBarHostState.showSnackbar("Passcode must be at least 4 characters")
                                }
                                return@Button
                            }
                            showChangePinSheet = false
                            scope.launch {
                                snackBarHostState.showSnackbar("Passcode updated")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF141714),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Update Passcode",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        // Active Devices Sheet
        if (showDevicesSheet) {
            ModalBottomSheet(
                onDismissRequest = { showDevicesSheet = false },
                sheetState = devicesSheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Active Devices",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "Devices currently signed in to your account.",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF9FAFB)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Android Device · Current Session",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Dushanbe · Active now",
                                fontSize = 12.sp,
                                color = Color(0xFF15803D)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        CashAppSnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun SecuritySwitchRow(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
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
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color(0xFF6B7280)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF22C55E)
            )
        )
    }
}

@Composable
private fun SecurityActionRow(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
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
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color(0xFF6B7280)
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFF9CA3AF)
        )
    }
}