# netology_javacore_http_cats
Working with http
## Задача 1. Запрос на получение списка фактов о кошках
Код по [ссылке](https://github.com/A-Sakhmina/netology_javacore_http_cats/tree/master/src/main/java)
### Задание
Прочитать весь [список фактов о кошках в http](https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats) и вернуть только те факты, у которых поле upvotes не равно `null`.
### Реализация
* чтение http
* преобразование json в java-объекты(`Post`- класс, в который будем преобразовывать json post в java post; описание структуры факта о кошке)
* фильтрация списка java-объектов(`List<Post>`) с помощью метода `filterByVotes()`, который возвращает список