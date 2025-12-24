package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlin.math.max

import com.cr_d.passwordmanagerapp.R
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.create_password.ApplicationOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

val horizontalFramePadding = 20.dp

@Composable
fun PasswordDetailScreen(
    innerPadding: PaddingValues,
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel,
    navController: NavController
){
    Column (modifier = Modifier.padding(innerPadding)){
        HeaderButtons(viewModel, snackFunction, navController)
        PasswordDetailedCard(context, snackFunction, viewModel, settings)
    }
}

@Composable
fun HeaderButtons(viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit, navController: NavController){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalFramePadding, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        ModeButton("Info", viewModel::onEnableBasicInfoMode)
        ModeButton("Detalle", viewModel::onEnableFullInfoMode)
        ModeButton("Editar", viewModel::onEnableEditMode)
        DeletePasswordButton(snackFunction, viewModel, navController)
    }
}

@Composable
fun ModeButton(label: String, onclick: () -> Unit){
    Button(
        onClick = { onclick() },
        shape = RectangleShape
    ) {
        Text(label)
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PasswordDetailedCard(
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel,
){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    when {
        state.password == null -> {
            CircularProgressIndicator()
        }
        else -> {
            Card(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(horizontal = horizontalFramePadding, vertical = 10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when(state.mode) {
                        PasswordDetailUiMode.FULL_INFO_MODE -> {
                            DetailedMode(
                                viewModel = viewModel,
                                settings = settings,
                                password = state.password,
                                context = context,
                                snackFunction = snackFunction,
                            )
                        }
                        PasswordDetailUiMode.EDIT_MODE -> {
                            EditMode(
                                viewModel = viewModel,
                                snackFunction = snackFunction
                            )
                        }

                        PasswordDetailUiMode.BASIC_INFO_MODE -> {
                            BasicMode(
                                viewModel = viewModel,
                                settings = settings,
                            )
                        }
                        else -> {

                        }
                    }
                }
            }
            if (state.mode!= PasswordDetailUiMode.EDIT_MODE) PasswordCard(
                password = state.password,
                viewModel = viewModel,
                isPasswordShown = state.isPasswordShown,
                context = context,
                snackFunction = snackFunction
            )
        }
    }
}

@Composable
fun DetailedMode(
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel,
    password: PasswordData?,
    context: Context,
    snackFunction: (String)-> Unit,
){
    ApplicationInfoSection(viewModel)
    PasswordInfoSection(viewModel)
    DateInfoSection(viewModel, settings)
    SecurityInfoSection(viewModel)
    ButtonsSection(
        password = password,
        viewModel = viewModel,
        context = context,
        snackFunction = snackFunction,
    )
}

@Composable
fun BasicMode(
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel,
){
    val password = viewModel.uiState.collectAsStateWithLifecycle().value.password ?: return
    val settings = settings.settings.collectAsState().value

    Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        ApplicationTitle(password.appInfo.applicationName)
        CustomRow("Nombre de usuario", password.appInfo.account)
        CustomRow("Sitio Web", password.appInfo.url)
        CustomRow("Última Modificación", password.metadata.lastUpdate.formatAs(settings.dateFormat))
    }
}

@Composable
fun ApplicationTitle(appTitle : String){
    Text(appTitle, fontSize = 50.sp, modifier = Modifier.padding(bottom = 10.dp))
}

@Composable
fun CustomRow(fieldName: String, value: String){
    Row(Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(fieldName)
        Spacer(modifier = Modifier.weight(1f))
        Text(value)
    }
    HorizontalDivider(thickness = 2.dp)
}

@Composable
fun PasswordCard(
    password: PasswordData,
    viewModel: PasswordDetailViewModel,
    isPasswordShown: Boolean,
    context: Context,
    snackFunction: (String) -> Unit
){
    Card (modifier = Modifier.padding(vertical = 10.dp, horizontal = horizontalFramePadding)) {
        Column (modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text("Contraseña", fontSize = 25.sp)
            HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 10.dp))
            ButtonsSection(
                password = password,
                viewModel = viewModel,
                context = context,
                snackFunction = snackFunction
            )
            if (isPasswordShown) Text(password.plainPassword.value)
            else Text("********")
        }
    }
}

@Composable
fun EditMode(viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    UpdateSection(viewModel, snackFunction)
}

@Composable
fun ApplicationInfoSection(viewModel: PasswordDetailViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value.password ?: return
    val appInfo = state.appInfo

    Text("Información de la aplicación")
    Text(appInfo.applicationName)
    Text("Sitio web: ${appInfo.url}")
    Text("Cuenta asociada: ${appInfo.account}")
}

@Composable
fun PasswordInfoSection(viewModel: PasswordDetailViewModel){
    val state = viewModel.uiState.collectAsState().value

    Text("Información de la contraseña")
    if (state.isPasswordShown) Text("Password: ${state.password!!.plainPassword.value}")
    else Text("Password: ********")
    MetadataBoolSection(viewModel)
}

@Composable
fun DateInfoSection(viewModel: PasswordDetailViewModel, settings: SettingsViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value.password ?: return
    val metadataInfo = state.metadata
    val settings = settings.settings.collectAsState().value

    Text("Fecha de creación: ${metadataInfo.creationDate.formatAs(settings.dateFormat)}")
    Text("Última actualización: ${metadataInfo.lastUpdate.formatAs(settings.dateFormat)}")
}

@Composable
fun SecurityInfoSection(viewModel: PasswordDetailViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value.password ?: return
    val securityScore = state.securityScore

    Text("Puntuación de seguridad ${String.format("%.2f", securityScore)}")
}

@Composable
fun ButtonsSection(
    password: PasswordData?,
    viewModel: PasswordDetailViewModel,
    context: Context,
    snackFunction: (String)-> Unit,
){
    if (password == null) return
    Row (Modifier.fillMaxWidth().padding(bottom = 5.dp), horizontalArrangement = Arrangement.SpaceAround){
        TogglePasswordVisibilityButton(viewModel)
        CopyToClipboardButton(password.plainPassword.value, context, snackFunction)
    }
}

@Composable
fun TogglePasswordVisibilityButton(viewModel: PasswordDetailViewModel){
    val passwordState = viewModel.uiState.collectAsState().value.isPasswordShown

    Button(
        onClick = viewModel::onVisibilityToggle
    ) {

        val progress by animateFloatAsState(
            targetValue = if (passwordState) 1f else 0f,
            animationSpec = tween(300)
        )

        if(passwordState){
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.outline_visibility_off_24),
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        scaleY = progress
                        alpha = max(progress, 0.2f)
                        transformOrigin = TransformOrigin(0.5f, 0.5f)
                    }
                )
                Text("Ocultar", modifier = Modifier.padding(horizontal = 15.dp))
            }
        } else {
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.outline_visibility_24),
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        alpha = 1f - progress
                        scaleX = 1f - progress
                    }
                )
                Text("Mostrar", modifier = Modifier.padding(horizontal = 15.dp))
            }
        }
    }
}

@Composable
fun CopyToClipboardButton(passwordText: String, context: Context, snackFunction: (String)-> Unit){
    Button(
        onClick = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied_Text", passwordText).apply {
                description.extras = PersistableBundle().apply {
                    putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                }
            }
            clipboard.setPrimaryClip(clip)

            snackFunction("Contraseña copiada en el portapapeles")
        }
    ) {
        Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text("Copiar", modifier = Modifier.padding(horizontal = 15.dp))
            Image(
                painterResource(R.drawable.outline_content_copy_24),
                contentDescription = "",
            )
        }
    }
}

@Composable
fun DeletePasswordButton(snackFunction: (String)-> Unit, viewModel: PasswordDetailViewModel, navController: NavController){
    Button(
        onClick = {
            viewModel.onDeletePassword()
            viewModel.onDeleteMode()
            snackFunction("Contraseña eliminada")
            navController.navigate("ShowPasswordScreen")
        }
    ) {
        Text("Eliminar")
    }
}

@Composable
fun UpdateSection(viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    val state = viewModel.uiState.collectAsState().value

    Column() {
        ApplicationOutlinedTextField("Aplicacion", state.newAppName, viewModel::onAppNameChanged)
        ApplicationOutlinedTextField("Url", state.newUrl, viewModel::onUrlChanged)
        ApplicationOutlinedTextField("Cuenta", state.newAccount, viewModel::onAccountChanged)
        ApplicationOutlinedTextField("Contraseña", state.newPlainPassword.value, viewModel::onPlainPasswordChange)
        UpdatePasswordButton(viewModel, snackFunction)
    }
}

@Composable
fun UpdatePasswordButton(viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    Button(
        onClick = {
            viewModel.onUpdatePassword()
            snackFunction("Contraseña actualizada correctamente")
        }
    ) {
        Text("Actualizar contraseña")
    }
}

@Composable
fun MetadataBoolSection(viewModel: PasswordDetailViewModel){
    val passwordData = viewModel.uiState.collectAsState().value.password ?: return
    val metadata = passwordData.metadata

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Column {
            MetadataCheck("Minúsculas", metadata.hasLowerCase)
            MetadataCheck("Mayúsculas", metadata.hasUpperCase)
        }
        Column {
            MetadataCheck("Números", metadata.hasNumbers)
            MetadataCheck("Especiales", metadata.hasSpecials)
        }
    }
}

@Composable
fun MetadataCheck(label: String, value: Boolean){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {
        Text(label)
        Icon(
            if (value) Icons.Default.Check
            else Icons.Default.Close,
            contentDescription = ""
        )
    }
}