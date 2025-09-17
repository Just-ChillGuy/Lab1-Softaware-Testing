# Lab1-Softaware-Testing

## Задание, по которому был создан проект:
Определите модель и найдите инвариант структуры данных «очередь». 

Определите функции, которые необходимы для реализации очереди.

Найдите их пред- и постусловия, если очередь не может содержать null.

Реализуйте классы, представляющие циклическую очередь на основе массива.

Класс ArrayQueueModule должен реализовывать один экземпляр очереди с использованием переменных класса.

Класс ArrayQueueADT должен реализовывать очередь в виде абстрактного типа данных (с явной передачей ссылки на экземпляр очереди).

Класс ArrayQueue должен реализовывать очередь в виде класса (с неявной передачей ссылки на экземпляр очереди).

Должны быть реализованы следующие функции (процедуры) / методы:

enqueue – добавить элемент в очередь;

element – первый элемент в очереди;

dequeue – удалить и вернуть первый элемент в очереди;

size – текущий размер очереди;

isEmpty – является ли очередь пустой;

clear – удалить все элементы из очереди.

Модель, инвариант, пред- и постусловия записываются в исходном коде в виде комментариев.

Обратите внимание на инкапсуляцию данных и кода во всех трех реализациях.

Напишите простые тесты к реализованным классам.

Определите интерфейс очереди Queue и опишите его контракт.

Выделите общие части классов ArrayQueue в базовый класс AbstractQueue.

Классы ArrayQueueADT и ArrayQueue должны быть параметризованы и типобезопастны.

Очевидно, что все из перечисленных методов имеют большую важность для проекта (в противном случае он не был бы засчитан), более того они не являются тривиально сделанными в виду того, что использовать надо ЦИКЛИЧЕСКУЮ очередь. Самое сложное, что есть в данном проекте - расчёт индекса в массиве, увеличение его ёмкости, удаление элементов. Основные сценарии: создание и заполнение очереди, обработка элементов и управление очередью, обработка дубликатов, различные граничные сценарии.

Поговорим про сами тесты. Используется подход ААА, так сначала создаются элементы и подготавливаются очереди:

```
        ArrayQueueADT.enqueue(queue, 1);
        ArrayQueueADT.enqueue(queue, 2);
        ArrayQueueADT.enqueue(queue, 3);
        ArrayQueueADT.enqueue(queue, 4);
```
Вызов методов очереди:

```
        int evens = ArrayQueueADT.countIf(queue, x -> ((Integer) x) % 2 == 0);
        int greaterThanTwo = ArrayQueueADT.countIf(queue, x -> ((Integer) x) > 2);
```
И соответсвенно проверка результатов:
```
        assertEquals(2, evens);          // 2 и 4
        assertEquals(2, greaterThanTwo); // 3 и 4
        assertEquals(4, ArrayQueueADT.size(queue));
```
Понятно, что это далеко не единственный пример, скорее это демонстрация того, что данный подход используется.

Хорошо, почему же выполняется FIRST. 
1) Используются небольшие очереди, поэтому всё выполняется быстро;
2) Каждый тест создаёт отдельный экземпляр очереди (@Before setUp()), тесты не зависят друг от друга;
3) Используются одинаковые входные данные, на выходе одни и те же результаты;
4) Результаты автоматически проверяются через assert;
5) Тут должна быть небольшая оговорка, что данный проект был создан мной полтора года назад, а нынешние тесты буквально на днях, но тогда их написал преподаватель, поэтому можно считать, что этот пункт тоже выполняется.

Все функциональности, которые тестировались, описаны в ТЗ. 

Для демонстрации некоторых примеров тестов буду использовать класс MyArrayElementsADTTest (такое странное название нужно было для того, чтобы не было коллизии с тестами преподавателя + его тесты требуют такого названия (да - да, тесты преподавателя тестили на тесты, которые в свою очередь тестят сам проект, а его тесты тоже тестят программу)

```
public void testEnqueueAndElement() {
        ArrayQueueADT.enqueue(queue, "A");

        assertFalse(ArrayQueueADT.isEmpty(queue));
        assertEquals(1, ArrayQueueADT.size(queue));
        assertEquals("A", ArrayQueueADT.element(queue));
    }
```

Данный метод тестирует добавление элемента и проверку первого элемента.

```
public void testIsEmptyInitially() {
        assertTrue(ArrayQueueADT.isEmpty(queue));
        assertEquals(0, ArrayQueueADT.size(queue));
        assertNull(ArrayQueueADT.element(queue));
    }
```

Данный метод тестирует проверку на пустую очередь.

```
public void testEnqueueMultipleAndDequeueOrder() {
        ArrayQueueADT.enqueue(queue, "A");
        ArrayQueueADT.enqueue(queue, "B");
        ArrayQueueADT.enqueue(queue, "C");

        assertEquals("A", ArrayQueueADT.dequeue(queue));
        assertEquals("B", ArrayQueueADT.dequeue(queue));
        assertEquals("C", ArrayQueueADT.dequeue(queue));

        assertTrue(ArrayQueueADT.isEmpty(queue));
    }
```

Данный метод тестирует корректное удаление элементов.

```
public void testClearResetsQueue() {
        ArrayQueueADT.enqueue(queue, "A");
        ArrayQueueADT.enqueue(queue, "B");
        ArrayQueueADT.enqueue(queue, "C");

        ArrayQueueADT.clear(queue);

        assertTrue(ArrayQueueADT.isEmpty(queue));
        assertEquals(0, ArrayQueueADT.size(queue));
        assertNull(ArrayQueueADT.element(queue));
    }
```

Данный метод тестирует корректную очистку очереди.

Результаты тестирования каждого из классов:
<img width="1689" height="560" alt="image" src="https://github.com/user-attachments/assets/c46b647e-f129-4915-b68a-509cf8ffde58" />

<img width="1652" height="580" alt="image" src="https://github.com/user-attachments/assets/2d2e27e2-828d-459d-8697-deef6b5bb6ac" />

<img width="1622" height="634" alt="image" src="https://github.com/user-attachments/assets/88028493-ae97-448a-a791-bce2c1d33b0b" />

Покрытие тестами (в %):

<img width="564" height="174" alt="image" src="https://github.com/user-attachments/assets/484cec97-195b-4a92-b055-5be8b478ae64" />

ArrayQueueADT не покрывается на 100%, т.к. в тестах создаётся очередь, в свою очередь в тестируемом классе просто создаётся объект, ломаться там скорее всего будет нечему, а потому тратит время на его тестирование неинтересно.

В целом тесты, для которых использовался JUnit4 выдались неплохие, да их немного, но почти весь функционал покрыт. Спасибо большое IDE за то, что покрытие можно прямо там посмотреть без настройки окружения.







