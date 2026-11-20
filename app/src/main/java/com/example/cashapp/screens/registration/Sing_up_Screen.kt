package com.example.cashapp.screens.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable

// Цвета по макету
val CashAppGreen = Color(0xFF00D632)
val InputBackgroundWhite = Color.White.copy(alpha = 0.18f)
val InputBorderWhite = Color.White.copy(alpha = 0.35f)
val PlaceholderWhite = Color.White.copy(alpha = 0.85f)


@Composable
fun CreateAccountScreen(
    onLogInClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isTermsAccepted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CashAppGreen)
    ) {
        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopEnd)
                .offset(x = 90.dp, y = (-70).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.14f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "GET STARTED",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Create\nAccount",
                    color = Color.White,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 48.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Your money, your moves. Let’s set up your\naccount.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                CashAppTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = "Full Name"
                )

                Spacer(modifier = Modifier.height(14.dp))

                CashAppTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(14.dp))

                CashAppTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = "Phone Number",
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(14.dp))

                CashAppTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Password",
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    isPasswordVisible = isPasswordVisible,
                    onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible }
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (isTermsAccepted) Color.White else InputBackgroundWhite
                            )
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable { isTermsAccepted = !isTermsAccepted },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isTermsAccepted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Checked",
                                tint = CashAppGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    val termsText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("I agree to the ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Terms of Service")
                        }
                    }

                    Text(
                        text = termsText,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { onTermsClick() }
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 20.dp)
            ) {
                Button(
                    onClick = { onFinish()},
                    enabled = isTermsAccepted,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White.copy(alpha = 0.5f),
                        disabledContentColor = Color.Black.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                val loginText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("Already have an account? ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Log In")
                    }
                }

                Text(
                    text = loginText,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onLogInClick() }
                )
            }
        }
    }
}

@Composable
fun CashAppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onTogglePasswordVisibility: () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = PlaceholderWhite,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(
                    onClick = onTogglePasswordVisibility,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) {
                            Icons.Outlined.VisibilityOff
                        } else {
                            Icons.Outlined.Visibility
                        },
                        contentDescription = "Toggle password visibility",
                        tint = PlaceholderWhite
                    )
                }
            }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = InputBackgroundWhite,
            unfocusedContainerColor = InputBackgroundWhite,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = InputBorderWhite,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateAccountScreenPreview() {
    CreateAccountScreen()
}