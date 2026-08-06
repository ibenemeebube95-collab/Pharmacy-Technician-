package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R

@Composable
fun WatermarkBackground(
    modifier: Modifier = Modifier,
    alpha: Float = 0.08f,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Red Pharmacy Technician watermark logo in the background
        Image(
            painter = painterResource(id = R.drawable.img_dashboard_watermark),
            contentDescription = "Pharmacy Technician Watermark",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.Center)
                .alpha(alpha)
        )

        content()
    }
}
