package com.example.deeplinkingwithjetpackcompose

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_SUBJECT
import android.content.Intent.EXTRA_TEXT
import android.content.Intent.createChooser
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.deeplinkingwithjetpackcompose.ui.theme.DeepLinkingWithJetPackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeepLinkingWithJetPackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    DeepLinking(intent)
                }
            }
        }
    }
}

@Composable
fun DeepLinking(intent: Intent) {

    val deepLinkMsg = remember { mutableStateOf("") }
    val context = LocalContext.current

    val uri: Uri? = intent.data
    if (uri != null) {
        val parameters: List<String> = uri.getPathSegments()
        val param = parameters[parameters.size - 1]
        deepLinkMsg.value = param
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(6.dp),
            text = "Deep Links in Android",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.padding(6.dp),
            text = deepLinkMsg.value,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Button(onClick = {
            try {
                startActivity(context, createChooser(Intent(ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(EXTRA_SUBJECT, "My Application")
                    var shareMessage = "Let me recommend you this application \n\n https://deeplinkingwithjetpackcompose.page.link/b"
                    putExtra(EXTRA_TEXT, shareMessage)
                }, "choose one"), null)
            } catch (e: Exception) {
                e.toString()
            }

        }) {
            Text(text = "Share")
        }


    }
}