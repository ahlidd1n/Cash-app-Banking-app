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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Groups
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashapp.screens.HomeScreens.CashAppSnackbarHost
import kotlinx.coroutines.launch
import java.util.Locale

private data class FamilyMember(
    val id: String,
    val name: String,
    val role: String,
    val initials: String,
    val monthlyLimit: Double,
    val spentThisMonth: Double,
    val isActive: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyScreen(
    onBackClick: () -> Unit = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val members = remember {
        mutableStateListOf(
            FamilyMember(
                id = "1",
                name = "Abdulhafizzoda Ahmad",
                role = "Partner · Shared budget",
                initials = "AR",
                monthlyLimit = 500.0,
                spentThisMonth = 142.50,
                isActive = true
            ),
            FamilyMember(
                id = "2",
                name = "Rajabov Muhammad",
                role = "Teen · Allowance card",
                initials = "K",
                monthlyLimit = 120.0,
                spentThisMonth = 38.00,
                isActive = true
            )
        )
    }

    var editingMember by remember { mutableStateOf<FamilyMember?>(null) }
    var showAddMemberSheet by remember { mutableStateOf(false) }

    val editSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val addSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
    )

    val totalFamilyLimit = members.filter { it.isActive }.sumOf { it.monthlyLimit }

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
                        text = "Family",
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
                        IconButton(onClick = { showAddMemberSheet = true }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add member",
                                tint = Color(0xFF111827)
                            )
                        }
                    }
                }
            }

            // Family Summary Card
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
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(13.dp))
                                        .background(Color(0xFFDCFCE7)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Groups,
                                        contentDescription = null,
                                        tint = Color(0xFF15803D),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "FAMILY PLAN",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp,
                                        color = Color(0xFF16A34A)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xFFF3F4F6)
                                    ) {
                                        Text(
                                            text = "$${String.format(Locale.US, "%.0f", totalFamilyLimit)}/mo",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF111827),
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                    Text(
                                        text = "${members.size} members connected",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF111827)
                                    )
                                }
                            }


                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Set individual spending limits, pause access anytime, and keep family money organized.",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { showAddMemberSheet = true },
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
                                text = "Add Family Member",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Members List Card
            item {
                Text(
                    text = "MEMBERS",
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
                        members.forEachIndexed { index, member ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { editingMember = member }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (member.isActive) Color(0xFF18191B)
                                            else Color(0xFFD1D5DB)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = member.initials,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = member.name,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF111827)
                                    )
                                    Text(
                                        text = member.role,
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Spent $${String.format(Locale.US, "%.2f", member.spentThisMonth)} of $${String.format(Locale.US, "%.0f", member.monthlyLimit)}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (member.isActive) Color(0xFF15803D) else Color(0xFF9CA3AF)
                                    )
                                }

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF)
                                )
                            }

                            if (index < members.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFF3F4F6)
                                )
                            }
                        }
                    }
                }
            }

            // Protection Note
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
                            text = "You stay in control of monthly caps and can pause any member card in one tap.",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }
        }

        // Edit Member Sheet
        val currentEdit = editingMember
        if (currentEdit != null) {
            var limitInput by remember(currentEdit.id) {
                mutableStateOf(currentEdit.monthlyLimit.toInt().toString())
            }
            var activeState by remember(currentEdit.id) {
                mutableStateOf(currentEdit.isActive)
            }

            ModalBottomSheet(
                onDismissRequest = { editingMember = null },
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
                        text = currentEdit.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = currentEdit.role,
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Card active",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF111827)
                        )
                        Switch(
                            checked = activeState,
                            onCheckedChange = { activeState = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF22C55E)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = limitInput,
                        onValueChange = { value ->
                            if (value.isEmpty() || value.all { it.isDigit() }) {
                                limitInput = value
                            }
                        },
                        label = { Text("Monthly limit ($)") },
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
                            val newLimit = limitInput.toDoubleOrNull()?.coerceAtLeast(10.0)
                                ?: currentEdit.monthlyLimit
                            val idx = members.indexOfFirst { it.id == currentEdit.id }
                            if (idx != -1) {
                                members[idx] = currentEdit.copy(
                                    monthlyLimit = newLimit,
                                    isActive = activeState
                                )
                            }
                            editingMember = null
                            scope.launch {
                                snackBarHostState.showSnackbar("Updated ${currentEdit.name}'s settings")
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

        // Add Member Sheet
        if (showAddMemberSheet) {
            var newName by remember { mutableStateOf("") }
            var newRole by remember { mutableStateOf("Family member") }
            var newLimit by remember { mutableStateOf("150") }

            ModalBottomSheet(
                onDismissRequest = { showAddMemberSheet = false },
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
                        text = "Add Family Member",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "Invite a family member with a monthly spending cap.",
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
                        value = newRole,
                        onValueChange = { newRole = it },
                        label = { Text("Role or note") },
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
                        value = newLimit,
                        onValueChange = { value ->
                            if (value.isEmpty() || value.all { it.isDigit() }) {
                                newLimit = value
                            }
                        },
                        label = { Text("Monthly limit ($)") },
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
                            val trimmedName = newName.trim()
                            if (trimmedName.isBlank()) {
                                scope.launch {
                                    snackBarHostState.showSnackbar("Enter a valid name")
                                }
                                return@Button
                            }
                            val derivedInitials = trimmedName
                                .split(" ")
                                .filter { it.isNotBlank() }
                                .take(2)
                                .joinToString("") { it.first().uppercase() }
                                .ifBlank { "F" }

                            val parsedLimit = newLimit.toDoubleOrNull()?.coerceAtLeast(10.0) ?: 150.0
                            members.add(
                                FamilyMember(
                                    id = (members.size + 1).toString() + trimmedName.hashCode(),
                                    name = trimmedName,
                                    role = newRole.trim().ifBlank { "Family member" },
                                    initials = derivedInitials,
                                    monthlyLimit = parsedLimit,
                                    spentThisMonth = 0.0,
                                    isActive = true
                                )
                            )
                            showAddMemberSheet = false
                            scope.launch {
                                snackBarHostState.showSnackbar("Added $trimmedName to Family")
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
                            text = "Add Member",
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