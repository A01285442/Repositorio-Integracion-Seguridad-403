package com.example.proyectobueno.views



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.composable
import com.example.proyectobueno.R
import com.example.proyectobueno.ui.theme.BlueTEC


import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

//import org.jetbrains.exposed.sql.*

@Serializable
data class User(
    val role: String,
    val password: String,
    val username: String
)

//Login
@Composable
fun LoginScreen(navController: NavController,modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    //Supabase
    val client = createSupabaseClient(
        supabaseUrl = "https://jznehexnwcemrfnrgcuo.supabase.co/",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imp6bmVoZXhud2NlbXJmbnJnY3VvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjcwNTgwNDUsImV4cCI6MjA0MjYzNDA0NX0.-G8wgDwGFR26KrgF5nDXGbetNoFvauHanyuHzoqbRnE"

    ){
        install(Auth)
        install(Postgrest)
    }




    // rol y la contraseña
    suspend fun getUserCredentials(username: String): Pair<String, String>? {
        return try {
            // Perform query to Supabase
            val response = client.from("login")
                .select(columns = Columns.list("role, password, username"))
                .decodeList<User>()

            val users = response ?: return null

            // Find user by username
            val user = users.firstOrNull { userMap ->
                val userUsername = userMap.username
                userUsername == username
            }

            // Extract role and password if user is found
            user?.let { userMap ->
                val role = userMap.role as? String
                val password = userMap.password as? String
                if (role != null && password != null) {
                    role to password
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            println("Error fetching user: ${e.message}")
            null
        }
    }
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LEGAL MATCH",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = BlueTEC,
            modifier = Modifier.padding(1.dp)
                .padding(10.dp)

        )

        Image(
            painter = painterResource(id = R.drawable.casaazul),
            contentDescription = "LogoAxel",
            modifier = Modifier.height(300.dp)

        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (loginError) {
            Text(text = "No valido", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    // Launch coroutine to handle login
                    coroutineScope.launch {
                        val credentials = getUserCredentials(username)
                        if (credentials != null) {
                            val (role, dbPassword) = credentials

                            println("Username ingresado: $username")
                            println("Contraseña ingresada: $password")
                            println("Contraseña en la base de datos: $dbPassword")
                            println("Rol en la base de datos: $role")

                            if (password == dbPassword) {
                                when (role) {
                                    "abogado" -> navController.navigate("casos")
                                    "cliente" -> navController.navigate("Cliente")
                                    else -> loginError = true
                                }
                            } else {
                                loginError = true
                            }
                        } else {
                            loginError = true
                        }
                    }
                } else {
                    loginError = true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueTEC,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "¿No tienes una cuenta?", color = Color.Black)
        Text(
            text = "Regístrate",
            color = BlueTEC,
            modifier = Modifier
                .clickable {
                    navController.navigate("Registro")
                }
        )



        Spacer(modifier = Modifier.height(8.dp))
    }


}



