package com.example.sgsitsnavigation;

import java.util.ArrayList;
import java.util.List;

public class CampusData {

    public static List<Building> getBuildings() {
        List<Building> buildings = new ArrayList<>();

        buildings.add(new Building("Select Destination", ""));

        buildings.add(new Building("Test 1", "Test_1"));
        buildings.add(new Building("Test 2", "Test_2"));
        buildings.add(new Building("Test 3", "Test_3"));



        buildings.add(new Building("ATC Building", "ATC"));
        buildings.add(new Building("SGSITS Guest House", "GUEST_HOUSE"));
        buildings.add(new Building("LT Building", "LT"));
        buildings.add(new Building("SGSITS Girls Hostel", "GIRLS_HOSTEL"));
        buildings.add(new Building("JC Bose Boys Hostel", "JC_BOSE_HOSTEL"));
        buildings.add(new Building("Hostel Play Ground", "HOSTEL_PLAY_GROUND"));
        buildings.add(new Building("MV Boys Hostel", "MV_HOSTEL"));
        buildings.add(new Building("Volleyball Court", "VOLLEYBALL_COURT"));
        buildings.add(new Building("EC Department", "EC"));
        buildings.add(new Building("Physics Lab", "PHYSICS_LAB"));
        buildings.add(new Building("Badminton Court", "BADMINTON_COURT"));
        buildings.add(new Building("CS Department", "CS"));
        buildings.add(new Building("Cafe-91", "CAFE_91"));
        buildings.add(new Building("Engineering Cell", "ENGINEERING_CELL"));
        buildings.add(new Building("Mechanical Department", "MECHANICAL"));
        buildings.add(new Building("Golden Jubilee Auditorium", "GOLDEN_JUBILEE_AUDITORIUM"));
        buildings.add(new Building("Silveria Hall", "SILVERIA_HALL"));
        buildings.add(new Building("Tennis Court", "TENNIS_COURT"));
        buildings.add(new Building("Basketball Court", "BASKETBALL_COURT"));
        buildings.add(new Building("Team GS Racers", "TEAM_GS_RACERS"));
        buildings.add(new Building("DH Hall", "DH_HALL"));
        buildings.add(new Building("Stationary", "STATIONARY"));
        buildings.add(new Building("The Central Library", "CENTRAL_LIBRARY"));
        buildings.add(new Building("Main Gate", "MAIN_GATE"));
        buildings.add(new Building("Director’s Office", "DIRECTORS_OFFICE"));
        buildings.add(new Building("EI Department", "EI"));
        buildings.add(new Building("Civil Department", "CIVIL"));
        buildings.add(new Building("Chemistry Lab", "CHEMISTRY_LAB"));
        buildings.add(new Building("SAC Ground", "SAC_GROUND"));
        buildings.add(new Building("Pharmacy Department", "PHARMACY"));
        buildings.add(new Building("IDEA Lab", "IDEA_LAB"));
        buildings.add(new Building("Incubation Forum (SIF)", "SIF"));
        buildings.add(new Building("Workshop", "WORKSHOP"));

        return buildings;
    }
}