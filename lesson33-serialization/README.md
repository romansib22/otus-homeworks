# otus-homeworks
## Задание:

Прочитать файл sms.json

Десериализовать файл в Java class

Создать новую структуру: список из полей <chat_sessions.chat_identifier> - <chat_sessions.members.last> - <chat_sessions.messages.belong_number> - <chat_sessions.messages.send_date> - <chat_sessions.messages.text> с группировкой по полю <chat_sessions.messages.belong_number> и сортировкой от более старых сообщений к более новым

Данные дублироваться не должны (файл должен получиться как можно меньше)

Сериализовать полученные данные и записать их в файл (текстовой или бинарный)

Десериализовать полученные данный и вывести результат на консоль

Обязательно (текстовой): json, xml, csv, yml (можно использовать любой вреймворк)

Дополнительно (бинарный): PrtotoBuf, Java Serialization

## Реализовано:
Spring-приложение запускается на порту 8080,
по пути http://localhost:8080/app
В зависимости от значения хэдера Accept возвращается сериализованный объект:
По умолчанию возвращается json, эквивалент application/json
application/xml - XML
application/csv - CSV
application/java (не придумал другого) - Java Standard Serialization