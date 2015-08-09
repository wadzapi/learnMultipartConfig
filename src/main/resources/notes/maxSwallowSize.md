#Механизмы обработки атрибута maxSwallowSize
 
Заметки о механизмах работы атрибута maxSwallowSize для настройки коннекторов подключения к tomcat 7.0.61.


###Основные направления:
- - -

 - Разобраться почему при настройке max-file-size внутри multipartconfig'a без настройки maxSwallowSize соединение сбрасывается
 - Найти и описать механизмы работы коннекторов при настройке указанных атрибутов
 - Найти возможность обхода и настройки корректного механизма обработки при превышении максимального размера
 - Доработать FailedRequestFilter с возможность нового механизма (отдавать 413)
 - Доработать FailedRequestFilter для настройки максимально допустимых размеров, передаваемых с артибутами запроса
 - Рассмотреть возможность динамического создания фильтров максимального размера загружаемого файла
 
## Поведение при настройке maxSwallowSize
- - -

###1. max-file-size=10485760 и maxSwallowSize=-1
max-file-size=10485760 и maxSwallowSize=-1

###2.max-file-size=10485760 и maxSwallowSize=10485760
Происходит сброс соединения на строне сервера, при этом происходит сброс буфера соединения без возможности получения нормального ответа на стороне клиента.
В коде сброс происходит из-за /org/apache/coyote/http11/filters/IdentityInputFilter.java:190 при этом выкидывается IOException(sm.getString("inputFilter.maxSwallow"));
О возможности клиента получить ответ сделана ремарка в коде:
> // Note: We do not fail early so the client has a chance to  
> // read the response before the connection is closed. See:

http://httpd.apache.org/docs/2.0/misc/fin_wait_2.html#appendix


