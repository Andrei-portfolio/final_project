### Тесты
Тесты находятся в папке src/test/java/tests

### Запуск тестов из Intellij Idea
1. Выделить правой кнопкой папку с тестами и выбрать Run
2. Тесты запускаются в браузере (должен быть установлен локально)
3. Видим прохождение тестов в браузере
4. Результаты для allure сохраняются в папку allure-results

### Запуск тестов из командной строки
1. В терминале/консоли перейти в корень папки проекта
2. Выполнить команду:
    1. mvn clean test

### Формирование отчета после выполнения теста:
1. В терминале выполнить:
   1. allure serve
   2. Будет открыт отчет в браузере по умолчанию

### Запустить тесты удаленно:
1. https://github.com/Andrei-portfolio/final_project //скачиваем проект с тестами
2. Заходим в репозиторий проекта https://github.com/Andrei-portfolio/final_project во вкладку Action
3. Переходим в workflow runs --> Annotations
