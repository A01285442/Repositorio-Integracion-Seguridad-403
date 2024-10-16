package com.example.legalmatch.google

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialManagerCallback
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.*
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.legalmatch.data.api.models.SendUsuario
import com.example.legalmatch.utils.SUPABASE_KEY
import com.example.legalmatch.utils.SUPABASE_URL
import com.example.legalmatch.viewmodel.Usuario
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

val supabase = createSupabaseClient(
    supabaseUrl = SUPABASE_URL,
    supabaseKey = SUPABASE_KEY
) {
    install(Auth)
    install(Postgrest)
    install(Storage)
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun GoogleSignIn(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("322465845579-pt69q803qhnfv1tk0e86al6n3fomuiqn.apps.googleusercontent.com")
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        credentialManager.getCredential(
            request,
            context.mainExecutor,
            object : CredentialManagerCallback<GetCredentialResponse> {
                override fun onSuccess(result: GetCredentialResponse) {
                    val googleCredential = result.credential as? GoogleIdCredential
                    if (googleCredential != null) {
                        val googleId = googleCredential.id
                        val email = googleCredential.idToken

                        coroutineScope.launch {
                            val userExists = checkIfUserExists(googleId)

                            if (userExists) {
                                navController.navigate("")
                            } else {
                                registerUser(googleId, email)
                                navController.navigate("")
                            }
                        }
                    }
                }

                override onError(e: Exception) {
                    Log.e(TAG, "Google sign in failed: ${e.message}", e)
                }
            }
        )
    }

    Button(onClick = onClick) {
        Text("Sign in with Google")
    }
}

suspend fun checkIfUserExists(googleId: String): Boolean {
    return try {
        val usuarios = supabase.from("usuarios")
            .select(){
                filter{
                    eq("google_id", googleId) //Falta agregarlo a supabase lol
                }
            }

            .decodeList<Usuario>()

        usuarios.isNotEmpty()
    } catch (e: Exception) {
        Log.e(TAG, "Error verificando usuario: ${e.message}", e)
        false
    }
}

suspend fun registerUser(googleId: String, email: String) {
    try {
        val response = supabase.from("usuarios")
            .insert(mapOf(
                "google_id" to googleId,
                "email" to email
            ))
            .decodeList<SendUsuario>()

    } catch (e: Exception) {
        Log.e(TAG, "Error al registrar usuario: ${e.message}", e)
    }
}
