package com.gridnine.testing;

import java.time.LocalDateTime;

/**
 * Фильтр перелётов, исключающий рейсы с вылетом в прошлом.
 * 
 * <p>Проверяет только время вылета <strong>первого сегмента</strong> перелёта,
 * так как именно оно определяет начало путешествия. Перелёт считается валидным,
 * если вылет назначен на текущий момент времени или в будущее.</p>
 * 
 * <p>Особенности:
 * <ul>
 *   <li>Перелёты с вылетом "ровно сейчас" проходят фильтрацию</li>
 *   <li>Последующие сегменты (пересадки) не проверяются — важен только старт</li>
 *   <li>Текущее время определяется динамически через {@link LocalDateTime#now()}</li>
 * </ul>

 * 
 * <p>Примеры:
 * <ul>
 *   <li>Вылет 06.02.2026 → исключён (в прошлом)</li>
 *   <li>Вылет 12.02.2026 → проходит фильтр (в будущем)</li>
 *   <li>Вылет сейчас (14:40:00.000) → проходит фильтр</li>
 * </ul>

 * 
 * @see Flight
 * @see Segment
 * @see FlightFilter
 */
public class DepartureBeforeNowFilter implements FlightFilter{

    /**
     * Проверяет, что перелёт не начинается в прошлом.
     * 
     * @param flight перелёт для проверки
     * @return {@code true}, если вылет первого сегмента назначен на текущий момент
     *         или в будущее; {@code false}, если вылет уже произошёл
     */
    @Override
    public boolean test(Flight flight) {
        LocalDateTime firstDeparture = flight.getSegments().get(0).getDepartureDate();
        return !firstDeparture.isBefore(LocalDateTime.now().minusNanos(1));
    }
    
}
