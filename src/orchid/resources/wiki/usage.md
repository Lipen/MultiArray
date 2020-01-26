* Multidimensional array of arbitrary type (e.g. `String`):

    ```kotlin
    val array: MultiArray<String> = MultiArray.create(2, 3) { (i, j) -> "[$i,$j]" }
    ```

* Multidimensional array of `Int`s:

    ```kotlin
    val array = IntMultiArray.create(3, 2) { (i, j) -> 10*i + j }
    ```
