import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun MainScreen(onSelectSearchType: (SearchType) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(SearchType.FileName.SequentialSearch, onSelectSearchType)

                CustomButton(SearchType.FileName.ParallelSearchWithCompletableFuture, onSelectSearchType)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(SearchType.FileContent.SequentialSearch, onSelectSearchType)

                CustomButton(SearchType.FileContent.ParallelSearchWithCompletableFuture, onSelectSearchType)

                CustomButton(SearchType.FileContent.ParallelSearchWithForkJoin, onSelectSearchType)
            }
        }
    }
}

@Composable
private fun CustomButton(searchType: SearchType, onSelectSearchType: (SearchType) -> Unit) {
    Card {
        Box(
            Modifier.size(180.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    onSelectSearchType(searchType)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                searchType.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}