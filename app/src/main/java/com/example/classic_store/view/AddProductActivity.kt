package com.example.classic_store.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.classic_store.model.ProductModel
import com.example.classic_store.repository.ProductRepositoryImpl
import com.example.classic_store.viewmodel.ProductViewModel

// ONLY CHANGED COLORS - Updated to Purple & Pink theme
private val PrimaryPurple = Color(0xFF7B1FA2)
private val PrimaryPurpleDark = Color(0xFF4A148C)
private val AccentPink = Color(0xFFE91E63)
private val AccentGold = Color(0xFFFF9800)
private val BackgroundLight = Color(0xFFFCE4EC)

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AddProductScreen()
        }
    }
}

@Composable
fun AddProductScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    AddProductBody(
        selectedImageUri = selectedImageUri,
        onPickImage = {
            imagePickerLauncher.launch("image/*")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    var recyclableItemName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val context = LocalContext.current
    val activity = context as? Activity

    Scaffold(
        containerColor = BackgroundLight,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Product",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryPurple
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                // Header Card with motivational message
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Product",
                            tint = AccentPink,
                            modifier = Modifier.size(82.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Add your premium product!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PrimaryPurpleDark,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            item {
                // Image Selection Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                2.dp,
                                if (selectedImageUri != null) PrimaryPurple else Color.Gray.copy(alpha = 0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onPickImage() },
                        contentAlignment = Alignment.Center
                    ) {
                        if (selectedImageUri != null) {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Selected Image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Photo",
                                    tint = PrimaryPurple,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Tap to add photo",
                                    color = PrimaryPurpleDark,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Show your product",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            item {
                // Form Fields Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Product Name Field
                        OutlinedTextField(
                            value = recyclableItemName,
                            onValueChange = { recyclableItemName = it },
                            label = { Text("Product Name") },
                            placeholder = { Text("e.g., Smartphone, Laptop, Headphones") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Product Name",
                                    tint = PrimaryPurple
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryPurple,
                                focusedLabelColor = PrimaryPurple,
                                cursorColor = PrimaryPurple
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Price Field
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("Price (₹)") },
                            placeholder = { Text("Enter product price") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Price",
                                    tint = AccentPink
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentPink,
                                focusedLabelColor = AccentPink,
                                cursorColor = AccentPink
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Description Field
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            placeholder = { Text("Describe product features, condition, specifications...") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Description",
                                    tint = AccentGold
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 4,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentGold,
                                focusedLabelColor = AccentGold,
                                cursorColor = AccentGold
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            item {
                // Add Product Button
                Button(
                    onClick = {
                        when {
                            selectedImageUri == null -> {
                                Toast.makeText(
                                    context,
                                    "Please select an image first",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            recyclableItemName.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "Please enter product name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            price.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "Please enter price",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            description.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "Please enter description",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                try {
                                    val priceValue = price.toDouble()
                                    viewModel.uploadImage(context, selectedImageUri) { imageUrl ->
                                        if (imageUrl != null) {
                                            val model = ProductModel(
                                                "",
                                                recyclableItemName,
                                                priceValue,
                                                description,
                                                imageUrl
                                            )
                                            viewModel.addProduct(model) { success, message ->
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                                if (success) activity?.finish()
                                            }
                                        } else {
                                            Log.e("Upload Error", "Failed to upload image to Cloudinary")
                                            Toast.makeText(
                                                context,
                                                "Failed to upload image. Please try again.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                } catch (e: NumberFormatException) {
                                    Toast.makeText(
                                        context,
                                        "Please enter a valid price",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPurple,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Add Product",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductPreview() {
    AddProductBody(
        selectedImageUri = null,
        onPickImage = {}
    )
}
