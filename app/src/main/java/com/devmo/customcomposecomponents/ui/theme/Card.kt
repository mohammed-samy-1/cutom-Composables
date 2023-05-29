package com.devmo.customcomposecomponents.ui.theme

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import com.devmo.customcomposecomponents.R
import com.steliospapamichail.creditcardmasker.viewtransformations.CardNumberMask
import com.steliospapamichail.creditcardmasker.viewtransformations.ExpirationDateMask

var numberInput by mutableStateOf(false)
var dateInput by mutableStateOf(false)
var nameInput by mutableStateOf(false)
var CVVInput by mutableStateOf(false)
var cardholderName by mutableStateOf("")
var cardNumber by mutableStateOf("")
var expirationDate by mutableStateOf("")
var cvv by mutableStateOf("")
fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x, from.y, abs(from.x + to.x) / 2f, abs(from.y + to.y) / 2f
    )
}

fun Modifier.animatedBorder(
    strokeWith: Dp, animationDuration: Int, isVisible: Boolean = false
) = composed {
    if (isVisible) {
        val gradient =
            listOf(Color(0xFFDAA520), Color(0xFFFFDF7E), Color(0xFFB8860B), Color(0xFFFFD700))
        val infiniteTransition = rememberInfiniteTransition()
        val angle by infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
                animation = tween(animationDuration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        val brush = Brush.sweepGradient(gradient)
        Modifier.drawWithContent {

            val strokeWidthPx = strokeWith.toPx()
            val width = size.width
            val height = size.height

            drawContent()

            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)

                // Destination
                drawRect(
                    color = Color.Gray,
                    topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                    size = Size(width - strokeWidthPx, height - strokeWidthPx),
                    style = Stroke(strokeWidthPx)
                )

                // Source
                rotate(angle) {

                    drawCircle(
                        brush = brush,
                        radius = size.width,
                        blendMode = BlendMode.SrcIn,
                    )
                }

                restoreToCount(checkPoint)
            }
        }
    } else return@composed this@animatedBorder
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CreditCard(
    modifier: Modifier = Modifier
) {
    val rotateY = animateFloatAsState(
        targetValue = if (CVVInput) 180f else 0f, animationSpec = tween(
            durationMillis = 500
        )
    )
    BoxWithConstraints(modifier = Modifier
        .padding(7.5.dp)
        .aspectRatio(1.586f)
        .clip(RoundedCornerShape(10.dp))
        .background(Color(0xFF280028))
        .graphicsLayer {
            rotationY = rotateY.value
            rotationX = 0f
            rotationZ = 0f
        }) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        val color1 = Offset(0f, height * 0.3f)
        val color2 = Offset(width * 0f, height * 0.35f)
        val color3 = Offset(0.4f * width, height * 0.05f)
        val color4 = Offset(width * 0.75f, height * 0.7f)
        val color5 = Offset(width * 1.4f, height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(color1.x, color1.y)
            standardQuadFromTo(color1, color2)
            standardQuadFromTo(color2, color3)
            standardQuadFromTo(color3, color4)
            standardQuadFromTo(color4, color5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()

        }
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)
        val lightColoredPath = Path().apply {
            moveTo(color1.x, color1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath, color = Color(0xC32E002E)
            )
            drawPath(
                path = lightColoredPath, color = Color(0xFF33003F)
            )
        }

        if (!CVVInput) CardFront(
            modifier = modifier
        )
        else CreditCardBack()

    }

}

@Composable
fun CardFront(
    modifier: Modifier,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Row(

            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(bottom = 30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.chip),
                contentDescription = "Visa Logo",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = cardNumber,
                color = Color.White,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .animatedBorder(2.dp, 2000, isVisible = numberInput)
                    .padding(5.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(Modifier.align(Alignment.BottomCenter)) {


            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "CARDHOLDER NAME",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.caption,

                    )
                Text(
                    text = "EXPIRATION DATE",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.End,
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = cardholderName,
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .weight(1f)
                        .animatedBorder(
                            2.dp,
                            2000,
                            isVisible = nameInput
                        )
                )
                Text(
                    text = expirationDate,
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.End,
                    modifier = Modifier.animatedBorder(
                        2.dp,
                        2000,
                        isVisible = dateInput
                    )
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.visa),
            contentDescription = "Card Chip",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(50.dp)
        )
    }
}

@Composable
fun CreditCardBack() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .graphicsLayer {
            rotationY = -180f
            rotationX = 0f
            rotationZ = 0f
        }) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(45.dp)

            )

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "CVV",
                color = Color.White,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(horizontal = 40.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 40.dp)
                    .background(Color.White)
                    .padding(start = 3.dp)

            ) {
                Text(
                    text = "123",
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview
@Composable
fun Card() {
    Column {
        CreditCard()

        var text by remember { mutableStateOf("") }


        PaymentInputForm()

        Box(
            modifier = Modifier
                .size(400.dp, 70.dp)
                .background(Color.Black)
                .animatedBorder(1.dp, 500)
        )
    }

}

@Composable
fun PaymentInputForm() {


    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Cardholder Name

        Spacer(modifier = Modifier.height(16.dp))

        // Card Number
        OutlinedTextField(value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card Number") },
            visualTransformation = CardNumberMask(),
            modifier = Modifier.onFocusChanged { numberInput = it.isFocused })

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(value = cardholderName, onValueChange = {
                cardholderName = it
            }, label = { Text("Cardholder Name") },
                modifier = Modifier
                    .weight(2f)
                    .onFocusChanged { nameInput = it.isFocused }
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Expiration Date
            OutlinedTextField(
                value = expirationDate,
                onValueChange = { expirationDate = it },
                visualTransformation = ExpirationDateMask(),
                label = { Text("Date") },
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { dateInput = it.isFocused }
            )

            Spacer(modifier = Modifier.width(16.dp))

            // CVV
            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("CVV") },
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged {
                        CVVInput = it.isFocused
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = { /* Handle submit button click */ }, modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }
}




