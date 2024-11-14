import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import model.MatchedFile
import strategies.FindFilePerContentParallelWithCompletableFutre
import strategies.FindFilePerContentParallelWithForkJoin
import strategies.FindFilePerContentSequential
import strategies.FindFilePerNameParallelWithCompletableFuture
import strategies.FindFilePerNameSequential
import strategies.search_strategy.SearchStrategy
import java.util.*
import kotlin.system.measureTimeMillis

class SearchViewModel(val searchType: SearchType) {

    private var timer = Timer()
    private var startTime: Long = 0L
    var elapsedTime by mutableStateOf(0.0)

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var job: Job? = null

    fun find() {
        startTime = System.currentTimeMillis()
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0
            }
        }, 0, 10)

        job?.let {
            if (it.isActive) {
                it.cancel()
                job = null
                println("job is cancelled")
            }
        }

        job = coroutineScope.launch {
            println("start")
            val time = measureTimeMillis {
                matchedFiles.clear()

                when (searchType) {
                    SearchType.FileName.ParallelSearchWithCompletableFuture -> {
                        val findFilePerNameParallel: FindFilePerNameParallelWithCompletableFuture =
                            SearchStrategy.newFilePerNameParallelWithCompletableFuture(path, wordToSearch)
                        findFilePerNameParallel.matchedFiles.forEach(matchedFiles::add)
                    }

                    SearchType.FileName.SequentialSearch -> {
                        val findFilePerNameSequential: FindFilePerNameSequential =
                            SearchStrategy.newFindFilePerNameSequential(path, wordToSearch)
                        findFilePerNameSequential.matchedFiles.forEach(matchedFiles::add)
                    }

                    SearchType.FileContent.SequentialSearch -> {
                        val findFilePerName: FindFilePerContentSequential =
                            SearchStrategy.newFindFilePerContentSequential(path, wordToSearch)
                        findFilePerName.matchedFiles.forEach(matchedFiles::add)
                    }

                    SearchType.FileContent.ParallelSearchWithForkJoin -> {
                        val findFilePerName: FindFilePerContentParallelWithForkJoin =
                            SearchStrategy.newFindFilePerContentParallelWithForkJoin(path, wordToSearch)
                        findFilePerName.matchedFiles.forEach(matchedFiles::add)
                    }

                    SearchType.FileContent.ParallelSearchWithCompletableFuture -> {
                        val findFilePerName: FindFilePerContentParallelWithCompletableFutre =
                            SearchStrategy.newFindFilePerContentParallelWithCompletableFutre(path, wordToSearch)
                        findFilePerName.matchedFiles.forEach(matchedFiles::add)
                    }

                    else -> throw IllegalStateException("Invalid Search Type")
                }
            }
            timer.cancel()

            println("time taken: ${time / 1000.0}")
            println("matched result: ${matchedFiles.size}")
        }
    }

    val matchedFiles = mutableStateListOf<MatchedFile>()

    var wordToSearch by mutableStateOf("")
    var path by mutableStateOf("")
    //vas
    //C:\Users\mtame\Desktop\root
}