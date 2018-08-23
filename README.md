# Большая стирка тем и стилей в Android

В докладе расскажу, как легко и без боли написать тему и стили для своего нового приложения или навести порядок в старом. На примере реальной проблемы захламлённости ресурсов, сучившейся у нас на проекте.  Мы пройдемся по каждому этапу преобразования и рассмотрим лучшие решения и практики для организации кода, а также распространённые ошибки.

## Summary

#### [Проблема](#Проблема-1)
#### [1.	Создаем основные ресурсы](#Создаем-основные-ресурсы)
#### [2.	Создаем styles_text.xml](#)
#### [3.	Создаем файлы для компонентов](#)
#### [4.	Создаем тему нашего приложения](#)
#### [5.	Проходимся по нашим экранам перерабатывая на новый стиль](#)
#### [6.	Чистим и удаляем все deprecated стили](#)


### Проблема
Обычно стили добавляются по мере написания приложения, то есть программист пишет фитчу видит отступ или новый компонент на дизайне и добавляет его в ресурсы он не видит целостной картины оформления стилей. Так как программист сосредоточен на написании фитчи, он не прикладывает достаточное количество силы и внимания на написание стилей и оформление ресурсов. Из-за этой ситуации возникает такие ошибки как: дублирование кода, не правильное именование, нарушение ответственности. Если приложение разрабатывается много лет оно может очень сильно зарасти грузом неправильно написанных ресурсов, которые в свою очередь порождают еще больше плохого кода так как в них тяжело разобраться. В докладе я расскажу, как легко и без боли написать тему и стили для своего нового приложения или навести порядок в старом. На примере реальной проблемы захламлённости ресурсов, сучившейся у нас на проекте. Мы пройдемся по каждому этапу преобразования и рассмотрим лучшие решения и практики для организации кода, а также распространённые ошибки.

Наша работа заключается в том, что мы должны написать заранее максимально полное стилевое ядро нашего приложения с четкой и понятной структурой как снаружи, так и внутри. Не явная грань или ее отсутствие первый шаг к бардаку в стилях. Четкая структура снаружи подразумевает, что явно прослеживается разделение стилей уровня приложения от стилей отдельной фитчи. Четкая структура внутри подразумевает, что мы легко понимаем где хранятся стили кнопок, а где для текста. 
Поэтому с самого начала желательно выделить наше стилевое ядро в отдельный модуль, в котором мы в дальнейшем будем вести разработку, но это по желанию так как это не критично.

### 1.	Создаем dimens.xml, colors.xml, **strings.xml** ресурсы
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

### 2.	Создаем styles_text.xml
### 3.	Создаем файлы для компонентов
### 4.	Создаем тему нашего приложения
### 5.	Проходимся по нашим экранам перерабатывая на новый стиль
### 6.	Чистим и удаляем все deprecated стили
