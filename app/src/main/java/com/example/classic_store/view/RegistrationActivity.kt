package com.example.classic_store.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.classic_store.R
import com.example.classic_store.model.UserModel
import com.example.classic_store.repository.UserRepositoryImpl
import com.example.classic_store.viewmodel.UserViewModel

// ONLY CHANGED COLORS - Updated to Purple & Pink theme
private val PrimaryPurple = Color(0xFF7B1FA2)
private val PrimaryPurpleDark = Color(0xFF4A148C)
private val AccentPink = Color(0xFFE91E63)
private val AccentGold = Color(0xFFFF9800)
private val BackgroundLight = Color(0xFFFCE4EC)

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationBody()
        }
    }
}

@Composable
fun RegistrationBody() {
    val repo = remember { UserRepositoryImpl() }
    val primaryColor = PrimaryPurple // Changed from green to purple
    val userViewModel = remember { UserViewModel(repo) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val activity = context as? Activity
    val scrollState = rememberScrollState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = BackgroundLight) // Changed background to light pink
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Join Classic Store",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = PrimaryPurpleDark // Changed to purple
            )
            Text(
                text = "Create an account to start shopping",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(R.drawable.loginimg),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .width(180.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Full Name Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = PrimaryPurple
                    )
                },
                placeholder = {
                    Text("Full Name")
                },
                value = fullName,
                onValueChange = { input ->
                    fullName = input
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    focusedLabelColor = PrimaryPurple,
                    cursorColor = PrimaryPurple
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Email Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = AccentPink
                    )
                },
                placeholder = {
                    Text("abc@gmail.com")
                },
                value = email,
                onValueChange = { input ->
                    email = input
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPink,
                    focusedLabelColor = AccentPink,
                    cursorColor = AccentPink
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Password Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = AccentGold
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            if (passwordVisibility)
                                R.drawable.baseline_visibility_24
                            else
                                R.drawable.baseline_visibility_off_24
                        ),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            passwordVisibility = !passwordVisibility
                        },
                        tint = AccentGold
                    )
                },
                placeholder = {
                    Text("Password")
                },
                value = password,
                onValueChange = { input ->
                    password = input
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentGold,
                    focusedLabelColor = AccentGold,
                    cursorColor = AccentGold
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Confirm Password Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = AccentGold
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            if (confirmPasswordVisibility)
                                R.drawable.baseline_visibility_24
                            else
                                R.drawable.baseline_visibility_off_24
                        ),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            confirmPasswordVisibility = !confirmPasswordVisibility
                        },
                        tint = AccentGold
                    )
                },
                placeholder = {
                    Text("Confirm Password")
                },
                value = confirmPassword,
                onValueChange = { input ->
                    confirmPassword = input
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentGold,
                    focusedLabelColor = AccentGold,
                    cursorColor = AccentGold
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Phone Number Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        tint = AccentPink
                    )
                },
                placeholder = {
                    Text("Phone Number")
                },
                value = phoneNumber,
                onValueChange = { input ->
                    phoneNumber = input
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPink,
                    focusedLabelColor = AccentPink,
                    cursorColor = AccentPink
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Address Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Address
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = PrimaryPurple
                    )
                },
                placeholder = {
                    Text("Enter Your Address")
                },
                value = address,
                onValueChange = { input ->
                    address = input
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    focusedLabelColor = PrimaryPurple,
                    cursorColor = PrimaryPurple
                )
            )

            // Terms and Conditions Checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = agreeToTerms,
                    onCheckedChange = { checked ->
                        agreeToTerms = checked
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PrimaryPurple, // Changed from green to purple
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = "I agree to the Terms and Conditions",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Register Button
            Button(
                onClick = {
                    // Add validation here
                    if (fullName.isBlank() || email.isBlank() || phoneNumber.isBlank() ||
                        password.isBlank() || confirmPassword.isBlank() || address.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (!agreeToTerms) {
                        Toast.makeText(context, "Please agree to terms and conditions", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    userViewModel.register(email, password) { success, message, userId ->
                        if (success) {
                            val userModel = UserModel(
                                userId, fullName, email, "" ,phoneNumber, address
                            )
                            userViewModel.addUserToDatabase(userId, userModel) { success, message ->
                                if (success) {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    // Navigate to login or main activity
                                    val intent = Intent(context, LoginActivity::class.java)
                                    context.startActivity(intent)
                                    activity?.finish()
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor // Now purple
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    "Create Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Login Link
            Text(
                text = "Already have an account? Login Now",
                modifier = Modifier.clickable {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                    activity?.finish()
                },
                color = AccentPink // Changed from blue to pink
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Social Login Options
            Text(
                text = "Or register with",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .clickable {
                            Toast.makeText(context, "Google registration coming soon!", Toast.LENGTH_SHORT).show()
                        }
                )

                Spacer(modifier = Modifier.width(20.dp))

                Image(
                    painter = painterResource(R.drawable.fb),
                    contentDescription = null,
                    modifier = Modifier
                        .height(40.dp)
                        .width(50.dp)
                        .clickable {
                            Toast.makeText(context, "Facebook registration coming soon!", Toast.LENGTH_SHORT).show()
                        }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun RegistrationPreviewBody() {
    RegistrationBody()
}
