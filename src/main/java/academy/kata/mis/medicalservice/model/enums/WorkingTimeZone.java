package academy.kata.mis.medicalservice.model.enums;

import java.time.LocalTime;
import java.util.List;

public enum WorkingTimeZone {
    MORNING(
            List.of(
                    LocalTime.of(7, 0, 0),
                    LocalTime.of(8, 0, 0),
                    LocalTime.of(9, 0, 0),
                    LocalTime.of(10, 0, 0),
                    LocalTime.of(11, 0, 0),
                    LocalTime.of(12, 0, 0),
                    LocalTime.of(13, 0, 0)
            )
    ),
    DAY(
            List.of(
                    LocalTime.of(9, 0, 0),
                    LocalTime.of(10, 0, 0),
                    LocalTime.of(11, 0, 0),
                    LocalTime.of(12, 0, 0),

                    LocalTime.of(14, 0, 0),
                    LocalTime.of(15, 0, 0),
                    LocalTime.of(16, 0, 0),
                    LocalTime.of(17, 0, 0)
            )
    ),
    EVENING(
            List.of(
                    LocalTime.of(14, 0, 0),
                    LocalTime.of(15, 0, 0),
                    LocalTime.of(16, 0, 0),
                    LocalTime.of(17, 0, 0),
                    LocalTime.of(18, 0, 0),
                    LocalTime.of(19, 0, 0),
                    LocalTime.of(20, 0, 0)
            )
    );

    private List<LocalTime> times;

    WorkingTimeZone(List<LocalTime> times) {
        this.times = times;
    }

    public List<LocalTime> getTimes() {
        return times;
    }
}
