package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Фильтр исключает перелёты, у которых общее время ожидания на земле
 * между сегментами превышает 2 часа.
 * 
 * <p>Время на земле рассчитывается как сумма интервалов между:
 * <ul>
 *   <li>прилётом i-го сегмента</li>
 *   <li>и вылетом (i+1)-го сегмента</li>
 * </ul>
 * 
 * <p>Особенности:
 * <ul>
 *   <li>Перелёты с одним сегментом всегда проходят фильтр (время на земле = 0)</li>
 *   <li>Паузы суммируются точно с учётом минут и секунд</li>
 *   <li>Лимит времени: строго больше 2 часов → исключение</li>
 * </ul>
 * 
 * @see Flight
 * @see Segment
 */
public class LongGroundTimeFilter implements FlightFilter{
    /**
     * Максимально допустимое время ожидания на земле между сегментами.
     */
    private static final Duration MAX_GROUND_TIME = Duration.ofHours(2);

    /**
     * Проверяет, что общее время ожидания на земле между сегментами не превышает лимит.
     * 
     * <p>Время на земле рассчитывается как сумма всех интервалов между:
     * <ul>
     *   <li>временем прилёта текущего сегмента ({@code arrivalDate})</li>
     *   <li>и временем вылета следующего сегмента ({@code departureDate})</li>
     * </ul>
    
     * 
     * <p>Особенности алгоритма:
     * <ul>
     *   <li>Перелёты с одним сегментом автоматически проходят фильтрацию (время на земле = 0)</li>
     *   <li>Для перелёта с {@code N} сегментами рассчитывается {@code N-1} пауза</li>
     *   <li>Время суммируется точно с учётом минут, секунд и наносекунд</li>
     *   <li>Лимит: строго больше 2 часов → перелёт отклоняется; 2 часа или меньше → проходит</li>
     * </ul>
    
     * 
     * <p>Пример расчёта:
     * <pre>
     * Сегмент 1: прилёт в 16:40
     * Сегмент 2: вылет в 19:40 → пауза = 3 часа
     * Сегмент 2: прилёт в 20:40
     * Сегмент 3: вылет в 22:40 → пауза = 2 часа
     * Общее время на земле = 3ч + 2ч = 5 часов → перелёт отклонён
     * </pre>
    
     * 
     * @param flight перелёт для проверки
     * @return {@code true}, если суммарное время на земле ≤ 2 часов;
     *         {@code false}, если превышает 2 часа
     * @see Duration#between(java.time.temporal.Temporal, java.time.temporal.Temporal)
     * @see Duration#compareTo(Duration)
     * @see #MAX_GROUND_TIME
     */
    @Override
    public boolean test(Flight flight) {
        Duration totalDuration = Duration.ZERO;
        List<Segment> segments = flight.getSegments();
        if (segments.size() == 1) return true;
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrivalDate = segments.get(i).getArrivalDate();
            LocalDateTime departureDate = segments.get(i + 1).getDepartureDate();
            Duration duration = Duration.between(arrivalDate, departureDate);
            totalDuration = totalDuration.plus(duration);
        }
        
        return totalDuration.compareTo(MAX_GROUND_TIME) <= 0;
    }
    
}
