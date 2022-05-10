package com.rulhouse.autofitwidthtext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rulhouse.autofitwidthtext.ui.theme.AutoFitWidthTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoFitWidthTextTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize(0.5f)
                        ) {
                            val listString = mutableListOf<String>()
                            for(i in 0..100) {
                                if (i == 0) {
                                    listString.add(i.toString())
                                } else {
                                    listString.add(listString[i - 1] + i.toString())
                                }
                            }
                            Column {
                                val text = remember { mutableStateOf("")}
                                val index = remember { mutableStateOf(0)}
                                Button(
                                    onClick = {
                                        index.value++
                                        if (index.value > listString.size - 1) index.value = 0
                                        text.value = listString[index.value]
                                    }
                                ) {
                                    Text(text = index.value.toString())
                                }
                                AutoFitWidthText(
                                    modifier = Modifier,
                                    text = text.value
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}