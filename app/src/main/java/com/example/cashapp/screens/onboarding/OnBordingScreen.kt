package com.example.cashapp.screens.onboarding

import android.R.attr.fontWeight
import android.util.Log.i
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.cashapp.data.OnBoardingData
import com.example.cashapp.screens.registration.CreateAccountScreen
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit
) {

    val pages = listOf(
        OnBoardingData(
            text = "FAST & EASY",
            title1 = "Send Money",
            title2 = "Instantly",
            description = "Pay friends, split bills, and transfer cash in seconds with zero hassle.",
            image = "https://github.com/user-attachments/assets/c97181cd-be2a-4b04-a9ac-bc6121f736eb"
        ),
        OnBoardingData(
            text = "Safe & secure",
            title1 = "Your Money,",
            title2 = "Protected",
            description = "Move your money confidently with secure payments and instant notifications.",
            image = "https://github.com/user-attachments/assets/7ac9b8e6-1e53-4e12-9e02-74348efc4365"
        ),
        OnBoardingData(
            text = "Made for you",
            title1 = "Take Control",
            title2 = "Today",
            description = "Track every payment and keep your everyday finances together in one place.",
            image = "https://github.com/user-attachments/assets/c97181cd-be2a-4b04-a9ac-bc6121f736eb"
        )
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    val scope = rememberCoroutineScope()

    val currentPage = pagerState.currentPage


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff00D632))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {

            TextButton(
                onClick = onFinished
            ) {
                Text(
                    text = "Skip",
                    color = Color.White
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->

            val item = pages[page]

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                AsyncImage(
                    model = item.image,
                    contentDescription = item.title1,
                    modifier = Modifier
                        .width(340.dp)
                        .height(340.dp)
                        .clip(shape = RoundedCornerShape(24.dp)),
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = item.text,
                    fontSize = 16.sp,
                    color = Color(0xFFE7E8E7)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = item.title1,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = item.title2,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = item.description,
                    fontSize = 16.sp,
                    color = Color(0xFFE7E8E7)
                )
            }
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            repeat(pages.size) { index ->

                Box(
                    modifier = Modifier
                        .size(
                            width = if (currentPage == index) 24.dp else 8.dp,
                            height = 8.dp
                        )
                        .background(
                            color = if (currentPage == index)
                                Color.White
                            else
                                Color(0xFFE7E8E7),
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = {

                if (currentPage < pages.lastIndex) {

                    scope.launch {
                        pagerState.animateScrollToPage(
                            currentPage + 1
                        )
                    }

                } else{
                    onFinished()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {

            Text(
                text = if (currentPage == pages.lastIndex)
                    "Get Started"
                else
                    "Next",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}
