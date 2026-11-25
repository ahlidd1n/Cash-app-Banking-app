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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashapp.screens.HomeScreens.CashAppSnackbarHost
import kotlinx.coroutines.launch

private enum class DocumentCategory(val label: String) {
    ALL("All"),
    STATEMENTS("Statements"),
    TAX_LEGAL("Tax & Legal")
}

private data class AccountDocument(
    val id: String,
    val title: String,
    val subtitle: String,
    val dateLabel: String,
    val sizeLabel: String,
    val category: DocumentCategory,
    val statusText: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentsScreen(
    onBackClick: () -> Unit = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedCategory by remember { mutableStateOf(DocumentCategory.ALL) }
    var selectedDocument by remember { mutableStateOf<AccountDocument?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val bgGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF2F6EC),
            Color(0xFFF6F6F4),
            Color(0xFFF2F2F2)
        )
    )

    val documents = remember {
        listOf(
            AccountDocument(
                id = "stmt_oct_2026",
                title = "Monthly Account Statement",
                subtitle = "October 2026 · Cash & Savings",
                dateLabel = "Nov 01, 2026",
                sizeLabel = "PDF · 248 KB",
                category = DocumentCategory.STATEMENTS,
                statusText = "Available",
                icon = Icons.Outlined.ReceiptLong,
                iconBg = Color(0xFFDCFCE7),
                iconTint = Color(0xFF15803D)
            ),
            AccountDocument(
                id = "stmt_sep_2026",
                title = "Monthly Account Statement",
                subtitle = "September 2026 · Cash & Savings",
                dateLabel = "Oct 01, 2026",
                sizeLabel = "PDF · 214 KB",
                category = DocumentCategory.STATEMENTS,
                statusText = "Available",
                icon = Icons.Outlined.Description,
                iconBg = Color(0xFFE0F2FE),
                iconTint = Color(0xFF0284C7)
            ),
            AccountDocument(
                id = "card_agreement",
                title = "Cardholder Agreement",
                subtitle = "Debit card terms & spending rules",
                dateLabel = "Updated Aug 2026",
                sizeLabel = "PDF · 410 KB",
                category = DocumentCategory.TAX_LEGAL,
                statusText = "Signed",
                icon = Icons.Outlined.Gavel,
                iconBg = Color(0xFFEDE9FE),
                iconTint = Color(0xFF6D28D9)
            ),
            AccountDocument(
                id = "privacy_consent",
                title = "Identity & Privacy Summary",
                subtitle = "Account verification & data notice",
                dateLabel = "Active",
                sizeLabel = "PDF · 162 KB",
                category = DocumentCategory.TAX_LEGAL,
                statusText = "Verified",
                icon = Icons.Outlined.VerifiedUser,
                iconBg = Color(0xFFFEF3C7),
                iconTint = Color(0xFFB45309)
            )
        )
    }

    val filteredDocuments = remember(selectedCategory, documents) {
        if (selectedCategory == DocumentCategory.ALL) {
            documents
        } else {
            documents.filter { it.category == selectedCategory }
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
                        text = "Documents",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )

                    Spacer(modifier = Modifier.size(44.dp))
                }
            }

            // Header Info Card
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
                            text = "RECORDS & STATEMENTS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.9.sp,
                            color = Color(0xFF16A34A)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Your account documents",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF111827)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Download monthly summaries, agreements, and account records anytime.",
                            fontSize = 13.sp,
                            color = Color(0xFF6B7280)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(DocumentCategory.entries) { category ->
                                val isSelected = selectedCategory == category
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable { selectedCategory = category },
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (isSelected) Color(0xFF141714) else Color(0xFFF3F4F6)
                                ) {
                                    Text(
                                        text = category.label,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSelected) Color.White else Color(0xFF374151),
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Documents List Card
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        filteredDocuments.forEachIndexed { index, doc ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedDocument = doc }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(13.dp))
                                        .background(doc.iconBg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = doc.icon,
                                        contentDescription = doc.title,
                                        tint = doc.iconTint,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = doc.title,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF111827)
                                    )
                                    Text(
                                        text = doc.subtitle,
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${doc.dateLabel} · ${doc.sizeLabel}",
                                        fontSize = 11.sp,
                                        color = Color(0xFF9CA3AF)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color(0xFF9CA3AF)
                                )
                            }

                            if (index < filteredDocuments.lastIndex) {
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

        // Document Details Bottom Sheet
        val currentDoc = selectedDocument
        if (currentDoc != null) {
            ModalBottomSheet(
                onDismissRequest = { selectedDocument = null },
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(currentDoc.iconBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = currentDoc.icon,
                                contentDescription = null,
                                tint = currentDoc.iconTint,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = currentDoc.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827)
                            )
                            Text(
                                text = currentDoc.subtitle,
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF9FAFB)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DetailRow(label = "Status", value = currentDoc.statusText)
                            DetailRow(label = "Issued", value = currentDoc.dateLabel)
                            DetailRow(label = "Format & Size", value = currentDoc.sizeLabel)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            val title = currentDoc.title
                            selectedDocument = null
                            scope.launch {
                                snackBarHostState.showSnackbar("Downloaded $title")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF141714),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Download,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Download Document",
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

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color(0xFF6B7280)
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )
    }
}