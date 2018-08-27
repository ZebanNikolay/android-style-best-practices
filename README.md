# Большая стирка тем и стилей в Android

В докладе расскажу, как легко и без боли написать тему и стили для своего нового приложения или навести порядок в старом. На примере реальной проблемы захламлённости ресурсов, сучившейся у нас на проекте.  Мы пройдемся по каждому этапу преобразования и рассмотрим лучшие решения и практики для организации кода, а также распространённые ошибки.

## Summary

#### [Проблема](#Проблема-1)
#### [1.	Подготовка](#2Подготовка-1)
#### [2.	Создаем dimens.xml, colors.xml, strings.xml ресурсы](#1Создаем-dimensxml-colorsxml-stringsxml-ресурсы-1)
#### [3.	Создаем стили текста](#2Создаем-стили-текста-1)
#### [4.	Создаем файлы для компонентов](#3Создаем-файлы-для-компонентов-1)
#### [5.	Создаем тему нашего приложения](#4Создаем-тему-нашего-приложения-1)
#### [6.	Проходимся по нашим экранам перерабатывая на новый стиль](#5Проходимся-по-нашим-экранам-перерабатывая-на-новый-стиль-1)
#### [7.	Чистим и удаляем все deprecated стили](#6Чистим-и-удаляем-все-deprecated-стили-1)


### Проблема
Обычно стили добавляются по мере написания приложения, то есть программист пишет фитчу видит отступ или новый компонент на дизайне и добавляет его в ресурсы он не видит целостной картины оформления стилей. Так как программист сосредоточен на написании фитчи, он не прикладывает достаточное количество силы и внимания на написание стилей и оформление ресурсов. Из-за этой ситуации возникает такие ошибки как: дублирование кода, не правильное именование, нарушение ответственности. Если приложение разрабатывается много лет оно может очень сильно зарасти грузом неправильно написанных ресурсов, которые в свою очередь порождают еще больше плохого кода так как в них тяжело разобраться. В докладе я расскажу, как легко и без боли написать тему и стили для своего нового приложения или навести порядок в старом. На примере реальной проблемы захламлённости ресурсов, сучившейся у нас на проекте. Мы пройдемся по каждому этапу преобразования и рассмотрим лучшие решения и практики для организации кода, а также распространённые ошибки.

### 1.	Подготовка

Наша работа заключается в том, что мы должны написать заранее максимально полное стилевое ядро нашего приложения с четкой и понятной структурой как снаружи, так и внутри. Не явная грань или ее отсутствие первый шаг к бардаку в стилях. Четкая структура снаружи подразумевает, что явно прослеживается разделение стилей уровня приложения от стилей отдельной фитчи. Четкая структура внутри подразумевает, что мы легко понимаем где хранятся стили кнопок, а где текста.

Поэтому с самого начала желательно выделить наше стилевое ядро в отдельный модуль, в котором мы в дальнейшем будем вести разработку, но это по желанию так как это не критично.
В случае существующего проекта нам нужно пометить старые стили как deprecated, чтобы избежать путаницы и в дальнейшем полностью от них избавится.

### 2.	Создаем dimens.xml, colors.xml, strings.xml ресурсы
**dimens.xml**

Используйте имя `spacing_*` для **paddings** и **margins**.
Имейте в виду, что по [материал дизайну](https://material.io/design/layout/spacing-methods.html) компоненты должны выравниваться по шагу в 8dp и 4dp. А это значит, что у вас не должно существовать таких значений как 9dp, 2dp и уж темболее 7.5dp.

```xml
    <dimen name="spacing_huge">40dp</dimen>
    <dimen name="spacing_large">24dp</dimen>
    <dimen name="spacing_normal">16dp</dimen>
    <dimen name="spacing_small">8dp</dimen>
    <dimen name="spacing_tiny">4dp</dimen>

    <dimen name="icon_size">48dp</dimen>
    <dimen name="icon_size_small">24dp</dimen>
```

**colors.xml**

Цвета делятся на две группы первая это непоследственно сам цвет `black`, `red_dark`, `black_tr_38` из них мы сотовляем вторую группу `brand_primary` `text_light` `icon_dark`
```xml
    <color name="transparent">@android:color/transparent</color>

    <color name="black">#FF000000</color>
    <color name="black_tr_87">#DF000000</color>
    <color name="black_tr_54">#8A000000</color>
    <color name="black_tr_38">#61000000</color>

    <color name="white">#FFFFFFFF</color>
    <color name="white_tr_70">#B3FFFFFF</color>

    <color name="gray">#e5e5e5</color>

    <color name="red_dark">#B71C1C</color>
    <color name="red_medium">#F44336</color>

    <color name="yellow">#FFE735</color>
    <color name="orange">#F57F17</color>

    <color name="primary">@color/red_medium</color>
    <color name="primary_dark">@color/red_dark</color>
    <color name="accent">@color/black</color>

    <color name="background">@color/gray</color>
    <color name="surface">@color/white</color>

    <color name="text_light">@color/white</color>
    <color name="text_dark">@color/black_tr_87</color>
    <color name="text_colored">@color/colorAccent</color>
    <color name="text_error">@color/red_dark</color>

    <color name="icon_dark">@color/black_tr_87</color>
```

**strings.xml** и **strings_untranslatable.xml**
 
В **strings.xml** мы складываем общие строки уровня приложения
```xml
	<string name="app_name">Android style best practices</string>

	<string name="button_label_ok">OK</string>
	<string name="button_label_cancel">Cancel</string>
	<string name="button_label_download">Download</string>

	<string name="error_message_an_error">An error occurred</string>
	<string name="error_message_call">Call failed</string>
	<string name="error_message_download">File download failed</string>
```
В **strings_untranslatable.xml** складываем не переводимые строки типо ссылок или api ключей
```xml
	<string name="server_url" translatable="false">https://github.com/ZebanNikolay/android-style-best-practices</string>
	<string name="some_service_api_key" translatable="false">key</string>
```
Так же не забывайте если ваш файл **strings.xml** раздулся не стесьняйтесь его дробить на более мелкие **strings_errors.xml**, **strings_buttons.xml** и т.д.

### 3.	Создаем стили текста
Создаем файл **styles_text.xml** в нем будут хранится все стили предназначенные для стилизации текста. Добовляем в него два родительских стиля `Base.TextAppearance`, `TextAppearance` от них будут наследоваться все наши текстовые стили поэтому в них будут находится все общие параметры.
```xml
<style name="Base.TextAppearance">
	<item name="fontFamily">@string/font_roboto_regular</item>
	<item name="android:textColor">@color/text_dark</item>
	<item name="android:textStyle">normal</item>
	<item name="textAllCaps">false</item>
</style>

<style name="TextAppearance" parent="Base.TextAppearance"/>
```
Далее создаем все стили текстов нашего приложения наследуясь от основного стиля как в данном примере переопределяя или добовляя новые параметры.
```xml
<style name="Base.TextAppearance.Button">
	<item name="fontFamily">@string/font_roboto_medium</item>
	<item name="android:textStyle">bold</item>
	<item name="textAllCaps">true</item>
	<item name="android:textSize">14sp</item>
</style>

<style name="TextAppearance.Button" parent="Base.TextAppearance.Button"/>
<style name="TextAppearance.Button.Colored">
	<item name="android:textColor">@color/text_colored</item>
</style>
<style name="TextAppearance.Button.Inverse">
	<item name="android:textColor">@color/text_light</item>
</style>
```

В android существует два вида наследования явный через параметр **parent**

`<style name="Child" parent="Parent"/>` и неявный через параметр **name**

`<style name="Parent.Child">` мы будем использовать оба.

Одна из самых распостроненных ошибок это когда разработчик использует оба вида наследования одновременно и уверен что они сольются. К сожалению это не так, если присутствует явное наследование через параметр `parent` то не явное не будет использоваться совсем.

**Base** нужен для того чтобы решать проблему совместимости разных платформ без дублирования кода.
Например возьмем стандартный материал стиль `Headline6`.

```xml
<style name="Base.TextAppearance.Headline6">
	<item name="fontFamily">@string/font_roboto_medium</item>
	<item name="android:textStyle">bold</item>
	<item name="android:textSize">20sp</item>
	<item name="android:letterSpacing">0.0125</item>
</style>
```
Так как параметр `letterSpacing` доступен только с API 21 мы создаем стиль `Base.TextAppearance.Headline6` который наполняется параметрами для всех API.
```xml
<style name="Base.TextAppearance.Headline6">
	<item name="fontFamily">@string/font_roboto_medium</item>
	<item name="android:textStyle">bold</item>
	<item name="android:textSize">20sp</item>
</style>
```
И стиль `TextAppearance.Headline6` которым мы будем пользоваться уже в разметке и в котором параметры для подходяших API.

**res/values/styles_text.xml**
```xml
<style name="TextAppearance.Headline6" parent="Base.TextAppearance.Headline6"/>
```

**res/values-v21/styles_text.xml**
```xml
<style name="TextAppearance.Headline6" parent="Base.TextAppearance.Headline6">
	<item name="android:letterSpacing">0.0125</item>
</style>
```
Таким образом когда мы пользуемся в разметке стилем `TextAppearance.Headline6` у нас будут применяться нужные стили для данной платформы.

Ни когда не пользуйтесь ситлями в разметке которые начинаются с `Base` так как они не полные и нужны для избавления от дублирования кода.

Стили из этого файла должны проставлятся в разметку только с помощью параметра **textAppearance**

`android:textAppearance="TextAppearance.Headline6"`

Распостраненная ошибка это когда проставляют с помощью параметра **style**. Не стоит так делать по двум причинам:

Во первых **textAppearance** может содержать только стили связанные с текстом и мы сразу обнаружем ошибку если стили например layout просачатся в наши текстовые стили.

И во вторых это единственная возможность для слияния двух стилей.
```xml
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="@style/TextAppearance.Button"
        style="@style/Widget.AppCompat.Button"/>
```

Полный файл [styles_text.xml](./uicommon/src/main/res/values/styles_text.xml)

### 4.	Создаем файлы для компонентов
### 5.	Создаем тему нашего приложения
### 6.	Проходимся по нашим экранам перерабатывая на новый стиль
Проходимся по каждому экрану, и заменяем неактуальные стили на новые.

Не забываем пользоваться [tool attributes](https://developer.android.com/studio/write/tool-attributes). Для корректной отрисовки верстки. Многие программисты пренебрегают этим инструментом, хотя на больших проектах он экономит массу времени при поиске нужного экрана и внесении изменений при верстке.

Если нечего написать в tools изпользуйте фэйковые данные [@tools:sample/*](https://developer.android.com/studio/write/tool-attributes#toolssample_resources). Для текста пишем `@tools:sample/lorem[8]` в квадратных скобках колличество слов, для имени `@tools:sample/full_names` для картинок `@tools:sample/backgrounds/scenic` и т.д. Не забываем, что можно создать свои собственные фэйковые данные.

### 7.	Чистим и удаляем все deprecated стили
После прохождения предыдущего пункта, deprecated стили должны быть не исползуемые. Проходимся по ним и проверяем на всякий случай на предмет использования комбинацией клавиш `Alt` + `F7` и удаляем их.

