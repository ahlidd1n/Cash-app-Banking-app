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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Verified
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalScreen(
    viewModel: UserViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showEditSheet by remember { mutableStateOf(false) }
    val editSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var customName by remember { mutableStateOf<String?>(null) }
    var customEmail by remember { mutableStateOf<String?>(null) }
    var customPhone by remember { mutableStateOf<String?>(null) }

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
                val displayName = customName ?: user.fullName
                val displayEmail = customEmail ?: user.email
                val displayPhone = customPhone ?: user.phone.ifBlank { "+992 90 000 0000" }

                val initials = displayName
                    .trim()
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .take(2)
                    .joinToString("") { it.first().uppercase() }
                    .ifBlank { "U" }

                val cashtag = "$" + displayName
                    .trim()
                    .lowercase()
                    .replace(" ", "")
                    .ifBlank { "user" }

                val lastFour = user.cardNumber.toString().takeLast(4).padStart(4, '0')

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
                                text = "Personal",
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
                                IconButton(onClick = { showEditSheet = true }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = "Edit profile",
                                        tint = Color(0xFF111827)
                                    )
                                }
                            }
                        }
                    }

                    // Profile Header Card
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            shadowElevation = 2.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(76.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF18191B)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = initials,
                                        color = Color.White,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = displayName,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF111827)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFFF0FDF4)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Verified,
                                            contentDescription = null,
                                            tint = Color(0xFF16A34A),
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text(
                                            text = "Verified account",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF15803D)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = { showEditSheet = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(46.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFF3F4F6),
                                        contentColor = Color(0xFF111827)
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(0.dp)
                                ) {
                                    Text(
                                        text = "Edit Personal Info",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Personal Details Section
                    item {
                        Text(
                            text = "IDENTITY & CONTACT",
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
                                PersonalInfoRow(
                                    icon = Icons.Outlined.Badge,
                                    iconBg = Color(0xFFDCFCE7),
                                    iconTint = Color(0xFF15803D),
                                    label = "Full legal name",
                                    value = displayName,
                                    onClick = { showEditSheet = true }
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFF3F4F6)
                                )

                                PersonalInfoRow(
                                    icon = Icons.Outlined.AlternateEmail,
                                    iconBg = Color(0xFFEDE9FE),
                                    iconTint = Color(0xFF6D28D9),
                                    label = "Cashtag",
                                    value = cashtag,
                                    onClick = {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Copied $cashtag")
                                        }
                                    }
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFF3F4F6)
                                )

                                PersonalInfoRow(
                                    icon = Icons.Outlined.Email,
                                    iconBg = Color(0xFFE0F2FE),
                                    iconTint = Color(0xFF0284C7),
                                    label = "Email address",
                                    value = displayEmail,
                                    onClick = { showEditSheet = true }
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFF3F4F6)
                                )

                                PersonalInfoRow(
                                    icon = Icons.Outlined.Phone,
                                    iconBg = Color(0xFFFEF3C7),
                                    iconTint = Color(0xFFB45309),
                                    label = "Phone number",
                                    value = displayPhone,
                                    onClick = { showEditSheet = true }
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFF3F4F6)
                                )

                                PersonalInfoRow(
                                    icon = Icons.Outlined.CreditCard,
                                    iconBg = Color(0xFFF3F4F6),
                                    iconTint = Color(0xFF1F2937),
                                    label = "Cash Card number",
                                    value = "•••• •••• •••• $lastFour",
                                    onClick = {}
                                )
                            }
                        }
                    }
                }

                // Edit Personal Info Sheet
                if (showEditSheet) {
                    var nameInput by remember(displayName) { mutableStateOf(displayName) }
                    var emailInput by remember(displayEmail) { mutableStateOf(displayEmail) }
                    var phoneInput by remember(displayPhone) { mutableStateOf(displayPhone) }

                    ModalBottomSheet(
                        onDismissRequest = { showEditSheet = false },
                        sheetState = editSheetState,
                        containerColor = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Edit Personal Info",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827)
                            )
                            Text(
                                text = "Keep your contact details up to date.",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = nameInput,
                                onValueChange = { nameInput = it },
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
                                value = emailInput,
                                onValueChange = { emailInput = it },
                                label = { Text("Email") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF16A34A),
                                    unfocusedBorderColor = Color(0xFFE5E7EB)
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = phoneInput,
                                onValueChange = { phoneInput = it },
                                label = { Text("Phone number") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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
                                    val cleanName = nameInput.trim()
                                    val cleanEmail = emailInput.trim()
                                    val cleanPhone = phoneInput.trim()

                                    if (cleanName.isBlank() || !cleanEmail.contains("@")) {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Enter a valid name and email")
                                        }
                                        return@Button
                                    }

                                    customName = cleanName
                                    customEmail = cleanEmail
                                    customPhone = cleanPhone.ifBlank { displayPhone }
                                    showEditSheet = false
                                    scope.launch {
                                        snackBarHostState.showSnackbar("Personal info updated")
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
                                    text = "Save Changes",
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

@Composable
private fun PersonalInfoRow(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    label: String,
    value: String,
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
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF6B7280)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }
    }
}