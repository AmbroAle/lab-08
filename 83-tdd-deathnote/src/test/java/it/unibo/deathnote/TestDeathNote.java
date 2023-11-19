package it.unibo.deathnote;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

import static it.unibo.deathnote.api.DeathNote.RULES;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class TestDeathNote {

    //private DeathNote deathNote;
    private static String DANILO_PIANINI = "Danilo Pianini";
    private static String LIGHT_YAGAMI = "Light Yagami";
     private DeathNoteImpl deathNote = new DeathNoteImpl();


    /**
     * Tests that rule number 0 and negative rules do not exist.
     */
    @Test
    void testIllegalRule() {
        for (final var index: List.of(-1, 0, RULES.size() + 1)) {
            assertThrows(
                new IllegalArgumentThrower() {
                    @Override
                    public void run() {
                        deathNote.getRule(index);
                    }
                }
            );
        }
    }

    /**
     * Checks that no rule is empty or null.
     */
    @Test
    void testRules() {
        for (int i = 1; i < DeathNote.RULES.size(); i++) {
            final var rule = DeathNote.RULES.get(i);
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    /**
     * Checks that the human whose name is written in this DeathNote will die.
     */
    @Test
    void testActualDeath() {
        assertFalse(deathNote.isNameWritten(DANILO_PIANINI));
        deathNote.writeName(DANILO_PIANINI);
        assertTrue(deathNote.isNameWritten(DANILO_PIANINI));
        assertFalse(deathNote.isNameWritten(LIGHT_YAGAMI));
        assertFalse(deathNote.isNameWritten(""));
    }

    /**
     * Checks that only if the cause of death is written within the next 40 milliseconds
     * of writing the person's name, it will happen.
     */
    @Test
    void testDeathCause() throws InterruptedException {
        deathNote.writeName(DANILO_PIANINI);
        sleep(50);
        assertEquals(true, deathNote.writeDeathCause("diarrea"));
    }

    /**
     * Checks that only if the cause of death is written within the next 6 seconds and
     * 40 milliseconds of writing the death's details, it will happen.
     */
    @Test
    void testDeathDetails() throws InterruptedException {
        deathNote.writeName(DANILO_PIANINI);
        sleep(6050);
        assertEquals(true, deathNote.writeDetails("pippo"));
        
    }

    static void assertThrows(final RuntimeExceptionThrower exceptionThrower) {
        try {
            exceptionThrower.run();
            fail();
        } catch (IllegalStateException | IllegalArgumentException e) {
            assertTrue(
                exceptionThrower instanceof IllegalArgumentThrower && e instanceof IllegalArgumentException
                || exceptionThrower instanceof IllegalStateThrower && e instanceof IllegalStateException
            );
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

    private interface RuntimeExceptionThrower {
        void run();
    }

    private interface IllegalStateThrower extends RuntimeExceptionThrower { }

    private interface IllegalArgumentThrower extends RuntimeExceptionThrower { }
}