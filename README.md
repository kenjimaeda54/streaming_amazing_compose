## Streaming Amazing
Aplicativo de streaming de vídeos, consumindo API do Youtube. Pode visualizar na home os principais vídeos em alta e os canais em que a pessoa está inscrita. Possui tela para os vídeos ao vivo por fim visualizar o perfil do usuário.



## Feature
- Para usar repositório, precisa setar em local.properties as variáveis CLIENT_ID, API_KEY, CLIENT_SECRET,[maneira](https://stackoverflow.com/questions/60474010/read-value-from-local-properties-via-kotlin-dsl) de usar.
- Para segurança, posso setar isMinifyEnabled para true.
- BuildFeatures precisam possuir buildConfig
- Após concluído o processo, precisa fazer o rebuild do projeto na aba Build
- Variáveis no local.properties precisam ser  maisculas se for composto seguido de _

```Kotlin
// acionar as varaiveis
android {
    namespace = "com.example.streamingamazing"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.streamingamazing"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
  
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").reader())
        val apiKey: String = properties.getProperty("API_KEY")
        val clientId: String = properties.getProperty("CLIENT_ID")
        val clientSecret: String = properties.getProperty("CLIENT_SECRET")
        buildConfigField(
            "String",
            "API_KEY",
            "\"$apiKey\""
        ) 
        buildConfigField("String", "CLIENT_ID", "\"$clientId\"")
        buildConfigField("String", "CLIENT_SECRET", "\"$clientSecret\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true // mais seguro
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


     buildFeatures {
        compose = true
        buildConfig = true // precisa para funcionar
    }

}

//para usar
val clientId = BuildConfig.CLIENT_ID

```

##
- Exemplo como implementar Google Sigin
- ClientId e a configuração do aplicativo Web no Google Console, ali também possui a chave secreta.
- ClientId nas configurações é usado no requestIdToken e requestServerAuthCode
- Para garantir que após navegar o bottom será a principal pilha da minha rota, eu faço popUpTo
- Para manter o usuário logado após ele entrar no aplicativo, salvo as informações necessárias no celular dele para recuperar o access token do OATH 2
- Eu recuperei o token do OATH2 no endpoint fornecido nessa [docs](https://developers.google.com/identity/protocols/oauth2?hl=pt-br)


```kotlin
//precisa importar o google auth

//function com as configuracoes

fun getGoogleSignIn(context: Context): GoogleSignInClient {
    val clientId = BuildConfig.CLIENT_ID
    val googleSigIn =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .requestIdToken(clientId)
            .requestScopes(
                Scope("https://www.googleapis.com/auth/youtube.force-ssl"),
                Scope("https://www.googleapis.com/auth/youtube.channel-memberships.creator"),
                Scope("https://www.googleapis.com/auth/youtube")
            )
            .requestServerAuthCode(
                clientId,
                true
            )
            .requestId().requestProfile().build()
    return GoogleSignIn.getClient(context, googleSigIn)
}

// depois no compose
//usuario fica apos a verificao do account == null


val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google sign in failed"
                } else {
                    coroutineScope.launch {
                        navController.navigate(BottomBarScreen.Home.route) {
                            popUpTo(StackScreen.SigIn.name) {
                                inclusive = true
                                saveState = true
                            }
                        } 
                    }
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
            }
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


// manter usuario logado

 fun getUserLogged(context: Context) {
        viewModelScope.launch {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            val tokenInfo = tokenCacheInformation.getTokenInfo()

            if (tokenInfo != null) {
                val dataIsValidToken = httpClientRepository.isValidToken(tokenInfo.idToken!!)


                if(dataIsValidToken.data != null) {
                    _isAnonymous.value = false
                    account?.let {
                        val user = UserModel(
                            accessToken = tokenInfo.accessToken,
                            photo = account.photoUrl,
                            givenName = account.givenName ?: account.displayName,
                            email = account.email,
                            authServeCode = tokenInfo.authServeCode,
                            idToken = tokenInfo.idToken,
                        )
                        _user.value =
                            DataOrException(data = user, isLoading = false, exception = null)
                    }
                }else {
                    _user.value =
                        DataOrException(data = null, isLoading = false, exception = null)
                    tokenCacheInformation.clearTokenInfo()
                }


            } else if (account != null && account.serverAuthCode != null) {
                val googleAuth = httpClientRepository.fetchTokenGoogleAuth(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    serverCode = account.serverAuthCode!!
                )

                if (googleAuth.data != null) {
                    val newTokenInfo = TokenCacheInformationModel(
                        accessToken = googleAuth.data?.access_token,
                        authServeCode = account.serverAuthCode,
                        idToken = googleAuth.data?.id_token
                    )
                    tokenCacheInformation.saveTokenInfo(newTokenInfo)
                    _isAnonymous.value = false
                    val user = UserModel(
                        accessToken = googleAuth.data!!.access_token,
                        photo = account.photoUrl,
                        givenName = account.givenName ?: account.displayName,
                        email = account.email,
                        authServeCode = account.serverAuthCode!!,
                        idToken = account.idToken,
                    )
                    _user.value = DataOrException(data = user, isLoading = false, exception = null)
                }
            } else {
                _user.value = DataOrException(data = null, isLoading = false, exception = null)
            }


        }


```

## 
- No retrofit para determinarmos parâmetros padrões, podemos usar um interceptador.

```kotlin
 fun streamingAmazingHttpGoogleApisClient(): HttpGoogleApisClient =
        Retrofit.Builder().baseUrl(Constants.baseUrlGoogleApi)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val url = chain
                            .request()
                            .url
                            .newBuilder()
                            .addQueryParameter("key" , apiKey)
                            .build()
                        chain.proceed(chain.request().newBuilder().url(url).build())
                    }
                    .build()
            )
            .build()
            .create(HttpGoogleApisClient::class.java)




```

##
- Para criar requisições sequenciais em Compose usei o conceito do Coroutines com async
- Para criar a lista de vídeos com os canais respectivos, eu consultava os vídeos, é com Coroutines requisitava os canais.


```kotlin


//repositorio 
suspend fun fetchVideosWithChannel(completion: (DataOrException<List<VideosWithChannel>, Boolean, Exception>) -> Unit) {
        try {
            val response = httpGoogleApisClient.searchVideos()
            CoroutineScope(Dispatchers.IO).launch {

                val videosWithChannel: List<VideosWithChannel> = response.items.map {
                    val channel =
                        async { httpGoogleApisClient.searchChannel(it.snippet.channelId) }.await()
                    VideosWithChannel(
                        descriptionVideo = it.snippet.description,
                        channelId = channel.items.first().id,
                        id = UUID.randomUUID().toString(),
                        publishedVideo = it.snippet.publishedAt,
                        thumbVideo = it.snippet.thumbnails.high.url,
                        subscriberCountChannel = channel.items.first().statistics.subscriberCount,
                        thumbProfileChannel = channel.items.first().snippet.thumbnails.medium.url,
                        titleVideo = it.snippet.title,
                        videoId = it.id.videoId
                    )

                }
                completion(DataOrException(data = videosWithChannel))
            }
        } catch (exception: Exception) {
            Log.d("Error", exception.message.toString())
            completion(DataOrException(exception = exception))
        }


// viewModel

    fun fetchVideos() {
        viewModelScope.launch {
            _videosWithChannel.value.isLoading = true
            httpClientRepository.fetchVideosWithChannel {
                _videosWithChannel.value = it
                _videosWithChannel.value.isLoading = it.data == null

            }
        }


    }

```

##
- Para refletir as mudanças na viewModel comecei a fazer uso do Flow ao invés de apenas o MutableStateOf


```kotlin

// viewModel
// crio a varaivel privada e o get


private val _videosWithChannel =
        MutableStateFlow<DataOrException<List<VideosWithChannel>, Boolean, Exception>>(
            DataOrException(data = null, true, Exception(""))
        )
private val _videoSelected = MutableStateFlow<VideosWithChannel?>(null)


fun fetchVideos() {
        viewModelScope.launch {
            _videosWithChannel.value.isLoading = true
            httpClientRepository.fetchVideosWithChannel {
                _videosWithChannel.value = it
                _videosWithChannel.value.isLoading = it.data == null

            }
        }


}


//no compose
fun HomeScreen(navController: NavController) {
  val videoWithChannelViewModel: VideoWithChannelViewModel = hiltViewModel()
  val videosWithChannel by videoWithChannelViewModel.videosWithChannel.collectAsState()


    LaunchedEffect(user) {
        user.data?.accessToken?.let {
            val header: Map<String, String> =
                mapOf("Authorization" to "Bearer $it")
            subscriptionViewModel.fetchSubscription(header)
            videoWithChannelViewModel.fetchVideos()
        }


    }


   ...
   items(videosWithChannel.data!!) {
                    RowVideosWithChannel(video = it, modifier = Modifier.clickable {
                        videoWithChannelViewModel.handleVideoSelected(it)
                        navController.navigate(StackScreen.DetailsVideo.name)

                    })
                }

}

```


##
- Para criar um header com LazyColumn pode usar stickyHeader
- Para remover a cor transparente colocada por padrão pode usar Surface no topo.
- Para remover comportamento de bounce use a propriedade  LocalOverscrollConfiguration 
- Quando precisar acompanhar as mudanças de efeito de uma variável, pode usar o LaunchedEffect
- Para lidar com [transições diferentes](https://tomas-repcik.medium.com/jetpack-compose-and-screen-transition-animations-b361fc8164cc) no Compose pode usar enterTransition e exitTransition

```Kotlin
 LocalOverscrollConfiguration provides null


  LaunchedEffect(user) {
        user.data?.accessToken?.let {
            val header: Map<String, String> =
                mapOf("Authorization" to "Bearer $it")
            subscriptionViewModel.fetchSubscription(header)
            videoWithChannelViewModel.fetchVideos()
        }


    }


  stickyHeader { 
                    Surface {
                        Column(
                            modifier = Modifier
                                .height(180.dp)// porque tem o lazyHorizontalGrid por isso precisa da altura
                            , verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(user.data?.photo).build(),
                                    contentDescription = "Image avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                                    Text(
                                        text = "Bem vindo de volta 👋",
                                        fontFamily = fontsLato,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    user.data?.givenName?.let {
                                        Text(
                                            text = it,
                                            fontFamily = fontsLato,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                            LazyHorizontalGrid(
                                rows = GridCells.Fixed(1),
                                modifier = Modifier.height(100.dp), //tem que definir altura dele tambem
                                horizontalArrangement = Arrangement.spacedBy(spacing),
                            ) {
                                items(subscription.data!!.items) {
                                    RowChannelSubscription(modifier = Modifier.clickable {
                                        subscriptionViewModel.handleChannelSubscriptionSelected(it)
                                        navController.navigate(
                                            StackScreen.DetailsChannel.name
                                        )
                                    }, snippet = it.snippet)
                                }
                            }
                        }
                    }




// usando transições de telas customizáveis
composable(StackScreen.DetailsChannel.name, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(3000)
            )
        }, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(3000)
            )
        }) { entry ->
            val parentEntryHome = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentEntrySubscriptionViewModel =
                hiltViewModel<SubscriptionViewModel>(parentEntryHome)
            val parentEntryVideoWithChannelViewModel = hiltViewModel<VideoWithChannelViewModel>(parentEntryHome)
            parentEntrySubscriptionViewModel.channelSubscriptionSelected?.let {
                DetailsChannel(
                    channelSubscription = it,
                    videoWithChannelViewModel = parentEntryVideoWithChannelViewModel,
                    navController
                )
            }
        }

```


##
- Uma maneira de integrar código de Android com Compose usando View
- Para alterar o comportamento de Landscap e Portrait programaticamente, pode usar  ActivityInfo
- Usei esta [lib](https://github.com/PierfrancescoSoffritti/android-youtube-player) para youtube player

```kotlin

fun YoutubeView(videoId: String) {
    val activity = LocalContext.current as Activity

    AndroidView(
        factory = {
            val view = YouTubePlayerView(it)
            view.enableAutomaticInitialization = false //precisa disso para nao crashar
            //coloque a palavra object porque e um abstrata classe
            view.addFullScreenListener(object : YouTubePlayerFullScreenListener {
                override fun onYouTubePlayerEnterFullScreen() {
                    activity.requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR
                }

                override fun onYouTubePlayerExitFullScreen() {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }

            })
            val playerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)

                    //https://github.com/PierfrancescoSoffritti/android-youtube-player/issues/1037
                    val uiController = DefaultPlayerUiController(view, youTubePlayer)
                    uiController.showUi(true)
                    uiController.showDuration(true)
                    uiController.showFullscreenButton(true)
                    uiController.showSeekBar(true)
                    uiController.showBufferingProgress(true)
                    uiController.showCurrentTime(true)
                    uiController.showMenuButton(false)
                    uiController.showPlayPauseButton(true)
                    uiController.showVideoTitle(false)
                    uiController.showYouTubeButton(false)
                    view.setCustomPlayerUi(uiController.rootView)
                    youTubePlayer.loadVideo(videoId, 0f)

                }


            }
            val builder = IFramePlayerOptions.Builder()
                .controls(0) //se deixar isso fora de 0 ira aparecer um outro snack em cima
                .ccLoadPolicy(0)
                .rel(0)
                /*.fullscreen(1)*/
                .ivLoadPolicy(1)
            val options = builder.build()
            view.initialize(playerListener, true, options)
            view
        })


//depois so chamar o Composable normal
PreviewYoutubePlaceHolder()

```


## 
- Tem uma lógica minha que usa [operator bitwise](https://www.programiz.com/kotlin-programming/bitwise) para trabalhar com operações de bit level, para isto precisa transformar em int ou long
- No meu caso, estou usando int, pois preciso do índex da lista.


```kotlin


    fun formatQuantityView(value: String): String {
        val symbol = listOf("", "mil", "mi", "b", "t", "p", "e")
        //operator bitwise so aceita long or int
        //https://www.programiz.com/kotlin-programming/bitwise
        val tier = log10(abs(value.toDouble())).toInt() / 3 or 0

        if (tier == 0) {
            return value
        }

        val suffix = symbol[tier]
        val scale = 10.0.pow(tier * 3)
        val scaled = value.toDouble() / scale
        return "${scaled.toBigDecimal().setScale(1, RoundingMode.FLOOR).toDouble()} $suffix"
    }

```



