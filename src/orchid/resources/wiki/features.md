1. Multidimensional array of arbitrary type (e.g. `String`):
```kotlin
val array: MultiArray<String> = MultiArray.new(2, 3) { (i, j) -> "($i,$j)" }
```
   
2. Multidimensional array of `Int`s:
```kotlin
val array = IntMultiArray.new(3, 2) { (i, j) -> 10*i + j }
```
