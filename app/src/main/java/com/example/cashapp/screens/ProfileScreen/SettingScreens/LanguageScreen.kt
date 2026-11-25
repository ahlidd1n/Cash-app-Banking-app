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
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashapp.screens.HomeScreens.CashAppSnackbarHost
import kotlinx.coroutines.launch

private data class AppLanguageOption(
    val code: String,
    val name: String,
    val nativeName: String,
    val badgeText: String
)

@Composable
fun LanguageScreen(
    initialLanguageCode: String = "en",
    onLanguageSaved: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCode by remember { mutableStateOf(initialLanguageCode) }
    var activeCode by remember { mutableStateOf(initialLanguageCode) }

    val languages = remember {
        listOf(
            AppLanguageOption("en", "English (US)", "English", "EN"),
            AppLanguageOption("tg", "Tajik", "Тоҷикӣ", "TJ"),
            AppLanguageOption("ru", "Russian", "Русский", "RU"),
            AppLanguageOption("uz", "Uzbek", "Oʻzbekcha", "UZ"),
            AppLanguageOption("es", "Spanish", "Español", "ES"),
            AppLanguageOption("de", "German", "Deutsch", "DE"),
            AppLanguageOption("fr", "French", "Français", "FR"),
            AppLanguageOption("tr", "Turkish", "Türkçe", "TR")
        )
    }

    val filteredLanguages = remember(searchQuery, languages) {
        if (searchQuery.isBlank()) {
            languages
        } else {
            languages.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.nativeName.contains(searchQuery, ignoreCase = true) ||
                        it.code.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val activeLanguage = languages.find { it.code == activeCode } ?: languages.first()
    val hasPendingChange = selectedCode != activeCode

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
                        text = "Language",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )

                    Spacer(modifier = Modifier.size(44.dp))
                }
            }

            // Current Language & Search Card
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
                                        imageVector = Icons.Outlined.Language,
                                        contentDescription = null,
                                        tint = Color(0xFF15803D),
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "APP LANGUAGE",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp,
                                        color = Color(0xFF16A34A)
                                    )
                                    Text(
                                        text = activeLanguage.name,
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
                                    text = activeLanguage.badgeText,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Choose the language used across menus, alerts, and account screens.",
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
                                    text = "Search language...",
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

            // Section Title
            item {
                Text(
                    text = "AVAILABLE LANGUAGES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            // Language Options List
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    if (filteredLanguages.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No matching languages found",
                                fontSize = 14.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    } else {
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            filteredLanguages.forEachIndexed { index, language ->
                                val isSelected = selectedCode == language.code

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedCode = language.code }
                                        .padding(horizontal = 16.dp, vertical = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                if (isSelected) Color(0xFFDCFCE7)
                                                else Color(0xFFF3F4F6)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = language.badgeText,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (isSelected) Color(0xFF15803D)
                                            else Color(0xFF374151)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = language.name,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF111827)
                                        )
                                        Text(
                                            text = language.nativeName,
                                            fontSize = 12.sp,
                                            color = Color(0xFF6B7280)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) Color(0xFF00C244)
                                                else Color.Transparent
                                            )
                                            .border(
                                                width = if (isSelected) 0.dp else 2.dp,
                                                color = if (isSelected) Color(0xFF00C244)
                                                else Color(0xFFD1D5DB),
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected",
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }

                                if (index < filteredLanguages.lastIndex) {
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

            // Save Button
            item {
                Button(
                    onClick = {
                        activeCode = selectedCode
                        val chosen = languages.find { it.code == selectedCode }
                        onLanguageSaved(selectedCode)
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                "Language changed to ${chosen?.name ?: selectedCode}"
                            )
                        }
                    },
                    enabled = hasPendingChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF141714),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFE5E7EB),
                        disabledContentColor = Color(0xFF9CA3AF)
                    )
                ) {
                    Text(
                        text = if (hasPendingChange) "Apply Language" else "Current Language",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        CashAppSnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}