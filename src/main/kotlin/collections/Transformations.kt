package collections

object TransformationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    mapping()
    zipping()
    association()
    flattening()
  }

  // операция трансформации mapping() создает новую коллецию на
  // основе функции, работающей с элементами другой коллекции
  private fun mapping() {
    println("mapping(): ")
    val numbers = setOf(1, 2, 3)
    println(numbers.map{it *2})
    println(numbers)
    println(numbers.mapIndexed {idx, value -> value * idx} )
    val numbers2 = setOf(1, 2, 3, 4, 5, 6)
    println(numbers2.mapNotNull{if (it == 6) null else it * 3})
    println(numbers2.mapIndexedNotNull {
      idx, value -> if (idx == 0) null else value * idx
    })
    println("-")
    var numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3,
            "key11" to 11)
    // преобразование ключей отображения
    numbersMap = numbersMap.mapKeys { it.key.toUpperCase() }
    println(numbersMap)
    // преобразование значений отображения
    numbersMap = numbersMap.mapValues {it.value + it.key.length}
    println(numbersMap)
    println("-----------------------------------")
  }

  // zipping() - построение пар из элементов с одинаковыми
  // позициями в обоих коллекциях
  private fun zipping() {
    println("zipping(): ")
    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")
    println(colors zip animals) // инфиксная форма
    val twoAnimals = listOf("fox", "bear")
    // размер коллекции после zip, равен размеру наименьшей из двух
    // коллекций
    println(colors.zip(twoAnimals)) // обычная форма
    println("-")
    // функция zip() может иметь два параметра: т.е. два элемента
    // из двуз коллекций
    println(colors.zip(animals) {color, animal ->
      "The ${animal.capitalize()} is $color"})
    // обратная трансформация  - unzipping(), на основе списка пар
    // создаются две коллекции
    val numberPairs = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4)
    println(numberPairs.unzip())
    println("-----------------------------------")
  }

  // операция ассоциации позволяет формировать отображения из
  // элементов коллекций и определенных значений, ассоциированных
  // с ними
  private fun association() {
    println("association(): ")
    val numbers = listOf("one", "two", "three", "four")
    // возвращаемое значение будет ключем для элемента
    // если встречено повторяющееся значение для ключа, только эта
    // пара останется в отображеении
    println(numbers.associateBy { it.length })
    var i = 0
    val numbers2 = numbers.associateBy {
      i++
      it + i // возвращаемое значение будет ключем для элемента
    }
    println(numbers2)
    println(numbers.associateBy {it.first().toUpperCase()})
    // можно не только создавать ключи, но также трансформировать
    // и сами значения
    println(numbers.associateBy(keySelector = { it.first().toUpperCase() },
            valueTransform = { it.length }))

    /*
     *Другим способом построения карт, в которых и ключи, и значения
     * так или иначе производятся из элементов исходной коллекции,
     * является функция associate (). Требуется лямбда-функция,
     * возвращающая Pair: ключ и значение соответствующей записи
     * для отображения. Функцию associate () следует использовать, когда
     * производительность не является критической или она более
     * предпочтительна, чем другие опции.
     */
    fun parseFullName(fullName: String): ClassForParse {
      val list = fullName.split(" ")
      return ClassForParse(list[0], list[1])
    }
    val names = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
    println(names.associate {
      name -> parseFullName(name).let { it.name to it.lastName}
    })
    println("-----------------------------------")
  }

  // функции сглаживания обеспечивают доступ к элементам вложенных
  // коллекций
  private fun flattening() {
    println("flattening():")

    val numbersSet = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
    // вернется список элементов из вложенных коллеций
    println(numbersSet.flatten())
    println("-----------------------------------")
  }
}

data class ClassForParse(val name: String, val lastName: String)