import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
            var searchType: SearchType by remember { mutableStateOf(SearchType.Unspecified) }
            if (searchType == SearchType.Unspecified) {
                MainScreen(onSelectSearchType = {
                    searchType = it
                })
            } else {
                SearchScreen(searchType, onBackPressed = {
                    searchType = SearchType.Unspecified
                })
            }
        }
    }
}

fun main() = application {
    val windowState = rememberWindowState(size = DpSize(width = 1080.dp, height = 720.dp))
    Window(onCloseRequest = ::exitApplication, state = windowState) {
        App()
    }
}
