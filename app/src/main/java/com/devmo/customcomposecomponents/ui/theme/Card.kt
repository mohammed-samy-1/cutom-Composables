package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import com.devmo.customcomposecomponents.R
import com.steliospapamichail.creditcardmasker.utils.CardType
import com.steliospapamichail.creditcardmasker.utils.getCardTypeFromNumber
import com.steliospapamichail.creditcardmasker.viewtransformations.CardNumberMask
import com.steliospapamichail.creditcardmasker.viewtransformations.ExpirationDateMask
import kotlinx.coroutines.delay

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
        val gradient = listOf(
            Color(0xFFB39DDB), // Light purple
            Color(0xFF6A1B9A)  // Dark purple
        )
        //listOf(Color(0xFFDAA520), Color(0xFFFFDF7E), Color(0xFFB8860B), Color(0xFFFFD700))
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
            val height = size.height - 25

            drawContent()

            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)

                // Destination
                drawRect(
                    color = Color.Gray,
                    topLeft = Offset(strokeWidthPx / 2, (strokeWidthPx / 2) + 12.5f),
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

@Composable
fun CreditCard() {
    val rotateY = animateFloatAsState(
        targetValue = if (CVVInput) 180f else 0f, animationSpec = tween(
            durationMillis = 500
        )
    )
    Box(modifier = Modifier.graphicsLayer {
        rotationY = rotateY.value
        rotationX = 0f
        rotationZ = 0f
        cameraDistance = 30f
    }) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(7.5.dp)
                .aspectRatio(1.586f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF3F003F))
        ) {
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
                    path = mediumColoredPath, color = Color(0xC34D004D)
                )
                drawPath(
                    path = lightColoredPath, color = Color(0x8058006D)
                )
            }
            var flip by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = CVVInput, block = {
                delay(100)
                flip = CVVInput
            })
            if (!flip) CardFront()
            else CreditCardBack()

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CardFront() {
    val cardTypeSrcId = when (getCardTypeFromNumber(cardNumber)) {
        CardType.VISA -> R.drawable.visa
        CardType.MASTERCARD -> R.drawable.mastercard
        CardType.AMERICAN_EXPRESS -> R.drawable.american_express
        CardType.JCB -> R.drawable.jcb
        CardType.MAESTRO -> R.drawable.maestro
        else -> R.drawable.placeholer

    }
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
                contentDescription = "Chip",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Fit
            )
            TextField(
                value = cardNumber,
                onValueChange = {},
                placeholder = {
                    Text(
                        "XXXX XXXX XXXX XXXX",
                        color = Color.White.copy(alpha = .8f)
                    )
                },
                visualTransformation = CardNumberMask(),
                modifier = Modifier
                    .animatedBorder(2.dp, 2000, isVisible = numberInput)
                    .focusable(false)
                    .widthIn(min = 150.dp, max = 270.dp)
                    .padding(horizontal = 10.dp),
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .animatedBorder(
                        2.dp,
                        2000,
                        isVisible = nameInput
                    )
                    .widthIn(min = 150.dp, max = 180.dp)
            ) {
                Text(
                    text = "CARDHOLDER NAME",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.offset(y = 15.dp, x = 15.dp)
                )
                TextField(
                    value = cardholderName, onValueChange = {},
                    placeholder = {
                        Text(
                            "Your name",
                            color = Color.White.copy(alpha = .8f),
                            modifier = Modifier.padding(0.dp)
                        )
                    },
                    readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    modifier = Modifier.widthIn(min = 150.dp, max = 180.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .animatedBorder(
                        2.dp,
                        2000,
                        isVisible = dateInput
                    )
                    .width(150.dp)
            ) {
                Text(
                    text = "EXPIRATION DATE",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .offset(y = 15.dp)
                        .padding(start = 15.dp, end = 10.dp)
                )
                TextField(
                    value = expirationDate, onValueChange = {},
                    placeholder = { Text("MM/YY", color = Color.White.copy(alpha = .8f)) },
                    visualTransformation = ExpirationDateMask(),
                    readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true

                )
            }

        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            AnimatedContent(
                targetState = cardTypeSrcId,
                transitionSpec = {
                    slideInVertically { if (cardTypeSrcId == R.drawable.placeholer) it else -it } with slideOutVertically { if (cardTypeSrcId == R.drawable.placeholer) -it else it }
                }
            ) {
                Image(
                    painter = painterResource(id = cardTypeSrcId),
                    contentDescription = "Card Chip",
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }


    }
}

@Composable
private fun CreditCardBack() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .graphicsLayer {
            rotationY = -180f
            rotationX = 0f
            rotationZ = 0f
        }
    ) {
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
                    .padding(3.dp)

            ) {
                Text(
                    text = cvv,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}


@Composable
fun CreditCardForm() {
    Column {
        CreditCard()
        PaymentInputForm()
    }
}

@Composable
private fun PaymentInputForm() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // Cardholder Name

        Spacer(modifier = Modifier.height(16.dp))

        // Card Number
        OutlinedTextField(value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card Number") },
            visualTransformation = CardNumberMask(),
            modifier = Modifier
                .onFocusChanged { numberInput = it.isFocused }
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(value = cardholderName, onValueChange = {
                if (it.length <= 16)
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
                onValueChange = { if (it.length <= 4) expirationDate = it },
                visualTransformation = ExpirationDateMask(),
                label = { Text("Date") },
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { dateInput = it.isFocused },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.width(16.dp))

            // CVV
            OutlinedTextField(
                value = cvv,
                onValueChange = {
                    if (it.length <= 3)
                        cvv = it
                },
                label = { Text("CVV") },
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged {
                        CVVInput = it.isFocused
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
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




