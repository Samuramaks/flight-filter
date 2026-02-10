package com.gridnine.testing;
/**
 * Интерфейс для фильтрации перелётов по заданному правилу.
 * 
 * <p>Реализации этого интерфейса определяют критерии валидности перелёта.
 * Метод {@link #test(Flight)} возвращает {@code true}, если перелёт
 * проходит фильтрацию (должен быть включён в результат).</p>
 * 
 * 
 * @see DepartureBeforeNowFilter
 * @see InvalidSegmentsFilter
 * @see LongGroundTimeFilter
 */
public interface FlightFilter {
    /**
     * Проверяет, соответствует ли перелёт правилу фильтрации.
     * 
     * @param flight перелёт для проверки
     * @return {@code true}, если перелёт проходит фильтрацию (валиден);
     *         {@code false}, если перелёт должен быть исключён
     */
    boolean test(Flight flight);
}
