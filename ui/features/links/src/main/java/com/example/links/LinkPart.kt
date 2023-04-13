package com.example.links

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.common_ui.ImageDisplayed
import com.example.local.model.Link
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState

@Composable
fun LinkPart(
    swipeable: Boolean,
    link: Link,
    onSwipe: () -> Unit
) {
    val uriHand = LocalUriHandler.current
    val swipeState = rememberSwipeableActionsState()

    val action = SwipeAction(
        onSwipe = {
            onSwipe.invoke()
        },
        icon = {},
        background = Color.Transparent
    )

    if (swipeable) {
        SwipeableActionsBox(
            modifier = Modifier,
            backgroundUntilSwipeThreshold = Color.Transparent,
            endActions = listOf(action),
            swipeThreshold = 100.dp,
            state = swipeState
        ) {
            LinkCard(
                swipeable = swipeable,
                id = link.id,
                title = link.title ?: "",
                host = link.host,
            ) {
                uriHand.openUri(link.url)
            }
        }
    } else {
        LinkCard(
            swipeable = swipeable,
            id = link.id,
            title = link.title ?: "",
            host = link.host,
        ) {
            uriHand.openUri(link.url)
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
private fun LinkCard(
    linkVM: LinkVM = hiltViewModel(),
    swipeable: Boolean,
    title: String,
    host: String,
    id: String,
    onClick: () -> Unit
) {
    val ctx = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (swipeable) 20.dp else 0.dp),
        shape = if (swipeable) roundedForCard else RoundedCornerShape(15.dp),
        onClick = {
            onClick.invoke()
        }
    ) {
        linkVM::imageDecoder.invoke(ctx, id)?.let {
            Image(
                modifier = Modifier.size(100.dp),
                bitmap = it,
                contentDescription = null)
        }

//        Row {
//
//            Column {
//                Text(
//                    text = title,
//                    fontSize = 13.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier.padding(start = 5.dp)
//                )
//                Text(
//                    text = host,
//                    fontSize = 11.sp,
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier.padding(start = 5.dp)
//                )
//
//            }
//        }
    }
}

private val roundedForCard = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)

