package zwee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import zwee.ZweeException;

public class DateTimeUtilTest {

    @Test
    public void parseUserDate_isoFormat_parsesCorrectly() {
        LocalDate date = DateTimeUtil.parseUserDate("2021-01-05");
        assertEquals(LocalDate.of(2021, 1, 5), date);
    }

    @Test
    public void parseUserDate_dmyFormat_parsesCorrectly() {
        LocalDate date = DateTimeUtil.parseUserDate("2/12/2019");
        assertEquals(LocalDate.of(2019, 12, 2), date);
    }

    @Test
    public void parseUserDate_invalid_throwsException() {
        assertThrows(ZweeException.class, () -> DateTimeUtil.parseUserDate("not-a-date"));
    }
}