package com.example.cashapp.screens.ProfileScreen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel

private data class AccountMenuItem(
    val title: String,
    val icon: ImageVector,
    val badge: String? = null,
    val onClick: () -> Unit = {}
)

@Composable
fun YourAccountScreen(
    viewModel: UserViewModel = viewModel(),
    onCloseClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onInviteFriendsClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {},
    goToSupport: () -> Unit = {},
    goToDocuments: () -> Unit = {},
    goToLimits: () -> Unit = {},
    goToFamily: () -> Unit = {},
    goTOSecurity: () -> Unit = {},
    goTOLinkedBanks: () -> Unit = {},
    goToFavorites: () -> Unit = {},
    goToNotification: () -> Unit = {},
    goToPersonal: () -> Unit = {},
    goToLanguage: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    val menuItems = listOf(
        AccountMenuItem("Personal", Icons.Outlined.PersonOutline, onClick = goToPersonal),
        AccountMenuItem("Linked Banks", Icons.Outlined.Link, onClick = goTOLinkedBanks),
        AccountMenuItem("Security & Privacy", Icons.Outlined.Shield, onClick = goTOSecurity),
        AccountMenuItem("Favorites", Icons.Outlined.FavoriteBorder, badge = "New", onClick = goToFavorites),
        AccountMenuItem("Family", Icons.Outlined.Groups, onClick = goToFamily),
        AccountMenuItem("Limits", Icons.Outlined.Tune, onClick = goToLimits),
        AccountMenuItem("Notifications", Icons.Outlined.NotificationsNone, onClick = goToNotification),
        AccountMenuItem("Language", Icons.Outlined.Language, onClick = goToLanguage),
        AccountMenuItem("Documents", Icons.Outlined.Description, onClick = goToDocuments),
        AccountMenuItem("Support", Icons.Outlined.HelpOutline, onClick = goToSupport)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F5))
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
                val initials = user.fullName
                    .trim()
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .take(2)
                    .joinToString("") { it.first().uppercase() }
                    .ifBlank { "U" }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 28.dp)
                ) {
                    // Top bar
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Your Account",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827)
                            )

                            IconButton(onClick = onCloseClick) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color(0xFF111827),
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }

                    // Profile card
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            shadowElevation = 1.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.GridView,
                                        contentDescription = "QR / Grid",
                                        tint = Color(0xFF1F2937),
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Icon(
                                        imageVector = Icons.Outlined.Share,
                                        contentDescription = "Share",
                                        tint = Color(0xFF1F2937),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Box(
                                    modifier = Modifier
                                        .size(72.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF18191B)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = initials,
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = user.fullName,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111827)
                                )

                                Text(
                                    text = user.email,
                                    fontSize = 13.sp,
                                    color = Color(0xFF6B7280)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = onEditProfileClick,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    shape = RoundedCornerShape(24.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFF3F4F6),
                                        contentColor = Color(0xFF111827)
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(0.dp)
                                ) {
                                    Text(
                                        text = "Edit Profile",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Invite friends banner
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable(onClick = onInviteFriendsClick),
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
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF22C55E)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Invite",
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Invite friends",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF111827)
                                    )
                                    Text(
                                        text = "Get $5",
                                        fontSize = 13.sp,
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
                    }

                    // Section title
                    item {
                        Text(
                            text = "ACCOUNT & SETTINGS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp,
                            color = Color(0xFF6B7280),
                            modifier = Modifier.padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 20.dp,
                                bottom = 8.dp
                            )
                        )
                    }

                    // Menu items
                    items(menuItems) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable(onClick = item.onClick)
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = Color(0xFF1F2937),
                                modifier = Modifier.size(22.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF111827),
                                modifier = Modifier.weight(1f)
                            )

                            if (item.badge != null) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFDCFCE7))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = item.badge,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF16A34A)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color(0xFF9CA3AF)
                            )
                        }
                    }

                    // Sign Out button
                    item {
                        Spacer(modifier = Modifier.height(14.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable {
                                    viewModel.logout()
                                    onSignOutClick()
                                }
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sign Out",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEF4444)
                            )
                        }
                    }

                    // Legal & version footer
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Privacy Notice · Terms of Service · Open Source",
                                fontSize = 12.sp,
                                color = Color(0xFF6B7280),
                                textDecoration = TextDecoration.Underline
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Version 0.0.1",
                                fontSize = 12.sp,
                                color = Color(0xFF9CA3AF)
                            )
                        }
                    }
                }
            }
        }
    }
}