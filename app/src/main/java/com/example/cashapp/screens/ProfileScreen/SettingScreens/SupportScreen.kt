package com.example.cashapp.screens.ProfileScreen.SettingScreens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashapp.screens.HomeScreens.CashAppSnackbarHost
import kotlinx.coroutines.launch

private data class SupportTopicCard(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color
)

private data class FaqItem(
    val question: String,
    val answer: String
)

@Composable
fun SupportScreen(
    onBackClick: () -> Unit = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var expandedIndex by remember { mutableStateOf<Int?>(0) }

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
    )

    val quickTopics = listOf(
        SupportTopicCard(
            title = "Balance & Transfers",
            subtitle = "Add cash, savings & limits",
            icon = Icons.Outlined.AccountBalanceWallet,
            iconBg = Color(0xFFDCFCE7),
            iconTint = Color(0xFF15803D)
        ),
        SupportTopicCard(
            title = "Card & Security",
            subtitle = "Lock card, PIN & privacy",
            icon = Icons.Outlined.CreditCard,
            iconBg = Color(0xFFEDE9FE),
            iconTint = Color(0xFF6D28D9)
        ),
        SupportTopicCard(
            title = "Account Protection",
            subtitle = "Login, verification & alerts",
            icon = Icons.Outlined.Shield,
            iconBg = Color(0xFFE0F2FE),
            iconTint = Color(0xFF0284C7)
        )
    )

    val allFaqs = remember {
        listOf(
            FaqItem(
                question = "How do I add cash to my balance?",
                answer = "Go to Home or Money, tap Add Cash, choose a quick amount or enter a custom amount, and confirm. Your balance updates right away."
            ),
            FaqItem(
                question = "How does Savings work?",
                answer = "Money moved to Savings is separated from your spending balance and stays available whenever you need it."
            ),
            FaqItem(
                question = "What should I do if my card is lost or stolen?",
                answer = "Open the Card tab immediately and tap Lock card to block spending, or tap Hide details to protect your card number."
            ),
            FaqItem(
                question = "Why didn't my balance update after adding money?",
                answer = "Check your internet connection and pull or reload your account data so changes sync cleanly with the server."
            )
        )
    }

    val filteredFaqs = remember(searchQuery, allFaqs) {
        if (searchQuery.isBlank()) {
            allFaqs
        } else {
            allFaqs.filter {
                it.question.contains(searchQuery, ignoreCase = true) ||
                        it.answer.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 28.dp),
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
                        text = "Support",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )

                    Spacer(modifier = Modifier.size(44.dp))
                }
            }

            // Hero Card + Search
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = "HELP CENTER",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.9.sp,
                            color = Color(0xFF16A34A)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "How can we help you?",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF111827)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Find answers about your balance, savings, and card.",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Search help topics...",
                                    fontSize = 14.sp,
                                    color = Color(0xFF9CA3AF)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Search",
                                    tint = Color(0xFF6B7280)
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF9FAFB),
                                unfocusedContainerColor = Color(0xFFF9FAFB),
                                focusedBorderColor = Color(0xFF16A34A),
                                unfocusedBorderColor = Color(0xFFE5E7EB),
                                focusedTextColor = Color(0xFF111827),
                                unfocusedTextColor = Color(0xFF111827)
                            )
                        )
                    }
                }
            }

            // Quick Topic Cards
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "POPULAR TOPICS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = Color(0xFF6B7280),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    quickTopics.forEach { topic ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    searchQuery = topic.title.substringBefore(" ")
                                },
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
                                        .clip(RoundedCornerShape(13.dp))
                                        .background(topic.iconBg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = topic.icon,
                                        contentDescription = topic.title,
                                        tint = topic.iconTint,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = topic.title,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF111827)
                                    )
                                    Text(
                                        text = topic.subtitle,
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
                    }
                }
            }

            // FAQ Section
            item {
                Text(
                    text = "FREQUENTLY ASKED QUESTIONS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }

            itemsIndexed(filteredFaqs) { index, faq ->
                val isExpanded = expandedIndex == index

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            expandedIndex = if (isExpanded) null else index
                        },
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
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
                                text = faq.question,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = if (isExpanded) {
                                    Icons.Outlined.ExpandLess
                                } else {
                                    Icons.Outlined.ExpandMore
                                },
                                contentDescription = null,
                                tint = Color(0xFF6B7280)
                            )
                        }

                        AnimatedVisibility(visible = isExpanded) {
                            Column {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    color = Color(0xFFF3F4F6)
                                )
                                Text(
                                    text = faq.answer,
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp,
                                    color = Color(0xFF4B5563)
                                )
                            }
                        }
                    }
                }
            }

            // Contact Support Card
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = "Still need help?",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Our support team typically replies within a few minutes.",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    snackBarHostState.showSnackbar("Live chat request sent to support")
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Chat with Support",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    scope.launch {
                                        snackBarHostState.showSnackbar("Email: support@cashapp.example")
                                    }
                                },
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFF3F4F6)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Email,
                                    contentDescription = null,
                                    tint = Color(0xFF111827),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Email Support",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF111827)
                                )
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