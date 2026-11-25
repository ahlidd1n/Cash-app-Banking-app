package com.example.cashapp.screens.HomeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigation: () -> Unit = {},
    viewModel: UserViewModel = viewModel(),
    navigateToAddCashScreen: () -> Unit = {},
    navigateToSavingScreen: () -> Unit = {},
    navigateToProfileScreen: () -> Unit = {},
    onBitcoinClick: () -> Unit = {},
    onStocksClick: () -> Unit = {},
    onTaxClick: () -> Unit = {},
    onCreditClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    var bottomSheet by remember { mutableStateOf(false) }
    val snackBar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
    )

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            CashAppSnackbarHost(
                hostState = snackBar,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgGradient)
                .padding(paddingValues)
        ) {
            when (val currentState = state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        color = Color(0xFF16A34A),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UiState.Error -> {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        shape = RoundedCornerShape(18.dp),
                        color = Color.White,
                        shadowElevation = 2.dp
                    ) {
                        Text(
                            text = "Error: ${currentState.errorMassage}",
                            color = Color(0xFFB91C1C),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        // Top Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CASH APP",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp,
                                    color = Color(0xFF6B7280)
                                )
                                Text(
                                    text = user.fullName,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF111827),
                                    letterSpacing = (-0.5).sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF18191B))
                                    .clickable(onClick = navigateToProfileScreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = initials,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Cash Balance Card
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            shadowElevation = 2.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Cash Balance",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF111827)
                                    )
                                    Text(
                                        text = "Account & Routing",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF6B7280)
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", user.balance)}",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF111827),
                                    letterSpacing = (-0.8).sp
                                )

                                Spacer(modifier = Modifier.height(22.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    BalanceActionPill(
                                        title = "Add Cash",
                                        icon = Icons.Outlined.Add,
                                        onClick = { bottomSheet = true },
                                        modifier = Modifier.weight(1f)
                                    )
                                    BalanceActionPill(
                                        title = "Cash Out",
                                        icon = Icons.Outlined.ArrowUpward,
                                        onClick = {
                                            scope.launch {
                                                snackBar.showSnackbar("Cash Out coming soon")
                                            }
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Row 1: Savings & Buy Bitcoin
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            FeatureGridCard(
                                title = "Savings",
                                valueText = "$${String.format(Locale.US, "%.2f", user.savings)}",
                                subtitle = "Save for a goal",
                                imageUrl = "https://github.com/user-attachments/assets/9f406684-7c98-43f3-87d9-5e56f4f36435",
                                imageSize = 76,
                                onClick = navigateToSavingScreen,
                                modifier = Modifier.weight(1f)
                            )

                            FeatureGridCard(
                                title = "Buy bitcoin",
                                valueText = null,
                                subtitle = "Invest anytime",
                                imageUrl = "https://github.com/user-attachments/assets/ac131a79-a6c7-4e53-b0b5-984a629c8cb6",
                                imageSize = 96,
                                onClick = onBitcoinClick,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Row 2: Invest in stocks & Free tax filing
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            FeatureGridCard(
                                title = "Invest in stocks",
                                valueText = null,
                                subtitle = "Top companies",
                                imageUrl = "https://github.com/user-attachments/assets/dd1a83e4-7b39-4fcc-a1d2-db727ce7408a",
                                imageSize = 96,
                                onClick = onStocksClick,
                                modifier = Modifier.weight(1f)
                            )

                            FeatureGridCard(
                                title = "Free tax filing",
                                valueText = null,
                                subtitle = "Simple & fast",
                                imageUrl = "https://github.com/user-attachments/assets/46882ab5-bfd1-425c-850a-6e0199286f62",
                                imageSize = 90,
                                onClick = onTaxClick,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Credit Card Banner
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(24.dp))
                                .clickable(onClick = onCreditClick),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            shadowElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Build your credit",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF111827)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Track and grow your score",
                                        fontSize = 13.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                AsyncImage(
                                    model = "https://github.com/user-attachments/assets/9c2e2aa0-1280-41dd-82c7-48e95e924d86",
                                    contentDescription = "Build your credit",
                                    modifier = Modifier.size(92.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    // Add Cash Bottom Sheet
                    if (bottomSheet) {
                        val numbers = listOf("$1", "$10", "$20", "$50", "$100", "...")
                        var selectedNumber by remember { mutableStateOf<String?>(null) }
                        var addCash by rememberSaveable { mutableStateOf(0.00) }

                        ModalBottomSheet(
                            onDismissRequest = { bottomSheet = false },
                            containerColor = Color.White,
                            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Add Cash",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF111827)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", addCash)}",
                                    fontSize = 38.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF00D45A)
                                )

                                FlowRow(
                                    maxItemsInEachRow = 3,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(14.dp),
                                    modifier = Modifier.padding(top = 22.dp)
                                ) {
                                    numbers.forEach { number ->
                                        val isSelected = selectedNumber == number
                                        Box(
                                            modifier = Modifier
                                                .size(width = 74.dp, height = 52.dp)
                                                .clip(RoundedCornerShape(15.dp))
                                                .background(
                                                    color = if (isSelected) Color(0xFF00D45A)
                                                    else Color(0xFFF3F4F6)
                                                )
                                                .clickable {
                                                    if (number == "...") {
                                                        bottomSheet = false
                                                        navigateToAddCashScreen()
                                                    } else {
                                                        selectedNumber = number
                                                        addCash = number.removePrefix("$").toDouble()
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = number,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) Color.White else Color(0xFF111827)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(28.dp))

                                Button(
                                    onClick = {
                                        if (addCash == 0.00) {
                                            scope.launch {
                                                snackBar.showSnackbar("Select some amount !")
                                            }
                                        } else {
                                            viewModel.addBalance(addCash)
                                            addCash = 0.00
                                            bottomSheet = false
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF00D45A),
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(18.dp)
                                ) {
                                    Text(
                                        text = "Add",
                                        fontSize = 16.sp,
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
}

@Composable
private fun BalanceActionPill(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(46.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(15.dp),
        color = Color(0xFFF3F4F6)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF111827),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }
    }
}

@Composable
private fun FeatureGridCard(
    title: String,
    valueText: String?,
    subtitle: String,
    imageUrl: String,
    imageSize: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(205.dp)
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFF9CA3AF),
                    modifier = Modifier.size(20.dp)
                )
            }

            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier.size(imageSize.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (valueText != null) {
                    Text(
                        text = valueText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF6B7280)
                )
            }
        }
    }
}