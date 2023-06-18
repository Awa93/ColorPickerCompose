package com.example.colorpicker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.colorpicker.screens.ColorBar
import com.example.colorpicker.screens.ColorBox
import com.example.colorpicker.ui.theme.ComposeColorPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           InitScreen()
        }
    }
}


@Composable
fun InitScreen(){
    ComposeColorPickerTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val hsv = remember {
                val hsv = floatArrayOf(0f, 0f, 0f)
                android.graphics.Color.colorToHSV(Color.Blue.toArgb(), hsv)

                mutableStateOf(
                    Triple(hsv[0], hsv[1], hsv[2])
                )
            }
            val backgroundColor = remember(hsv.value) {
                mutableStateOf(Color.hsv(hsv.value.first, hsv.value.second, hsv.value.third))
            }

            ColorBox(hue = hsv.value.first) { sat, value ->
                hsv.value = Triple(hsv.value.first, sat, value)
            }

            Spacer(modifier = Modifier.height(32.dp))

            ColorBar { hue ->
                hsv.value = Triple(hue, hsv.value.second, hsv.value.third)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(backgroundColor.value)
            )

            val colorCode = remember {
                mutableStateOf("")
            }

            colorCode.value = Integer.toHexString(backgroundColor.value.toArgb())

            Text(
                text = "#${colorCode.value}",
                modifier= Modifier.padding(10.dp),
            )

            Log.d("ColorCode : ", "initScreen: " )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InitScreen()
}