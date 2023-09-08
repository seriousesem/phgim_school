package com.serioussem.phgim.school.presentation.ui.screens.login
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.serioussem.phgim.school.R
import com.serioussem.phgim.school.presentation.ui.components.AdMobBanner
import com.serioussem.phgim.school.presentation.ui.components.AppBackground
import com.serioussem.phgim.school.presentation.ui.components.ErrorDialog
import com.serioussem.phgim.school.presentation.ui.components.ScreenProgress
import com.serioussem.phgim.school.presentation.ui.components.VerticalSpacing
import com.serioussem.phgim.school.utils.findActivity
import androidx.compose.material3.Text as Text

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.viewState.value
    val context = LocalContext.current
    BackHandler(onBack = {
        context.findActivity()?.finish()
    })
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AppBackground(modifier = Modifier.matchParentSize())
        if (state.isLoading) {
            ScreenProgress()
        } else if (state.error != null) {
            ErrorDialog(errorMessage = state.error) {
                viewModel.setEvent(event = LoginScreenContract.Event.CLOSE_DIALOG, data = null)
            }
        } else {
            LoginForm(
                login = state.login,
                changeLoginAction = { loginValue ->
                    viewModel.setEvent(
                        event = LoginScreenContract.Event.CHANGE_LOGIN,
                        data = loginValue
                    )
                },
                password = state.password,
                changePasswordAction = { passwordValue ->
                    viewModel.setEvent(
                        event = LoginScreenContract.Event.CHANGE_PASSWORD,
                        data = passwordValue,
                    )
                },
                isEnabledSignInButton = state.isEnabledSignInButton
            ) {
                viewModel.setEvent(
                    event = LoginScreenContract.Event.SIGN_IN,
                    data = navController
                )
            }
        }
    }

}

@Composable
private fun LoginIcon() {
    Image(
        painter = painterResource(id = R.drawable.login_icon),
        contentDescription = null,
        modifier = Modifier.size(120.dp),
    )
}

@Composable
private fun PasswordTextField(password: String, changePasswordAction: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 0.dp))
    {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.password_label)) },
            value = password,
            onValueChange = { changePasswordAction(it) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = RoundedCornerShape(50.dp),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
    }
}

@Composable
private fun LoginTextField(login: String, changeLoginAction: (String) -> Unit) {
    Box(modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 0.dp)) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.login_label)) },
            value = login,
            onValueChange = { changeLoginAction(it) },
            singleLine = true,
            shape = RoundedCornerShape(50.dp),
        )
    }
}

@Composable
private fun SignInButton(
    isEnabledSignInButton: Boolean,
    signInButtonAction: () -> Unit
) {
    Box(modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 0.dp))
    {
        Button(
            onClick = signInButtonAction,
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = isEnabledSignInButton
        ) {
            Text(
                text = stringResource(id = R.string.sign_in),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LoginForm(
    login: String,
    changeLoginAction: (String) -> Unit,
    password: String,
    changePasswordAction: (String) -> Unit,
    isEnabledSignInButton: Boolean,
    signInButtonAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        LoginIcon()
        VerticalSpacing(spacing = 32)
        LoginTextField(login = login, changeLoginAction = changeLoginAction)
        VerticalSpacing(spacing = 24)
        PasswordTextField(password = password, changePasswordAction = changePasswordAction)
        VerticalSpacing(spacing = 32)
        SignInButton(
            isEnabledSignInButton = isEnabledSignInButton,
            signInButtonAction = signInButtonAction
        )
        Spacer(modifier = Modifier.weight(1.0f))
        AdMobBanner()
    }
}


