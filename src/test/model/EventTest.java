package model;

// import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

// unit tests for the Event class
public class EventTest {
    private Event event;
    private Date date;

    @BeforeEach
    public void runBefore() {
        event = new Event("Test Event");
        date = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Test Event", event.getDescription());

        // create Calendar objects to extract date and time fields
        Calendar calendarExpected = Calendar.getInstance();
        Calendar calendarActual = Calendar.getInstance();
        calendarExpected.setTime(date);
        calendarActual.setTime(event.getDate());

        assertEquals(calendarExpected.get(Calendar.YEAR), calendarActual.get(Calendar.YEAR));
        assertEquals(calendarExpected.get(Calendar.MONTH), calendarActual.get(Calendar.MONTH));
        assertEquals(calendarExpected.get(Calendar.DAY_OF_MONTH), calendarActual.get(Calendar.DAY_OF_MONTH));
        assertEquals(calendarExpected.get(Calendar.HOUR_OF_DAY), calendarActual.get(Calendar.HOUR_OF_DAY));
        assertEquals(calendarExpected.get(Calendar.MINUTE), calendarActual.get(Calendar.MINUTE));
        assertEquals(calendarExpected.get(Calendar.SECOND), calendarActual.get(Calendar.SECOND));
        // assertEquals(calendarExpected.get(Calendar.MILLISECOND),
        // calendarActual.get(Calendar.MILLISECOND));

        // assertEquals(date, event.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Test Event", event.toString());
    }

    @Test
    public void testEquals() {
        Event sameEvent = new Event(event.getDescription());
        Event differentEvent = new Event("Different Event");

        assertTrue(event.equals(event));
        assertFalse(event.equals(null));
        assertTrue(event.equals(sameEvent));
        assertFalse(event.equals(differentEvent));
    }

    // @Test
    // public void testHashCode() {
    // Event sameEvent = new Event(event.getDescription());
    // assertEquals(event.hashCode(), sameEvent.hashCode());
    // }
}
