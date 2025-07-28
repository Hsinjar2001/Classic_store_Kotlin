package com.example.classic_store.view

import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.classic_store.R

// ONLY CHANGED COLORS - Updated to Purple & Pink theme
private val PrimaryPurple = Color(0xFF7B1FA2)
private val PrimaryPurpleDark = Color(0xFF4A148C)
private val AccentPink = Color(0xFFE91E63)
private val AccentGold = Color(0xFFFF9800)
private val BackgroundLight = Color(0xFFFCE4EC)

class ResetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResetPasswordBody()
        }
    }
}

@Composable
fun ResetPasswordBody() {
    var email by remember { mutableStateOf("") }
    var isEmailSent by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val activity = context as? Activity

    // Email validation function
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = BackgroundLight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Back Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        activity?.finish()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryPurpleDark
                    )
                }
            }

            if (!isEmailSent) {
                // Reset Password Form
                Text(
                    text = "Reset Password",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurpleDark
                )
                Text(
                    text = "Enter your email to reset password",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(50.dp))

                Image(
                    painter = painterResource(R.drawable.loginimg), // Using same image as login
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

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
                            tint = PrimaryPurple
                        )
                    },
                    placeholder = {
                        Text("Enter your email address")
                    },
                    value = email,
                    onValueChange = { input ->
                        email = input
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        focusedLabelColor = PrimaryPurple,
                        cursorColor = PrimaryPurple
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        if (email.isEmpty()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Please enter your email address")
                            }
                        } else if (!isValidEmail(email)) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Please enter a valid email address")
                            }
                        } else {
                            isLoading = true
                            // Simulate sending reset email
                            coroutineScope.launch {
                                kotlinx.coroutines.delay(2000) // Simulate network delay
                                isLoading = false
                                isEmailSent = true
                                Toast.makeText(
                                    context,
                                    "Password reset link sent to $email",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple,
                        contentColor = Color.White
                    )
                ) {
                    Text(if (isLoading) "Sending..." else "Send Reset Link")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Remember your password? Login",
                    color = AccentPink,
                    modifier = Modifier.clickable {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                        // to destroy activity
                        activity?.finish()
                    }
                )

            } else {
                // Success Screen
                Text(
                    text = "Email Sent!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentPink
                )

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(R.drawable.loginimg), // You can replace with success icon
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "We've sent a password reset link to:",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = PrimaryPurpleDark
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = email,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurpleDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Check your email and click the link to reset your password.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                        activity?.finish()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple,
                        contentColor = Color.White
                    )
                ) {
                    Text("Back to Login")
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Didn't receive email? Resend",
                    modifier = Modifier.clickable {
                        isEmailSent = false // Go back to form
                    },
                    color = AccentPink
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Updated Branding to Classic Store
            Text(
                text = "Classic Store",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple
            )
            Text(
                text = "Premium quality products",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun ResetPasswordPreviewBody() {
    ResetPasswordBody()
}

// Updated LoginBody with navigation to Reset Password
@Composable
fun UpdatedLoginBody() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val activity = context as? Activity

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = BackgroundLight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to Classic Store",
                color = PrimaryPurpleDark,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Premium quality products",
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(R.drawable.loginimg),
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

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
                        tint = PrimaryPurple
                    )
                },
                placeholder = {
                    Text("abc@gmail.com")
                },
                value = username,
                onValueChange = { input ->
                    username = input
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    focusedLabelColor = PrimaryPurple,
                    cursorColor = PrimaryPurple
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(12.dp),
                visualTransformation =
                    if (passwordVisibility) PasswordVisualTransformation()
                    else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = AccentPink
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            if (passwordVisibility)
                                R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                        ),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            passwordVisibility = !passwordVisibility
                        },
                        tint = AccentGold
                    )
                },
                placeholder = {
                    Text("*******")
                },
                value = password,
                onValueChange = { input ->
                    password = input
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPink,
                    focusedLabelColor = AccentPink,
                    cursorColor = AccentPink
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { remember ->
                            rememberMe = remember
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = PrimaryPurple,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(text = "Remember me")
                }

                // Updated Forget Password with navigation
                Text(
                    text = "Forget Password",
                    modifier = Modifier.clickable {
                        val intent = Intent(context, ResetPasswordActivity::class.java)
                        context.startActivity(intent)
                    },
                    color = AccentPink
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (username == "Rahul" && password == "password") {
                        val intent = Intent(context, DashboardActivity::class.java)
                        intent.putExtra("username", username)
                        intent.putExtra("password", password)
                        context.startActivity(intent)
                        Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Invalid login")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryPurple,
                    contentColor = Color.White
                )
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "New user? Register Now",
                modifier = Modifier.clickable {
                    val intent = Intent(context, RegistrationActivity::class.java)
                    context.startActivity(intent)
                    activity?.finish()
                },
                color = AccentPink
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
                )

                Spacer(modifier = Modifier.width(10.dp))

                Image(
                    painter = painterResource(R.drawable.fb),
                    contentDescription = null,
                    modifier = Modifier
                        .height(40.dp)
                        .width(50.dp)
                )
            }
        }
    }
}
