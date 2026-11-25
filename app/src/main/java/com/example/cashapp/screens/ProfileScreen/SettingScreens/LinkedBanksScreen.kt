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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Shield
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
import com.example.cashapp.screens.HomeScreens.CashAppSnackbarHost
import kotlinx.coroutines.launch

private data class LinkedBankAccount(
    val id: String,
    val bankName: String,
    val accountType: String,
    val lastFour: String,
    val isPrimary: Boolean,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkedBanksScreen(
    onBackClick: () -> Unit = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val banks = remember {
        mutableStateListOf(
            LinkedBankAccount(
                id = "1",
                bankName = "Amonat Bank",
                accountType = "Checking · Instant transfer",
                lastFour = "4821",
                isPrimary = true,
                icon = Icons.Outlined.AccountBalance,
                iconBg = Color(0xFFDCFCE7),
                iconTint = Color(0xFF15803D)
            ),
            LinkedBankAccount(
                id = "2",
                bankName = "Visa Debit Card",
                accountType = "Debit card",
                lastFour = "2048",
                isPrimary = false,
                icon = Icons.Outlined.CreditCard,
                iconBg = Color(0xFFEDE9FE),
                iconTint = Color(0xFF6D28D9)
            )
        )
    }

    var selectedBank by remember { mutableStateOf<LinkedBankAccount?>(null) }
    var showAddBankSheet by remember { mutableStateOf(false) }

    val manageSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val addSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
                        text = "Linked Banks",
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
                        IconButton(onClick = { showAddBankSheet = true }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Link bank",
                                tint = Color(0xFF111827)
                            )
                        }
                    }
                }
            }

            // Header Card
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "PAYMENT METHODS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp,
                            color = Color(0xFF16A34A)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Connected banks & cards",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF111827)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Use a linked bank account or debit card to add cash and withdraw funds.",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { showAddBankSheet = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF141714),
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Link Bank or Card",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Connected Accounts List
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        banks.forEachIndexed { index, bank ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedBank = bank }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(13.dp))
                                        .background(bank.iconBg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = bank.icon,
                                        contentDescription = bank.bankName,
                                        tint = bank.iconTint,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = bank.bankName,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF111827)
                                        )
                                        if (bank.isPrimary) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = Color(0xFFDCFCE7)
                                            ) {
                                                Text(
                                                    text = "Primary",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF15803D),
                                                    modifier = Modifier.padding(
                                                        horizontal = 7.dp,
                                                        vertical = 2.dp
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = "${bank.accountType} · •••• ${bank.lastFour}",
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

                            if (index < banks.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFF3F4F6)
                                )
                            }
                        }
                    }
                }
            }

            // Security Note
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
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFE0F2FE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = null,
                                tint = Color(0xFF0284C7),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Bank connections are protected and can be removed at any time.",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }
        }

        // Manage Selected Bank Sheet
        val currentBank = selectedBank
        if (currentBank != null) {
            ModalBottomSheet(
                onDismissRequest = { selectedBank = null },
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
                        text = currentBank.bankName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "${currentBank.accountType} · •••• ${currentBank.lastFour}",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            val updated = banks.map {
                                it.copy(isPrimary = it.id == currentBank.id)
                            }
                            banks.clear()
                            banks.addAll(updated)
                            val name = currentBank.bankName
                            selectedBank = null
                            scope.launch {
                                snackBarHostState.showSnackbar("$name set as primary")
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
                            text = "Set as Primary",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                val name = currentBank.bankName
                                banks.removeAll { it.id == currentBank.id }
                                selectedBank = null
                                scope.launch {
                                    snackBarHostState.showSnackbar("Removed $name")
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFFF5F5)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Remove Account",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFDC2626)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        // Add Bank / Card Sheet
        if (showAddBankSheet) {
            var bankName by remember { mutableStateOf("") }
            var lastFour by remember { mutableStateOf("") }

            ModalBottomSheet(
                onDismissRequest = { showAddBankSheet = false },
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
                        text = "Link Bank or Card",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "Enter a bank name and the last 4 digits of your account or card.",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = bankName,
                        onValueChange = { bankName = it },
                        label = { Text("Bank or card name") },
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
                        value = lastFour,
                        onValueChange = { digits ->
                            if (digits.length <= 4 && digits.all { it.isDigit() }) {
                                lastFour = digits
                            }
                        },
                        label = { Text("Last 4 digits") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            val cleanName = bankName.trim()
                            if (cleanName.isBlank() || lastFour.length != 4) {
                                scope.launch {
                                    snackBarHostState.showSnackbar("Enter valid bank name and 4 digits")
                                }
                                return@Button
                            }
                            banks.add(
                                LinkedBankAccount(
                                    id = (banks.size + 1).toString() + cleanName.hashCode(),
                                    bankName = cleanName,
                                    accountType = "Linked bank account",
                                    lastFour = lastFour,
                                    isPrimary = banks.isEmpty(),
                                    icon = Icons.Outlined.AccountBalance,
                                    iconBg = Color(0xFFE0F2FE),
                                    iconTint = Color(0xFF0284C7)
                                )
                            )
                            showAddBankSheet = false
                            scope.launch {
                                snackBarHostState.showSnackbar("Linked $cleanName")
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
                            text = "Link Account",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
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