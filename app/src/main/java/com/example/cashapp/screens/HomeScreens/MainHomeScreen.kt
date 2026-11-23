package com.example.cashapp.screens.HomeScreens

import android.R
import android.R.attr.fontWeight
import android.R.attr.top
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.cashapp.viewmodel.UiState
import com.example.cashapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigation: () -> Unit = {},
    viewModel: UserViewModel = viewModel(),
    navigateToAddCashScreen: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    var bottomSheet by remember{ mutableStateOf(false) }
    val snackBar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                SnackbarHost(
                    hostState = snackBar,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) { data ->

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFFF0000),
                        contentColor = Color.White,
                        shape = RoundedCornerShape(16.dp),
                        shadowElevation = 6.dp
                    ) {
                        Text(
                            text = data.visuals.message,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                when (val user = state) {

                    is UiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is UiState.Error -> {
                        Text("Error: ${user.errorMassage}")
                    }

                    is UiState.Success -> {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color(0xFFF5F5F5))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 10.dp, start = 10.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                ) {
                                    Text(user.userInfo.fullName, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)

                                    Box(
                                        modifier = Modifier
                                            .size(35.dp)
                                            .background(color = Color.Black, shape = CircleShape),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text("${user.userInfo.fullName.first()}", color = Color.White, fontSize = 20.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(10.dp)
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp)
                                        ) {
                                            Text("Clash Balance", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                            Text("Account & Routing", fontSize = 14.sp, color = Color.LightGray, fontWeight = FontWeight.Bold)
                                        }

                                        Spacer(modifier = Modifier.height(10.dp))

                                        Text("$${user.userInfo.balance}", fontSize = 35.sp, fontWeight = FontWeight.ExtraBold)

                                        Spacer(modifier = Modifier.height(35.dp))

                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 15.dp, end = 15.dp)
                                        ) {
                                            Button(
                                                onClick = {bottomSheet = true},
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFFF5F5F5)
                                                )
                                            ) {
                                                Text("Add Cash", color = Color.Black, fontWeight = FontWeight.Bold)
                                            }

                                            Button(
                                                onClick = {},
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFFF5F5F5)
                                                )
                                            ) {
                                                Text("Cash Out", color = Color.Black, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }




                                if (bottomSheet){
                                    ModalBottomSheet(
                                        onDismissRequest = {
                                            bottomSheet = false
                                        },
                                        containerColor = Color.White
                                    ) {

                                        val numbers = listOf("$1", "$10", "$20", "$50", "$100", "...")
                                        var selectedNumber by remember { mutableStateOf<String?>(null) }
                                        var addCash by rememberSaveable { mutableStateOf(0) }


                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text("Add Cash", fontSize = 25.sp, fontWeight = FontWeight.Bold)

                                            Spacer(Modifier.height(15.dp))

                                            Text("$$addCash", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF00D45A))

                                            FlowRow(
                                                maxItemsInEachRow = 3,
                                                horizontalArrangement = Arrangement.spacedBy(17.dp),
                                                verticalArrangement = Arrangement.spacedBy(17.dp),
                                                modifier = Modifier.padding(top = 25.dp)
                                            ) {
                                                numbers.forEach { number ->
                                                    val isSelected = selectedNumber == number
                                                    Box(
                                                        modifier = Modifier
                                                            .size(width = 66.dp, height = 53.dp)
                                                            .clip(RoundedCornerShape(15.dp))
                                                            .background(
                                                                color = if (isSelected) Color(0xFF00D45A) else Color(0xFFF5F5F5)
                                                            )
                                                            .clickable {
                                                                if (number == "..."){
                                                                    navigateToAddCashScreen()
                                                                }else{
                                                                    selectedNumber = number
                                                                    addCash = number.removePrefix("$").toInt()
                                                                }
                                                            },
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = number,
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = if (isSelected) Color.White else Color.Black
                                                        )
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(52.dp))

                                            Button(
                                                onClick = {
                                                    if (addCash == 0){
                                                        scope.launch {
                                                            snackBar.showSnackbar(message = "Select some amount !")
                                                        }
                                                    }
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(48.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFF00D45A)
                                                ),
                                                shape = RoundedCornerShape(30.dp)
                                            ) {
                                                Text(
                                                    "Add",
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }







                                FlowRow(
                                    maxItemsInEachRow = 2,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                                    verticalArrangement = Arrangement.spacedBy(15.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(195.dp)
                                            .clickable(onClick = {})
                                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp),
                                            ) {
                                                Text("Savings", fontWeight = FontWeight.Bold)
                                                Text(">", color = Color.LightGray, fontWeight = FontWeight.Bold)
                                            }
                                            AsyncImage(
                                                model = "https://github.com/user-attachments/assets/9f406684-7c98-43f3-87d9-5e56f4f36435",
                                                contentDescription = "Savings",
                                                modifier = Modifier
                                                    .width(80.dp)
                                                    .height(80.dp)
                                                    .padding(bottom = 10.dp)
                                            )

                                            Text("$100000", fontSize = 25.sp, fontWeight = FontWeight.Bold)

                                            Spacer(Modifier.height(5.dp))

                                            Text("Save for a goal", color = Color.LightGray, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(195.dp)
                                            .clickable(onClick = {})
                                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp),
                                        ) {
                                            Text("Buy bitcoin", fontWeight = FontWeight.Bold)
                                            Text(">", color = Color.LightGray, fontWeight = FontWeight.Bold)
                                        }
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Bottom,
                                            modifier = Modifier
                                                .fillMaxSize()
                                        ) {
                                            AsyncImage(
                                                model = "https://github.com/user-attachments/assets/ac131a79-a6c7-4e53-b0b5-984a629c8cb6",
                                                contentDescription = "Bitcoin",
                                                modifier = Modifier
                                                    .width(130.dp)
                                                    .height(130.dp)
                                                    .padding(bottom = 10.dp)
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(195.dp)
                                            .clickable(onClick = {})
                                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 25.dp),
                                        ) {
                                            Text("Invest in\nstocks", fontWeight = FontWeight.Bold)
                                            Text(">", color = Color.LightGray, fontWeight = FontWeight.Bold)
                                        }
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Bottom,
                                            modifier = Modifier
                                                .fillMaxSize()
                                        ) {
                                            AsyncImage(
                                                model = "https://github.com/user-attachments/assets/dd1a83e4-7b39-4fcc-a1d2-db727ce7408a",
                                                contentDescription = "Stocks",
                                                modifier = Modifier
                                                    .width(150.dp)
                                                    .height(180.dp)
                                                    .padding(bottom = 10.dp)
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(195.dp)
                                            .clickable(onClick = {})
                                            .background(Color.White, RoundedCornerShape(16.dp))
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 25.dp),
                                        ) {
                                            Text("Free tax\nfilling", fontWeight = FontWeight.Bold)
                                            Text(">", color = Color.LightGray, fontWeight = FontWeight.Bold)
                                        }

                                        AsyncImage(
                                            model = "https://github.com/user-attachments/assets/46882ab5-bfd1-425c-850a-6e0199286f62",
                                            contentDescription = "Tax",
                                            modifier = Modifier
                                                .width(150.dp)
                                                .height(100.dp)
                                                .align(Alignment.BottomCenter)
                                        )
                                    }


                                }

                                Spacer(Modifier.height(5.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(170.dp)
                                        .clickable(onClick = {})
                                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {

                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 25.dp),
                                        ) {
                                            Text("Build your credit", fontWeight = FontWeight.Bold)
                                            Text(">", color = Color.LightGray, fontWeight = FontWeight.Bold)
                                        }

                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp)
                                        ) {
                                            Text("Track and grow\nyour score", fontWeight = FontWeight.Bold)

                                            AsyncImage(
                                                model = "https://github.com/user-attachments/assets/9c2e2aa0-1280-41dd-82c7-48e95e924d86",
                                                contentDescription = "Tax",
                                                modifier = Modifier
                                                    .width(250.dp)
                                                    .height(200.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(Modifier.height(15.dp))

                            }
                        }

                    }


                }

            }

        }
    }

}
