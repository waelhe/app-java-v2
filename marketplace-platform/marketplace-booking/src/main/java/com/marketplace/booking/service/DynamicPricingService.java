package com.marketplace.booking.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;
import org.springframework.stereotype.Service;

@Service
public class DynamicPricingService {

    private static final EnumSet<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    private static final BigDecimal WEEKEND_MULTIPLIER = new BigDecimal("1.25"); // 25% increase
    private static final BigDecimal PEAK_SEASON_MULTIPLIER = new BigDecimal("1.50"); // 50% increase for summer/winter

    public BigDecimal calculateTotalPrice(BigDecimal basePrice, Instant startDate, Instant endDate) {
        if (basePrice == null || startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            return BigDecimal.ZERO;
        }

        LocalDate start = LocalDate.ofInstant(startDate, ZoneId.systemDefault());
        LocalDate end = LocalDate.ofInstant(endDate, ZoneId.systemDefault());
        
        long totalDays = ChronoUnit.DAYS.between(start, end);
        if (totalDays <= 0) totalDays = 1; // Minimum 1 day

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (int i = 0; i < totalDays; i++) {
            LocalDate currentDay = start.plusDays(i);
            BigDecimal dailyPrice = calculateDailyPrice(basePrice, currentDay);
            totalPrice = totalPrice.add(dailyPrice);
        }

        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDailyPrice(BigDecimal basePrice, LocalDate date) {
        BigDecimal multiplier = BigDecimal.ONE;

        // Weekend logic (Friday/Saturday for many regions)
        if (WEEKEND.contains(date.getDayOfWeek())) {
            multiplier = multiplier.multiply(WEEKEND_MULTIPLIER);
        }

        // Seasonality logic
        int month = date.getMonthValue();
        if (isPeakSeason(month)) {
            multiplier = multiplier.multiply(PEAK_SEASON_MULTIPLIER);
        }

        return basePrice.multiply(multiplier);
    }

    private boolean isPeakSeason(int month) {
        // Peak seasons: Summer (6, 7, 8) and Winter Holidays (12)
        return month == 6 || month == 7 || month == 8 || month == 12;
    }
}
