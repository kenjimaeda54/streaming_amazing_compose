package com.example.streamingamazing.screens.sigIn

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.streamingamazing.ui.theme.fontsLato
import com.example.streamingamazing.ui.theme.fontsPoppins
import com.example.streamingamazing.utility.AuthResultContract
import com.example.streamingamazing.utility.BottomBarScreen
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

//google sigin
//https://blog.devgenius.io/simplifying-authentication-with-google-sign-in-in-jetpack-compose-ui-abba3a652d40
@Composable
fun SigInScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val signInRequestCode = 1
    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google sign in failed"
                } else {
                    coroutineScope.launch {
                        //aqui consigo pegar o usuario logado usando o acount
                        navController.navigate(BottomBarScreen.Home.route)
                    }
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
            }
        }




    Surface {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://images.unsplash.com/photo-1638389746768-fd3020d35add?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                .build(),
            contentDescription = "ImageBackground",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 13.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.7f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Streaming Amazing",
                    fontFamily = fontsPoppins,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = TextStyle(
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.primary.copy(0.39f),
                            offset = Offset(x = 0f, y = 6f),
                            blurRadius = 8.30f
                        ),
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Seu aplicativo de streaming de videos.\nEste aplicativo é independente usamos sua \n conta do Youtube para personalizar sua experiência.",
                    fontFamily = fontsLato,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary,
                    lineHeight = 25.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.primary.copy(0.39f),
                            offset = Offset(x = 0f, y = 6f),
                            blurRadius = 8.30f
                        ),
                    )
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary

                ),
                onClick = { authResultLauncher.launch(signInRequestCode) }) {
                Text(
                    text = "Vamos começar?",
                    fontWeight = FontWeight.Light,
                    fontFamily = fontsLato,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }


}

