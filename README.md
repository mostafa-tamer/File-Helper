# File Helper

**File Helper** is a desktop application built with [Jetpack Compose for Desktop](https://www.jetbrains.com/lp/compose-desktop/) and Java, designed to provide efficient and flexible file searching capabilities. Whether you need to search by file name or content, **File Helper** offers multiple search strategies to optimize performance and meet your specific needs.

## Features

- **Search by File Name**:
  - **Sequential Search**: Simple, step-by-step file name searching.
  - **Parallel Search with CompletableFuture**: Enhanced performance using Java's `CompletableFuture` for concurrent searches.

- **Search by File Content**:
  - **Sequential Search**: Line-by-line content searching.
  - **Parallel Search with CompletableFuture**: Concurrent content searching for faster results.
  - **Parallel Search with ForkJoin**: Utilizes the Fork/Join framework for efficient parallel processing.

- **User-Friendly Interface**:
  - Built with Jetpack Compose Desktop for a modern and responsive UI.