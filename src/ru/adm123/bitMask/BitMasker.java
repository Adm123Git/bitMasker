package ru.adm123.bitMask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Класс для получения и разбора битовой маски из/в {@link List} {@code boolean}-значений
 */
public class BitMasker {

    private final int capacity;
    private int mask = -1;
    private List<Boolean> list = new ArrayList<>();

    /**
     * Конструктор объекта
     *
     * @param capacity емкость коллекции {@code boolean}-значений, для преобразования которой из/в битовую маску
     * @throws IllegalArgumentException если {@code capacity} лежит вне пределов 1 - 32 включительно
     */
    public BitMasker(int capacity) throws IllegalArgumentException {
        throwCapacityArgumentException(capacity);
        this.capacity = capacity;
    }

    /**
     * Конструктор объекта
     *
     * @param list {@link List} {@code boolean}-значений для формирования битовой маски. Если размер коллекции,
     *             переданной в параметр, отличен от {@link #capacity}, то коллекция или обрезается до
     *             размера {@link #capacity}, или дополняется до него значениями {@code false}
     * @throws IllegalArgumentException если {@code list} == null или его размер лежит вне пределов 1 - 32 включительно
     */
    public BitMasker(List<Boolean> list) throws IllegalArgumentException {
        throwNullListArgumentException(list);
        throwCapacityArgumentException(list.size());
        this.capacity = list.size();
        setList(list);
    }

    /**
     * Метод устанавливает в объект класса битовую маску. Коллекции {@code boolean}-значений, восстановленную
     * именно из этой битовой маски, вернет вызванный на этом объекте метод {@link #getList()}
     *
     * @param mask {@code int}-значение (битовая маска)
     * @throws IllegalArgumentException при некорректно заданном значении битовой маски
     */
    public void setMask(int mask) throws IllegalArgumentException {
        if (mask < 0) {
            throw new IllegalArgumentException("mask must be positive");
        }
        this.mask = mask;
        this.list = getBooleanList(mask);
    }

    /**
     * Метод возвращает битовую маску, хранящуюся в объекте класса.
     *
     * @return {@code int}-значение битовой маски, полученной установленной в объект коллекции {@code boolean}-значений.
     * Если коллекция {@code boolean}-значений в объект не установлена, то вернется
     * значение, установленное методом {@link #setMask(int)} или {@code -1}, если значение не устанавливалось
     */
    public int getMask() {
        return mask;
    }

    /**
     * Метод устанавливает в объект класса коллекцию {@code boolean}-значений. Битовую маску именно этой
     * коллекции вернет вызванный на этом объекте метод {@link #getMask()}
     *
     * @param list {@link List} {@code boolean}-значений для формирования битовой маски. Если размер коллекции,
     *             переданной в параметр, отличен от {@link #capacity}, то коллекция или обрезается до
     *             размера {@link #capacity}, или дополняется до него значениями {@code false}
     * @throws IllegalArgumentException если аргумент {@code null}
     */
    public void setList(List<Boolean> list) throws IllegalArgumentException {
        throwNullListArgumentException(list);
        if (list.size() < capacity) {
            for (int i = 0; i < capacity - list.size(); i++) {
                list.add(false);
            }
        } else {
            while (capacity < list.size()) {
                list.remove(list.size() - 1);
            }
        }
        this.list = list;
        this.mask = getMask(list);
    }

    /**
     * Метод возвращает коллекцию {@code boolean}-значений, хранящуюся в объекте класса.
     *
     * @return {@link List} {@code boolean}-значений, полученный из установленной в объект битовой маски.
     * Если битовая маска в объект не установлена, то вернется коллекция, установленная методом {@link #setList(List)}
     * или пустой {@link List}, если коллекция не устанавливалась.
     */
    public List<Boolean> getList() {
        return list;
    }

    private void throwCapacityArgumentException(int capacity) throws IllegalArgumentException {
        if (capacity > 32 || capacity < 1) {
            throw new IllegalArgumentException("capacity must be less than 33 and more than 0");
        }
    }

    private void throwNullListArgumentException(Collection<?> list) {
        if (list == null) {
            throw new IllegalArgumentException("list must not be null");
        }
    }

    private List<Boolean> getBooleanList(int mask) {
        List<Boolean> list = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            list.add(((int) Math.pow(2, capacity - 1 - i) & mask) > 0);
        }
        return list;
    }

    private int getMask(List<Boolean> booleanList) throws IllegalArgumentException {

        int mask = 0;
        for (int i = 0; i < booleanList.size(); i++) {
            int argument = booleanList.get(i)
                    ? (int) Math.pow(2, booleanList.size() - 1 - i)
                    : 0;
            mask = mask | argument;
        }
        return mask;
    }

}