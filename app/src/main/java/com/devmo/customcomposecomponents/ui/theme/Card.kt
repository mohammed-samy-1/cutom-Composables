package com.devmo.customcomposecomponents.ui.theme

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import com.devmo.customcomposecomponents.R
import com.steliospapamichail.creditcardmasker.viewtransformations.CardNumberMask
import java.util.IdentityHashMap


fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CreditCard(
    modifier: Modifier = Modifier,
    flipped: Boolean = false
) {
    val rotateY = animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = 500
        )
    )
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1.586f)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF280028))
            .graphicsLayer {
                rotationY = rotateY.value
                rotationX = 0f
                rotationZ = 0f
            }
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
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath,
                color = Color(0xC32E002E)
            )
            drawPath(
                path = lightColoredPath,
                color = Color(0xFF33003F)
            )
        }

        if (!flipped)
            CardFront(
                modifier = modifier,
                year = 23,
                month = 12,
                cardHolderName = "mohammed samy"
            )
        else
            CreditCardBack()

    }

}

@Composable
fun CardFront(
    modifier: Modifier,
    cardNumber: String = "xxxx xxxx xxxx xxxx",
    year: Int,
    month: Int,
    cardHolderName: String
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
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(Modifier.align(Alignment.BottomCenter)) {


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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
                    textAlign = TextAlign.End
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = cardHolderName,
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "ExDate",
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.End
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
    Box(
        modifier = Modifier
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
            Box(modifier = Modifier.fillMaxWidth()
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
                    .padding(horizontal = 40.dp )
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
    var rotat by remember {
        mutableStateOf(false)
    }
    Column {
        CreditCard(flipped = rotat)
        Button(onClick = { rotat = !rotat }) {
            Text(text = "flip")
        }
        var text by remember { mutableStateOf("") }


        PaymentInputForm()
    }

}

@Composable
fun PaymentInputForm() {
    var cardholderName by remember { mutableStateOf("") }
    var cardholderNameActive by remember { mutableStateOf(false) }
    var cardNumber by remember { mutableStateOf("") }
    var cardNumberActive by remember { mutableStateOf(false) }
    var expirationDate by remember { mutableStateOf("") }
    var expirationDateActive by remember { mutableStateOf(false) }
    var cvv by remember { mutableStateOf("") }
    var cvvActive by remember { mutableStateOf(false) }
    if(cardNumberActive){
        Toast.makeText(LocalContext.current , "focus" , Toast.LENGTH_LONG).show()
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Cardholder Name

        OutlinedTextField(
            value = cardholderName,
            onValueChange = {
                cardholderName = it
            },
            label = { Text("Cardholder Name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card Number
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card Number") },
            visualTransformation = CardNumberMask("-"),
            modifier = Modifier.onFocusChanged { cardNumberActive =  it.isFocused }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Expiration Date
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text("Expiration Date") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // CVV
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text("CVV") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = { /* Handle submit button click */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }
}

private fun formatCreditCardNumber(input: String): String {
    // Remove any non-digit characters from the input
    val digitsOnly = input.replace(Regex("[^\\d]"), "")

    // Insert a space after every fourth character
    val formattedText = StringBuilder()
    for (i in digitsOnly.indices) {
        if (i > 0 && i % 4 == 0) {
            formattedText.append(" ")
        }
        formattedText.append(digitsOnly[i])
    }

    return formattedText.toString()
}



