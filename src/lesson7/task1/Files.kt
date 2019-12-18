@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File


/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> = TODO()

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    var text = File(inputName).readText()
    var resultFile = File(outputName).bufferedWriter()
    var i = 0
    while (i <= text.length - 2) {
        resultFile.write(text[i].toString())
        if ((text[i] == 'ж') || (text[i] == 'Ж') || (text[i] == 'ш') || (text[i] == 'Ш') || (text[i] == 'ч')
            || (text[i] == 'Ч') || (text[i] == 'щ') || (text[i] == 'Щ')
        ) {
            when (text[i + 1]) {
                'ы' -> {
                    resultFile.write("и")
                    i++
                }
                'я' -> {
                    resultFile.write("а")
                    i++
                }
                'ю' -> {
                    resultFile.write("у")
                    i++
                }
                'Ы' -> {
                    resultFile.write("И")
                    i++
                }
                'Я' -> {
                    resultFile.write("А")
                    i++
                }
                'Ю' -> {
                    resultFile.write("У")
                    i++
                }
            }
        }
        i++
    }
    resultFile.write((text[text.length - 1]).toString())
    resultFile.close()

}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun format(lines: List<String>): List<String> {
    val ans = mutableListOf<String>()
    for (line in lines) {
        if (line == "") ans.add("")
        else {
            var s = ""
            var ss = ""
            var onlySpacesFound = true
            for (i in 0 until line.length) {
                if (onlySpacesFound) {
                    if (line[i] != ' ') {
                        onlySpacesFound = false
                        s += line[i].toString()
                    }
                } else {
                    s += line[i].toString()
                }
            }
            onlySpacesFound = true
            for (i in (s.length - 1) downTo 0) {
                if (onlySpacesFound) {
                    if (line[i] != ' ') {
                        onlySpacesFound = false
                        ss = s[i].toString() + ss
                    }
                } else {
                    ss = s[i].toString() + ss
                }
            }
            ans.add(ss)
        }
    }
    return ans.toList()
}

fun centerFile(inputName: String, outputName: String) {
    val lines = format(File(inputName).readLines())
    val ans = File(outputName).bufferedWriter()
    var maxLength = 0
    for (line in lines) if (line.length > maxLength) maxLength = line.length
    for (line in lines) {
        for (i in 0 until ((maxLength - line.length) / 2)) ans.write(" ")
        ans.write("$line\n")
    }
    ans.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun isSpaces(line: String): Boolean {
    for (i in 0 until line.length) if (line[i] != ' ') return false
    return true
}

fun format1(lines: List<String>): List<String> {
    val ans = mutableListOf<String>()
    for (line in lines) {
        if (isSpaces(line)) ans.add("")
        else {
            var s = ""
            var ss = ""
            var onlySpacesFound = true
            var spacesSequence = false
            for (i in 0 until line.length) {
                if (onlySpacesFound) {
                    if (line[i] != ' ') {
                        onlySpacesFound = false
                        s += line[i].toString()
                    }
                } else {
                    if (line[i] == ' ') {
                        if (!spacesSequence) {
                            s += line[i].toString()
                            spacesSequence = true
                        }
                    } else {
                        spacesSequence = false
                        s += line[i].toString()
                    }
                }
            }
            onlySpacesFound = true
            for (i in s.length - 1 downTo 0) {
                if (onlySpacesFound) {
                    if (s[i] != ' ') {
                        onlySpacesFound = false
                        ss = s[i].toString() + ss
                    }
                } else ss = s[i].toString() + ss
            }
            ans.add(ss)
        }
    }
    return ans.toList()
}

fun spaceCounter(line: String): Int {
    var counter = 0
    for (i in 0 until line.length) if (line[i] == ' ') counter++
    return counter
}

fun insertSpaces(line: String, spaces: Int): String {
    if (line == "") return ""
    if (spaceCounter(line) == 0) return line
    var r = ""
    var buff = ""
    for (i in 0..(spaces / spaceCounter(line))) r += " "
    for (i in 0 until line.length) {
        if (line[i] != ' ') buff += line[i].toString()
        else buff += r
    }
    var ans = ""
    var counter = spaces % spaceCounter(line)
    var i = 0
    while (counter != 0) {
        if (buff[i] == ' ') {
            while (buff[i + 1] == ' ') i++
            ans = "$ans$r "
            counter--
        } else {
            ans += buff[i].toString()
        }
        i++
    }
    for (a in i until buff.length) ans += buff[a].toString()
    return ans
}

fun alignFileByWidth(inputName: String, outputName: String) {
    val lines = format1(File(inputName).readLines())
    val ans = File(outputName).bufferedWriter()
    var maxLength = 0
    for (line in lines) if (line.length > maxLength) maxLength = line.length
    for (line in lines) {
        ans.write("${insertSpaces(line, (maxLength - line.length))}\n")
    }
    ans.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val line = File(inputName).readText()
    val ans = File(outputName).bufferedWriter()
    val d = mutableMapOf<Char, String>()
    for ((key, value) in dictionary) d[key.toLowerCase()] = value
    for (symbol in line) {
        if (d.containsKey(symbol.toLowerCase())) {
            if (symbol.isLowerCase()) ans.write(d[symbol.toLowerCase()]?.toLowerCase().toString())
            else {
                val str = d[symbol.toLowerCase()].toString()
                ans.write(str[0].toUpperCase().toString())
                if (str.length > 1) for (i in 1 until str.length) ans.write(str[i].toLowerCase().toString())
            }
        } else ans.write("$symbol")
    }
    ans.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */

fun isChaotic(word: String): Boolean {
    val list = mutableListOf<Char>()
    for (symbol in word) {
        if (list.contains(symbol.toLowerCase())) return false
        else list.add(symbol.toLowerCase())
    }
    return true
}

fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val words = (File(inputName).readLines().sortedBy { it.length }).toMutableList()
    var i = 0
    while (true) {
        if ((!isChaotic(words[i])) || (words[i].length < words.last().length)) {
            words.removeAt(i)
            i--
        }
        i++
        if (i == words.size) break
    }
    val ans = File(outputName).bufferedWriter()
    ans.write(words.joinToString(", "))
    ans.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */

/*
 * - * -> <i>
 * - ** -> <b>
 * - ~~ -> <s>
 * - *** - <b><i>
 */

fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val text = File(inputName).readText()
    val ans = File(outputName).bufferedWriter()
    ans.write("<html>\n<body>\n<p>")
    var i = 0
    var iflag = false
    var bflag = false
    var sflag = false
    while (i <= text.length - 3) {
        if (text[i] == '*') {
            if (text[i + 1] == '*') {
                if (text[i + 2] == '*') {
                    if ((iflag) && (bflag)) ans.write("</b></i>")
                    else ans.write("<b><i>")
                    iflag = !iflag
                    bflag = !bflag
                    i += 2
                } else {
                    if (bflag) ans.write("</b>") else ans.write("<b>")
                    bflag = !bflag
                    i++
                }
            } else {
                if (iflag) ans.write("</i>") else ans.write("<i>")
                iflag = !iflag
            }
        } else if ((text[i] == '~') && (text[i + 1] == '~')) {
            if (sflag) ans.write("</s>") else ans.write("<s>")
            sflag = !sflag
            i++
        } else if ((text[i] == '\r') && (text[i + 1] == '\n') && (text[i + 2] == '\r') && (text[i + 3] == '\n')) {
            ans.write("\n</p>\n<p>")
            i++
        } else {
            ans.write(text[i].toString())
        }
        i++
    }
    if (i == text.length - 1) ans.write(text[text.length - 1].toString())
    else if (i == text.length - 2) {
        if (text[i] == '*') {
            if (text[i + 1] == '*') ans.write("</b>")
            else ans.write("</i>")
        } else if ((text[i] == '~') && (text[i + 1] == '~')) ans.write("</s>")
        else ans.write(text[i].toString() + text[i + 1].toString())
    }
    ans.write("\n</p>\n</body>\n</html>")
    ans.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

