package com.example.streamingamazing.screens.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.streamingamazing.route.StackScreen
import com.example.streamingamazing.screens.profile.view.TitleAndSubtitle
import com.example.streamingamazing.ui.theme.fontsLato
import com.example.streamingamazing.view.ComposableLifecycle
import com.example.streamingamazing.viewmodels.UserViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val userViewModel: UserViewModel = hiltViewModel()
    val user by userViewModel.user.collectAsState()

    ComposableLifecycle { _, event ->

        if (event == Lifecycle.Event.ON_CREATE) {
            userViewModel.getUserLogged(context = context)
        }

    }


    fun handleGoogleSingOut() {
        userViewModel.signOutUser()
        navController.navigate(StackScreen.SigIn.name)

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(vertical = 10.dp, horizontal = 13.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(user.data?.photo).build(),
            contentDescription = "Avatar user",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(
                    CircleShape
                ),
            alignment = Alignment.Center

        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.Start
        ) {
            user.data?.givenName?.let { TitleAndSubtitle(title = "Nome", subTitle = it) }
            Spacer(modifier = Modifier.height(15.dp))
            user.data?.email.let {
                if (it != null) {
                    TitleAndSubtitle(title = "Email", subTitle = it)
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.clickable { handleGoogleSingOut() },
                text = "Sair", fontFamily = fontsLato,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start
            )
        }

    }
}