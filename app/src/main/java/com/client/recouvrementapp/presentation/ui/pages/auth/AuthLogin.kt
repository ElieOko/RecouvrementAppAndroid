package com.client.recouvrementapp.presentation.ui.pages.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.client.recouvrementapp.R
import com.client.recouvrementapp.core.AsteriskPasswordVisualTransformation
import com.client.recouvrementapp.data.local.Constantes.Companion.authConnectRoute
import com.client.recouvrementapp.data.remote.KtorClientAndroid
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.model.ResponseHttpRequest
import com.client.recouvrementapp.domain.model.ResponseHttpRequestAuth
import com.client.recouvrementapp.domain.model.core.user.ProfilUser
import com.client.recouvrementapp.domain.model.core.user.User
import com.client.recouvrementapp.domain.model.core.user.UserAuth
import com.client.recouvrementapp.domain.model.room.UserModel
import com.client.recouvrementapp.domain.route.ScreenRoute
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.animate.AnimatedBackgroundShapes
import com.client.recouvrementapp.presentation.components.elements.MAlertDialog
import com.partners.hdfils_recolte.presentation.ui.components.Space
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthLogin(
    navC: NavHostController,
    vm: ApplicationViewModel?
) {
    AuthLoginBody(navC,vm)
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthLoginBody(
    navC: NavHostController? = null,
    vm: ApplicationViewModel? = null,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }
    var msg: String? by remember { mutableStateOf("") }
    var titleMsg by remember { mutableStateOf("Erreur") }
    var isShow by remember { mutableStateOf(false) }
    val isVisible = remember { mutableStateOf(false) }
    val userList = vm?.room?.user?.listUser?.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AnimatedBackgroundShapes()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Space(y=5)
                    Image(
                        painterResource(R.drawable.recouvrement),
                        contentDescription = "",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop)
                    Space(y=10)
                    Text(
                        text = "Login",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Recouvrement FJS",
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                                username = it
                        },
                        label = { Text("Username") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                                tint = Color.Black
                            )
                        },
                        maxLines = 1,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedLeadingIconColor = Color.Black,
                            unfocusedLeadingIconColor = Color.Black
                        ),
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.password),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                                tint = Color.Black
                            )
                        },
                        maxLines = 1,
                        trailingIcon = {
                            IconButton(onClick = {
                                isVisible.value = !isVisible.value
                            }) {
                                Icon(painter = painterResource(if(isVisible.value) R.drawable.open else R.drawable.close), contentDescription = null, tint = Color.Black)
                            }
                        },
                        visualTransformation = if(isVisible.value) VisualTransformation.None else AsteriskPasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            focusedLeadingIconColor = Color.Black,
                            unfocusedLeadingIconColor = Color.Black
                        ),
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Space(y=20)
                    Button(
                        onClick = {
                            try {
                                when(vm?.configuration?.isConnectNetwork){
                                    true ->{
                                        if(password.isEmpty()){
                                            isShow = true
                                            msg = "Le mot de passe n'est pas renseigné"
                                            titleMsg = "Champs vide"
                                        }
                                        if(username.isEmpty()){
                                            isShow = true
                                            msg = "Le nom d'utilisateur n'est pas renseigné"
                                            titleMsg = "Champs vide"
                                        }
                                        if (username.isNotEmpty() && password.isNotEmpty()){
                                            coroutineScope.launch {
                                                isActive = false
                                                delay(1000)
                                                Log.e("connect server->","${UserAuth(username,password)}")
                                                try {
                                                    val response = KtorClientAndroid().postData(authConnectRoute,
                                                        UserAuth(username =username,password = password, grant_type = "password")
                                                    )
                                                    val status = response.status.value
                                                    when(status){
                                                        in 200..299 ->{
                                                            isActive = true
                                                            val res = response.body<ResponseHttpRequestAuth>()
                                                            val userModel = UserModel(id = res.profile.id, username = res.profile.username, displayName = res.profile.displayName)
                                                            Log.e("data response ->","$res")
                                                            scope.launch {
                                                                //StoreData(context).delete()
                                                                val userT = ProfilUser(
                                                                    token_type = res.token_type,
                                                                    access_token = res.access_token,
                                                                    profile = User(res.profile.id,
                                                                        res.profile.displayName,
                                                                        res.profile.username
                                                                    )
                                                                )
                                                                Log.e("Call response ->***","$userT")
                                                                StoreData(context).saveUser(userT)
                                                            }
                                                            scope.launch {
                                                                vm.room.user.getUser(userId = userModel.id)
                                                                if (userList?.value?.isNotEmpty() == true){
                                                                    vm.room.user.update(userModel)
                                                                    Log.i("update user->","$userModel")
                                                                } else{
                                                                    vm.room.user.insert(userModel)
                                                                    Log.i("insert user->","$userModel")
                                                                }
                                                                Log.i("size user->","${userList?.value?.size}")
                                                            }
                                                            navC?.navigate(route = ScreenRoute.Home.name){
                                                                popUpTo(navC.graph.id){
                                                                    inclusive = true
                                                                }
                                                            }
                                                        }
                                                        in 500..599 ->{
                                                            isActive = true
                                                            titleMsg = "Erreur serveur"
                                                            msg = "Erreur serveur réssayer plus tard nous resolvons ce problème"
                                                            isShow = true
                                                        }
                                                        in 400..499 ->{
                                                            isActive = true
                                                            val res = response.body<ResponseHttpRequest>()
                                                            titleMsg = "erreur"
                                                            msg = res.message
                                                            isShow = true
                                                        }
                                                    }
                                                }
                                                catch (e : HttpRequestTimeoutException){
                                                    msg         = "La requete a pris plus de temps que prevue cela est du a votre connection ressayer"
                                                    titleMsg    = "Request expire"
                                                    isShow      = true
                                                    isActive    = true
                                                    Log.e("Network expired request ->",e.message.toString())
                                                }
                                                catch (e : Exception){
                                                    msg         = "${e.message}\nVeuillez ressayer !!"
                                                    titleMsg    = "Exception"
                                                    isShow      = true
                                                    isActive    = true
                                                }
                                            }
                                        }
                                    }
                                    else -> {
                                        msg = "Vous n'êtes pas connecté veuillez vérifier votre connexion !!!"
                                        titleMsg = "Problème de connexion"
                                        isShow = true
                                    }
                                }
                            }
                            catch (e : Exception){
                                Log.e("error ->",e.message.toString())
                            }

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =  Color(0xFF15D77D),
                            disabledContentColor = Color(0xFF080624),
                            disabledContainerColor = Color(0xFF080624)
                        )
                    ) {
                        if(isActive)
                            Text(text = "Se connecter", fontSize = 16.sp, color = Color.White)
                        else
                            LinearWavyProgressIndicator(
                                color = Color.White,
                                trackColor = Color.White,
                            )
                    }
                    Space(y=20)
                    if(isShow){
                        MAlertDialog(
                            dialogTitle = titleMsg,
                            dialogText =  "$msg",
                            onDismissRequest = {
                                isShow = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthLoginPreview(){
    AuthLoginBody()
}