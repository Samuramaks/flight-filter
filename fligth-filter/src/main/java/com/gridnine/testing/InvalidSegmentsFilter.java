package com.gridnine.testing;

import java.util.List;
/**
 * Фильтр перелётов, исключающий рейсы с логически некорректными сегментами.
 * 
 * <p>Сегмент считается некорректным, если время прилёта указано раньше времени вылета
 * ({@code arrival < departure}). Такая ситуация невозможна в реальности и указывает
 * на ошибку в данных.</p>
 * 
 * <p>Особенности:
 * <ul>
 *   <li>Проверяются <strong>все сегменты</strong> перелёта</li>
 *   <li>Наличие хотя бы одного некорректного сегмента приводит к отклонению всего перелёта</li>
 *   <li>Перелёт с одним корректным сегментом всегда проходит фильтрацию</li>
 * </ul>

 * 
 * <p>Пример:
 * <ul>
 *   <li>Сегмент [14:40 → 08:40] → некорректен (прилёт раньше вылета)</li>
 *   <li>Перелёт с таким сегментом → полностью исключён из результатов</li>
 * </ul>

 * 
 * @see Flight
 * @see Segment
 * @see FlightFilter
 */
public class InvalidSegmentsFilter implements FlightFilter{

    /**
     * Проверяет, что все сегменты перелёта логически корректны.
     * 
     * @param flight перелёт для проверки
     * @return {@code true}, если во всех сегментах время прилёта не раньше времени вылета;
     *         {@code false}, если найден хотя бы один некорректный сегмент
     */
    @Override
    public boolean test(Flight flight) {
        List<Segment> segment = flight.getSegments();
        return segment.stream().noneMatch(date -> date.getArrivalDate().isBefore(date.getDepartureDate()));
    }
    
}
