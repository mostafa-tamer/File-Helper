import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import model.MatchedFile


@Composable
fun SearchScreen(searchType: SearchType, onBackPressed: () -> Unit) {

    val viewModel = remember { SearchViewModel(searchType) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        floatingActionButton = {
            IconButton(
                onClick = {
                    if (viewModel.matchedFiles.isNotEmpty()) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(viewModel.matchedFiles.size - 1)
                        }
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.rotate(-90f)
                )
            }
        }
    ) {
        Column(
            Modifier.fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Top(onBackPressed, searchType)
            Inputs(viewModel)
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.matchedFiles) { file ->
                    FileCard(file = file)
                }
            }
            Row {
                Text("Time elapsed: ${String.format("%.3f", viewModel.elapsedTime)} seconds")
                Spacer(Modifier.width(32.dp))
                Text("Matched results: ${viewModel.matchedFiles.size}")
            }
        }
    }
}


@Composable
fun FileCard(file: MatchedFile) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = file.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(250.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                SelectionContainer {
                    Text(
                        text = file.path,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Top(onBackPressed: () -> Unit, searchType: SearchType) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onBackPressed,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            searchType.toString(),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun Inputs(
    viewModel: SearchViewModel,
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = viewModel.wordToSearch,
            placeholder = {
                Text("Token")
            },
            onValueChange = { viewModel.wordToSearch = it }
        )
        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            modifier = Modifier.width(300.dp),
            value = viewModel.path,
            placeholder = {
                Text("Path")
            },
            onValueChange = { viewModel.path = it }
        )
        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = {
                viewModel.find()
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

