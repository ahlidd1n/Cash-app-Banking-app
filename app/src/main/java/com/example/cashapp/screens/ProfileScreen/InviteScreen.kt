package com.example.cashapp.screens.ProfileScreen


import android.content.Intent
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cashapp.screens.HomeScreens.CashAppSnackbarHost
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

private data class FriendInviteItem(
    val id: String,
    val name: String,
    val handle: String,
    val initials: String,
    val avatarBg: Color,
    val isInvited: Boolean = false
)

@Composable
fun InviteFriendsScreen(
    viewModel: UserViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val friends = remember {
        mutableStateListOf(
            FriendInviteItem(
                id = "1",
                name = "Abdulhafizoda Ahmad",
                handle = "@ahmad",
                initials = "AA",
                avatarBg = Color(0xFF18191B),
                isInvited = true
            ),
            FriendInviteItem(
                id = "2",
                name = "Rajabov Muhammad",
                handle = "@muhammad",
                initials = "RM",
                avatarBg = Color(0xFF15803D),
                isInvited = false
            ),
            FriendInviteItem(
                id = "3",
                name = "Yatimov Ahmad",
                handle = "@yatimov",
                initials = "YA",
                avatarBg = Color(0xFF6D28D9),
                isInvited = false
            )
        )
    }

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
    )

    fun shareInviteLink(code: String) {
        val shareText = "Join CashApp with my invite code $code and get $5!"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Invite a friend"))
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
                val cleanSlug = user.fullName
                    .trim()
                    .uppercase()
                    .replace(" ", "")
                    .take(6)
                    .ifBlank { "CASH" }
                val inviteCode = "$cleanSlug-5"
                val invitedCount = friends.count { it.isInvited }
                val earnedBonus = invitedCount * 5

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
                                text = "Invite Friends",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )

                            Spacer(modifier = Modifier.size(44.dp))
                        }
                    }

                    // Bonus Hero Card
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
                                                imageVector = Icons.Outlined.CardGiftcard,
                                                contentDescription = null,
                                                tint = Color(0xFF15803D),
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = "REFERRAL REWARD",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 0.8.sp,
                                                color = Color(0xFF16A34A)
                                            )
                                            Text(
                                                text = "Invite friends, get $5",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = Color(0xFF111827)
                                            )
                                            Surface(
                                                shape = RoundedCornerShape(10.dp),
                                                color = Color(0xFFF0FDF4)
                                            ) {
                                                Text(
                                                    text = "$$earnedBonus earned",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF15803D),
                                                    modifier = Modifier.padding(
                                                        horizontal = 10.dp,
                                                        vertical = 5.dp
                                                    )
                                                )
                                            }
                                        }
                                    }

                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "Share your invite code with friends. When they sign up and complete their first step, you earn $5.",
                                    fontSize = 13.sp,
                                    color = Color(0xFF6B7280)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Invite Code Box
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFF9FAFB)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 14.dp, vertical = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "YOUR INVITE CODE",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 0.7.sp,
                                                color = Color(0xFF9CA3AF)
                                            )
                                            Text(
                                                text = inviteCode,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = Color(0xFF111827)
                                            )
                                        }

                                        Surface(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .clickable {
                                                    clipboardManager.setText(
                                                        AnnotatedString(inviteCode)
                                                    )
                                                    scope.launch {
                                                        snackBarHostState.showSnackbar(
                                                            "Copied code $inviteCode"
                                                        )
                                                    }
                                                },
                                            shape = RoundedCornerShape(12.dp),
                                            color = Color(0xFFDCFCE7)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(
                                                    horizontal = 12.dp,
                                                    vertical = 8.dp
                                                ),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Outlined.ContentCopy,
                                                    contentDescription = "Copy code",
                                                    tint = Color(0xFF15803D),
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "Copy",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF15803D)
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Button(
                                    onClick = { shareInviteLink(inviteCode) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF141714),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Share,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Share Invite Link",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Suggested Contacts Section
                    item {
                        Text(
                            text = "SUGGESTED CONTACTS ($invitedCount INVITED)",
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
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                friends.forEachIndexed { index, friend ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape)
                                                .background(friend.avatarBg),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = friend.initials,
                                                color = Color.White,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(12.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = friend.name,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF111827)
                                            )
                                            Text(
                                                text = friend.handle,
                                                fontSize = 12.sp,
                                                color = Color(0xFF6B7280)
                                            )
                                        }

                                        if (friend.isInvited) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 8.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Outlined.CheckCircle,
                                                    contentDescription = null,
                                                    tint = Color(0xFF16A34A),
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "Invited",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF15803D)
                                                )
                                            }
                                        } else {
                                            Surface(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(12.dp))
                                                    .clickable {
                                                        friends[index] = friend.copy(isInvited = true)
                                                        scope.launch {
                                                            snackBarHostState.showSnackbar(
                                                                "Invite sent to ${friend.name}"
                                                            )
                                                        }
                                                    },
                                                shape = RoundedCornerShape(12.dp),
                                                color = Color(0xFF00D45A)
                                            ) {
                                                Text(
                                                    text = "Invite",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(
                                                        horizontal = 14.dp,
                                                        vertical = 7.dp
                                                    )
                                                )
                                            }
                                        }
                                    }

                                    if (index < friends.lastIndex) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            color = Color(0xFFF3F4F6)
                                        )
                                    }
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