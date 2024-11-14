sealed class SearchType {
    sealed class FileName : SearchType() {
        data object SequentialSearch : FileName() {

            override fun toString() = "Filter Files Per File Name Sequentially"
        }

        data object ParallelSearchWithCompletableFuture : FileName() {

            override fun toString() = "Filter Files Per File Name Parallel With Completable Future"
        }
    }

    sealed class FileContent : SearchType() {
        data object SequentialSearch : FileContent() {
            override fun toString() = "Filter Files Per Content Sequentially"
        }

        data object ParallelSearchWithCompletableFuture : FileContent() {
            override fun toString() = "Filter Files Per Content Parallel With Completable Future"
        }

        data object ParallelSearchWithForkJoin : FileContent() {
            override fun toString() = "Filter Files Per Content Parallel With Fork Join"
        }

    }

    data object Unspecified : SearchType() {
        override fun toString() = "Unspecified"
    }
}
