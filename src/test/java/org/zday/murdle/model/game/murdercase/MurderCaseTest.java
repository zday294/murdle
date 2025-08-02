package org.zday.murdle.model.game.murdercase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zday.murdle.model.game.murdercase.suspect.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MurderCaseTest {
    MurderCase murderCase;
    MurderCase murderCaseWithMotive;

    @BeforeEach
    void setup() {
        // Persons
        Person person1 = new Person(
                "John Doe",
                "Tall man with glasses",
                "person_icon.png",
                "Brown",
                "Blue",
                "Right",
                "Gemini",
                "6ft",
                "I was in the kitchen at the time."
        );
        Person person2 = new Person(
                "Jane Smith",
                "Short woman with red hair",
                "person2_icon.png",
                "Red",
                "Green",
                "Left",
                "Leo",
                "5ft 4in",
                "I heard a loud noise from the hallway."
        );
        Person person3 = new Person(
                "Alex Johnson",
                "Middle-aged with a beard",
                "person3_icon.png",
                "Black",
                "Brown",
                "Right",
                "Virgo",
                "5ft 10in",
                "I was reading in the lounge."
        );

        // Locations
        Location location1 = new Location(
                "Library",
                "A quiet room full of books",
                "library_icon.png",
                Location.Doorness.INDOORS
        );
        Location location2 = new Location(
                "Garden",
                "A lush outdoor garden",
                "garden_icon.png",
                Location.Doorness.OUTDOORS
        );
        Location location3 = new Location(
                "Kitchen",
                "A modern kitchen with stainless steel appliances",
                "kitchen_icon.png",
                Location.Doorness.INDOORS
        );

        // Weapons
        Weapon weapon1 = new Weapon(
                "Candlestick",
                "A heavy brass candlestick",
                "candlestick_icon.png",
                Weapon.Weight.HEAVY,
                "Brass"
        );
        Weapon weapon2 = new Weapon(
                "Knife",
                "A sharp kitchen knife",
                "knife_icon.png",
                Weapon.Weight.LIGHT,
                "Steel"
        );
        Weapon weapon3 = new Weapon(
                "Rope",
                "A long, sturdy rope",
                "rope_icon.png",
                Weapon.Weight.MEDIUM,
                "Hemp"
        );

        // Motives
        Motive motive1 = new Motive(
                "Jealousy",
                "Driven by envy",
                "motive_icon.png"
        );

        Motive motive2 = new Motive(
                "Revenge",
                "Seeking payback for a past wrong",
                "revenge_icon.png"
        );

        Motive motive3 = new Motive(
                "Greed",
                "Desire for wealth or possessions",
                "greed_icon.png"
        );

        murderCase = new MurderCase(
                "Case 1",
                "A mysterious murder in the library.",
                List.of(person1, person2, person3),
                List.of(location1, location2, location3),
                List.of(weapon1, weapon2, weapon3),
                List.of(),
                List.of("A broken vase was found."),
                "Check the alibi.",
                null
        );

        murderCaseWithMotive = new MurderCase(
                "Case 2",
                "A crime of passion.",
                List.of(person1, person2, person3),
                List.of(location1, location2, location3),
                List.of(weapon1, weapon2, weapon3),
                List.of(motive1, motive2, motive3),
                List.of("A love letter was discovered."),
                "Look for hidden relationships.",
                null
        );
    }

    @Test
    void getSuspectListByType() {
        // PERSON
        List<Suspect> persons = murderCase.getSuspectListByType(SuspectType.PERSON);
        assertEquals(3, persons.size());
        assertInstanceOf(Person.class, persons.get(0));

        // LOCATION
        List<Suspect> locations = murderCase.getSuspectListByType(SuspectType.LOCATION);
        assertEquals(3, locations.size());
        assertInstanceOf(Location.class, locations.get(0));

        // WEAPON
        List<Suspect> weapons = murderCase.getSuspectListByType(SuspectType.WEAPON);
        assertEquals(3, weapons.size());
        assertInstanceOf(Weapon.class, weapons.get(0));

        // MOTIVE (should be empty for murderCase, 1 for murderCaseWithMotive)
        List<Suspect> motives = murderCase.getSuspectListByType(SuspectType.MOTIVE);
        assertTrue(motives.isEmpty());

        List<Suspect> motivesWith = murderCaseWithMotive.getSuspectListByType(SuspectType.MOTIVE);
        assertEquals(3, motivesWith.size());
        assertInstanceOf(Motive.class, motivesWith.get(0));
    }

    @Test
    void getSuspectTypes() {
        // murderCase has no motives, so MOTIVE should not be included
        List<SuspectType> types = murderCase.getSuspectTypes();
        assertTrue(types.contains(SuspectType.PERSON));
        assertTrue(types.contains(SuspectType.LOCATION));
        assertTrue(types.contains(SuspectType.WEAPON));
        assertFalse(types.contains(SuspectType.MOTIVE));

        // murderCaseWithMotive has motives, so MOTIVE should be included
        List<SuspectType> typesWithMotive = murderCaseWithMotive.getSuspectTypes();
        assertTrue(typesWithMotive.contains(SuspectType.PERSON));
        assertTrue(typesWithMotive.contains(SuspectType.LOCATION));
        assertTrue(typesWithMotive.contains(SuspectType.WEAPON));
        assertTrue(typesWithMotive.contains(SuspectType.MOTIVE));
    }

    @Test
    void askInspectorIrratino_noMotive() {
        // 0 correct
        var noneCorrect = Map.of("person", false, "weapon", false, "location", false);
        assertEquals("Inspector Irratino is confident that you're wrong about everything.",
                murderCase.askInspectorIrratino(noneCorrect));

        // 1 correct: person
        var personCorrect = Map.of("person", true, "weapon", false, "location", false);
        assertEquals("Inspector Irratino believes that you're right about the person",
                murderCase.askInspectorIrratino(personCorrect));

        // 1 correct: weapon
        var weaponCorrect = Map.of("person", false, "weapon", true, "location", false);
        assertEquals("Inspector Irratino believes that you're right about the weapon",
                murderCase.askInspectorIrratino(weaponCorrect));

        // 1 correct: location
        var locationCorrect = Map.of("person", false, "weapon", false, "location", true);
        assertEquals("Inspector Irratino believes that you're right about the location",
                murderCase.askInspectorIrratino(locationCorrect));

        // 2 correct: person and weapon
        var personWeaponCorrect = Map.of("person", true, "weapon", true, "location", false);
        assertEquals("Inspector Irratino believes that you're right about the person and the weapon.",
                murderCase.askInspectorIrratino(personWeaponCorrect));

        // 2 correct: person and location
        var personLocationCorrect = Map.of("person", true, "weapon", false, "location", true);
        assertEquals("Inspector Irratino believes that you're right about the person and the location.",
                murderCase.askInspectorIrratino(personLocationCorrect));

        // 2 correct: weapon and location
        var weaponLocationCorrect = Map.of("person", false, "weapon", true, "location", true);
        assertEquals("Inspector Irratino believes that you're right about the weapon and the location.",
                murderCase.askInspectorIrratino(weaponLocationCorrect));
    }

    @Test
    void askInspectorIrratino_withMotive() {
        // 0 correct
        var noneCorrect = Map.of("person", false, "weapon", false, "location", false, "motive", false);
        assertEquals("Inspector Irratino is confident that you're wrong about everything.",
                murderCaseWithMotive.askInspectorIrratino(noneCorrect));

        // 1 correct
        var personCorrect = Map.of("person", true, "weapon", false, "location", false, "motive", false);
        assertEquals("Inspector Irratino believes that you're right about the person",
                murderCaseWithMotive.askInspectorIrratino(personCorrect));

        var weaponCorrect = Map.of("person", false, "weapon", true, "location", false, "motive", false);
        assertEquals("Inspector Irratino believes that you're right about the weapon",
                murderCaseWithMotive.askInspectorIrratino(weaponCorrect));

        var locationCorrect = Map.of("person", false, "weapon", false, "location", true, "motive", false);
        assertEquals("Inspector Irratino believes that you're right about the location",
                murderCaseWithMotive.askInspectorIrratino(locationCorrect));

        var motiveCorrect = Map.of("person", false, "weapon", false, "location", false, "motive", true);
        assertEquals("Inspector Irratino believes that you're right about the motive",
                murderCaseWithMotive.askInspectorIrratino(motiveCorrect));

        // 2 correct
        var personWeapon = Map.of("person", true, "weapon", true, "location", false, "motive", false);
        assertEquals("Inspector Irratino believes that you're right about the person and the weapon.",
                murderCaseWithMotive.askInspectorIrratino(personWeapon));

        var personLocation = Map.of("person", true, "weapon", false, "location", true, "motive", false);
        assertEquals("Inspector Irratino believes that you're right about the person and the location.",
                murderCaseWithMotive.askInspectorIrratino(personLocation));

        var personMotive = Map.of("person", true, "weapon", false, "location", false, "motive", true);
        assertEquals("Inspector Irratino believes that you're right about the person and the motive.",
                murderCaseWithMotive.askInspectorIrratino(personMotive));

        var weaponLocation = Map.of("person", false, "weapon", true, "location", true, "motive", false);
        assertEquals("Inspector Irratino believes that you're right about the weapon and the location.",
                murderCaseWithMotive.askInspectorIrratino(weaponLocation));

        var weaponMotive = Map.of("person", false, "weapon", true, "location", false, "motive", true);
        assertEquals("Inspector Irratino believes that you're right about the weapon and the motive.",
                murderCaseWithMotive.askInspectorIrratino(weaponMotive));

        var locationMotive = Map.of("person", false, "weapon", false, "location", true, "motive", true);
        assertEquals("Inspector Irratino believes that you're right about the location and the motive.",
                murderCaseWithMotive.askInspectorIrratino(locationMotive));

        // 3 correct
        var personWeaponLocation = Map.of("person", true, "weapon", true, "location", true, "motive", false);
        assertEquals("Inspector Irratino believes that you're right about the person, the weapon, and the location.",
                murderCaseWithMotive.askInspectorIrratino(personWeaponLocation));

        var personWeaponMotive = Map.of("person", true, "weapon", true, "location", false, "motive", true);
        assertEquals("Inspector Irratino believes that you're right about the person, the weapon, and the motive.",
                murderCaseWithMotive.askInspectorIrratino(personWeaponMotive));

        var personLocationMotive = Map.of("person", true, "weapon", false, "location", true, "motive", true);
        assertEquals("Inspector Irratino believes that you're right about the person, the location, and the motive.",
                murderCaseWithMotive.askInspectorIrratino(personLocationMotive));

        var weaponLocationMotive = Map.of("person", false, "weapon", true, "location", true, "motive", true);
        assertEquals("Inspector Irratino believes that you're right about the weapon, the location, and the motive.",
                murderCaseWithMotive.askInspectorIrratino(weaponLocationMotive));
    }
}