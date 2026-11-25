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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.util.Locale

private data class FavoriteContact(
    val id: String,
    val name: String,
    val handle: String,
    val initials: String,
    val avatarBg: Color,
    val isPinned: Boolean = true
)

private data class FavoriteShortcut(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color,
    val routeKey: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: UserViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onNavigateToSavings: () -> Unit = {},
    onNavigateToCard: () -> Unit = {},
    onNavigateToLimits: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var selectedRecipient by remember { mutableStateOf<FavoriteContact?>(null) }
    var showAddFavoriteSheet by remember { mutableStateOf(false) }

    val sendSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val addSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val favorites = remember {
        mutableStateListOf(
            FavoriteContact(
                id = "1",
                name = "Abdulhafizoda Ahmad",
                handle = "@ahmad.a",
                initials = "AA",
                avatarBg = Color(0xFF18191B),
                isPinned = true
            ),
            FavoriteContact(
                id = "2",
                name = "Rajabov Muhammad",
                handle = "@muhammad",
                initials = "RM",
                avatarBg = Color(0xFF15803D),
                isPinned = true
            )
        )
    }

    val shortcuts = remember {
        listOf(
            FavoriteShortcut(
                title = "Savings Goal",
                subtitle = "Quick transfer to rainy day fund",
                icon = Icons.Outlined.AccountBalanceWallet,
                iconBg = Color(0xFFDCFCE7),
                iconTint = Color(0xFF15803D),
                routeKey = "savings"
            ),
            FavoriteShortcut(
                title = "Cash Card Controls",
                subtitle = "Lock card or hide card details",
                icon = Icons.Outlined.CreditCard,
                iconBg = Color(0xFFEDE9FE),
                iconTint = Color(0xFF6D28D9),
                routeKey = "card"
            ),
            FavoriteShortcut(
                title = "Spending Limits",
                subtitle = "Adjust monthly & daily caps",
                icon = Icons.Outlined.Shield,
                iconBg = Color(0xFFE0F2FE),
                iconTint = Color(0xFF0284C7),
                routeKey = "limits"
            )
        )
    }

    val filteredFavorites = remember(searchQuery, favorites.toList()) {
        if (searchQuery.isBlank()) {
            favorites.toList()
        } else {
            favorites.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.handle.contains(searchQuery, ignoreCase = true)
            }
        }
    }

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
                                text = "Favorites",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )

                            Surface(
                                modifier = Modifier.size(44.dp),
                                shape = CircleShape,
                                color = Color.White,
                                shadowElevation = 2.dp
                            ) {
                                IconButton(onClick = { showAddFavoriteSheet = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add favorite",
                                        tint = Color(0xFF111827)
                                    )
                                }
                            }
                        }
                    }

                    // Search & Balance Card
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
                                        text = "QUICK SEND & SHORTCUTS",
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
                                            text = "Balance: $${String.format(Locale.US, "%.2f", user.balance)}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF15803D),
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Your favorites",
                                    fontSize = 21.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF111827)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    placeholder = {
                                        Text(
                                            text = "Search by name or @handle...",
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

                    // Favorite Contacts Section
                    item {
                        Text(
                            text = "FAVORITE PEOPLE",
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
                            if (filteredFavorites.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No matching favorites found",
                                        fontSize = 14.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }
                            } else {
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    filteredFavorites.forEachIndexed { index, contact ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { selectedRecipient = contact }
                                                .padding(horizontal = 16.dp, vertical = 12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(44.dp)
                                                    .clip(CircleShape)
                                                    .background(contact.avatarBg),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = contact.initials,
                                                    color = Color.White,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(12.dp))

                                            Column(modifier = Modifier.weight(1f)) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(
                                                        text = contact.name,
                                                        fontSize = 15.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(0xFF111827)
                                                    )
                                                    if (contact.isPinned) {
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Icon(
                                                            imageVector = Icons.Filled.Star,
                                                            contentDescription = "Pinned",
                                                            tint = Color(0xFFF59E0B),
                                                            modifier = Modifier.size(15.dp)
                                                        )
                                                    }
                                                }
                                                Text(
                                                    text = contact.handle,
                                                    fontSize = 12.sp,
                                                    color = Color(0xFF6B7280)
                                                )
                                            }

                                            Surface(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(12.dp))
                                                    .clickable { selectedRecipient = contact },
                                                shape = RoundedCornerShape(12.dp),
                                                color = Color(0xFFDCFCE7)
                                            ) {
                                                Text(
                                                    text = "Send",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF15803D),
                                                    modifier = Modifier.padding(
                                                        horizontal = 12.dp,
                                                        vertical = 6.dp
                                                    )
                                                )
                                            }
                                        }

                                        if (index < filteredFavorites.lastIndex) {
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

                    // Favorite Shortcuts Section
                    item {
                        Text(
                            text = "SAVED SHORTCUTS",
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
                                shortcuts.forEachIndexed { index, shortcut ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                when (shortcut.routeKey) {
                                                    "savings" -> onNavigateToSavings()
                                                    "card" -> onNavigateToCard()
                                                    "limits" -> onNavigateToLimits()
                                                }
                                            }
                                            .padding(horizontal = 16.dp, vertical = 14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(42.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(shortcut.iconBg),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = shortcut.icon,
                                                contentDescription = shortcut.title,
                                                tint = shortcut.iconTint,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(12.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = shortcut.title,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF111827)
                                            )
                                            Text(
                                                text = shortcut.subtitle,
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

                                    if (index < shortcuts.lastIndex) {
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

                // Send Money Sheet
                val recipient = selectedRecipient
                if (recipient != null) {
                    var amountInput by remember(recipient.id) { mutableStateOf("25") }

                    ModalBottomSheet(
                        onDismissRequest = { selectedRecipient = null },
                        sheetState = sendSheetState,
                        containerColor = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Send to ${recipient.name}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827)
                            )
                            Text(
                                text = "${recipient.handle} · Available: $${String.format(Locale.US, "%.2f", user.balance)}",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf("10", "25", "50", "100").forEach { preset ->
                                    val isSelected = amountInput == preset
                                    Surface(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable { amountInput = preset },
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (isSelected) Color(0xFFDCFCE7) else Color(0xFFF3F4F6)
                                    ) {
                                        Box(
                                            modifier = Modifier.padding(vertical = 9.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "$$preset",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) Color(0xFF14532D) else Color(0xFF374151)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = amountInput,
                                onValueChange = { raw ->
                                    if (raw.isEmpty() || raw.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                                        amountInput = raw
                                    }
                                },
                                label = { Text("Amount ($)") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                                    val amount = amountInput.toDoubleOrNull()
                                    if (amount == null || amount <= 0.0) {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Enter a valid amount")
                                        }
                                        return@Button
                                    }
                                    if (amount > user.balance) {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Insufficient balance")
                                        }
                                        return@Button
                                    }

                                    val updatedBalance = user.balance - amount
                                    viewModel.addBalance(updatedBalance)
                                    selectedRecipient = null
                                    scope.launch {
                                        snackBarHostState.showSnackbar(
                                            "Sent $${String.format(Locale.US, "%.2f", amount)} to ${recipient.name}"
                                        )
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
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Send Money",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }

                // Add Favorite Sheet
                if (showAddFavoriteSheet) {
                    var newName by remember { mutableStateOf("") }
                    var newHandle by remember { mutableStateOf("") }

                    ModalBottomSheet(
                        onDismissRequest = { showAddFavoriteSheet = false },
                        sheetState = addSheetState,
                        containerColor = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Add to Favorites",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827)
                            )
                            Text(
                                text = "Save a frequent contact for faster transfers.",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = newName,
                                onValueChange = { newName = it },
                                label = { Text("Full name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF16A34A),
                                    unfocusedBorderColor = Color(0xFFE5E7EB)
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = newHandle,
                                onValueChange = { newHandle = it },
                                label = { Text("Username (e.g. @username)") },
                                singleLine = true,
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
                                    val cleanName = newName.trim()
                                    if (cleanName.isBlank()) {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Enter a valid name")
                                        }
                                        return@Button
                                    }

                                    val cleanHandle = newHandle.trim().let { raw ->
                                        when {
                                            raw.isBlank() -> "@${cleanName.lowercase().replace(" ", ".")}"
                                            raw.startsWith("@") -> raw
                                            else -> "@$raw"
                                        }
                                    }

                                    val initials = cleanName
                                        .split(" ")
                                        .filter { it.isNotBlank() }
                                        .take(2)
                                        .joinToString("") { it.first().uppercase() }
                                        .ifBlank { "F" }

                                    favorites.add(
                                        0,
                                        FavoriteContact(
                                            id = (favorites.size + 1).toString() + cleanName.hashCode(),
                                            name = cleanName,
                                            handle = cleanHandle,
                                            initials = initials,
                                            avatarBg = Color(0xFF16A34A),
                                            isPinned = true
                                        )
                                    )
                                    showAddFavoriteSheet = false
                                    scope.launch {
                                        snackBarHostState.showSnackbar("Added $cleanName to Favorites")
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
                                    text = "Save Favorite",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}