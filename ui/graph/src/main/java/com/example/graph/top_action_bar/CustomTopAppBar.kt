package com.example.graph.top_action_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.Icons.MENU_BURGER_ICON
import com.example.datastore.DataStore
import com.example.graph.sound
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    drawerState: DrawerState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    title: String
) {
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)

    TopAppBar(
        title = { Text(title, fontSize = 22.sp,modifier = Modifier.padding(start = 15.dp)) },
        scrollBehavior = topAppBarScrollBehavior,
        navigationIcon = {
            Icon(
                painterResource(MENU_BURGER_ICON),
                null,
                modifier = Modifier.clickable {
                    scope.launch {
                        sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                        drawerState.open()
                    }
                }
            )
        }
    )
}