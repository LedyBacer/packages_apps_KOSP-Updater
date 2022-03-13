package com.krypton.updater.ui

import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.krypton.updater.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Screen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen() {
        var visible by remember { mutableStateOf(false) }
        val heightFraction: Float by animateFloatAsState(if (visible) 0.4f else 1f)
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = if (visible) Arrangement.Bottom else Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(fraction = heightFraction),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.ic_updater_logo),
                        contentDescription = "Updater logo"
                    )
                    Text(
                        modifier = Modifier.padding(top = 32.dp),
                        text = stringResource(
                            id = R.string.system_build_info_format,
                            "2022-03-12",
                            "2.5"
                        )
                    )
                    Button(
                        modifier = Modifier.padding(top = 32.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = { visible = !visible }) {
                        Text(text = stringResource(id = R.string.check_for_updates))
                    }
                    Text(
                        modifier = Modifier.padding(top = 32.dp),
                        text = stringResource(
                            id = R.string.last_checked_time_format,
                            "2022-03-12 2:30 pm"
                        )
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 32.dp, end = 12.dp, bottom = 12.dp)
                        .fillMaxHeight(fraction = 0.6f),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    NoUpdateCardContent()
                }
            }
        }
    }

    @Composable
    fun NoUpdateCardContent() {
        Box(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.align(Alignment.TopStart)) {
                Text(text = stringResource(id = R.string.up_to_date))
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.come_back_later)
                )
            }
        }
    }

    @Preview
    @Composable
    fun ScreenPreview() {
        Screen()
    }
}