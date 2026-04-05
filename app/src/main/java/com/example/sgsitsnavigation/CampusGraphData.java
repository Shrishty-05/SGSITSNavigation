package com.example.sgsitsnavigation;

public class CampusGraphData {

    public static CampusGraph createCampusGraph() {
        CampusGraph graph = new CampusGraph();

        graph.addNode(new CampusNode("Test1", "Test_1", 22.589686, 75.317163));
        graph.addNode(new CampusNode("Test2", "Test_2", 22.589340, 75.316800));


        // way points
        graph.addNode(new CampusNode("WP1", "Waypoint 1", 22.72635, 75.87356));
        graph.addNode(new CampusNode("WP2", "Waypoint 2", 22.72632, 75.87326));
        graph.addNode(new CampusNode("WP3", "Waypoint 3", 22.726382531360834, 75.87299153466863));
        graph.addNode(new CampusNode("WP4", "Waypoint 4", 22.726752969981348, 75.87302334365417));
        graph.addNode(new CampusNode("WP5", "Waypoint 5", 22.72726755193526, 75.8730635767854));
        graph.addNode(new CampusNode("WP6", "Waypoint 6", 22.7276856483474, 75.87310649212536));
        graph.addNode(new CampusNode("WP7", "Waypoint 7", 22.727698018050656, 75.87224550305109));
        graph.addNode(new CampusNode("WP8", "Waypoint 8", 22.727295311007946, 75.87217941154942));
        graph.addNode(new CampusNode("WP9", "Waypoint 9", 22.72690826324039, 75.87217345935512));
        graph.addNode(new CampusNode("WP10", "Waypoint 10", 22.72655689977965, 75.8722359573951));
        graph.addNode(new CampusNode("WP11", "Waypoint 11", 22.726213770528975, 75.87229250324077));
        graph.addNode(new CampusNode("WP12", "Waypoint 12", 22.72614937907875, 75.87293956262346));
        graph.addNode(new CampusNode("WP13", "Waypoint 13", 22.725629198676636, 75.87288396048976));
        graph.addNode(new CampusNode("WP14", "Waypoint 14", 22.72558523967604, 75.87356905827701));
        graph.addNode(new CampusNode("WP15", "Waypoint 15", 22.725010108155985, 75.87350352718236));
        graph.addNode(new CampusNode("WP16", "Waypoint 16", 22.725039414280186, 75.87286013099784));
        graph.addNode(new CampusNode("WP17", "Waypoint 17", 22.72504561747799, 75.8725305661217));
        graph.addNode(new CampusNode("WP18", "Waypoint 18", 22.72506412523266, 75.87180620334757));
        graph.addNode(new CampusNode("WP19", "Waypoint 19", 22.725101140734804, 75.87109387986997));
        graph.addNode(new CampusNode("WP20", "Waypoint 20", 22.72510484228545, 75.87072467554334));
        graph.addNode(new CampusNode("WP21", "Waypoint 21", 22.72514000700173, 75.86996620157285));
        graph.addNode(new CampusNode("WP22", "Waypoint 22", 22.72541392132735, 75.86995817535018));
        graph.addNode(new CampusNode("WP23", "Waypoint 23", 22.725698939706568, 75.86996419497882));
        graph.addNode(new CampusNode("WP24", "Waypoint 24", 22.725821090256982, 75.86996018188904));
        graph.addNode(new CampusNode("WP25", "Waypoint 25", 22.725695238173877, 75.87035948434718));
        graph.addNode(new CampusNode("WP26", "Waypoint 26", 22.725669327437643, 75.8708711533482));
        graph.addNode(new CampusNode("WP27", "Waypoint 27", 22.72565637206669, 75.87178814442409));
        graph.addNode(new CampusNode("WP28", "Waypoint 28", 22.72564156592836, 75.87222155818296));
        graph.addNode(new CampusNode("WP29", "Waypoint 29", 22.725628610555702, 75.87267102429304));
        graph.addNode(new CampusNode("WP30", "Waypoint 30", 22.726131334057175, 75.871745002317));
        graph.addNode(new CampusNode("WP31", "Waypoint 31", 22.726106935001614, 75.87138524432271));

        // Destination nodes
        graph.addNode(new CampusNode("GATE", "Golden Gate", 22.72630, 75.87413));
        graph.addNode(new CampusNode("ATC", "ATC Building", 22.72600, 75.87323));
        graph.addNode(new CampusNode("GUEST_HOUSE", "SGSITS Guest House", 22.726621882383878, 75.8739862575166));
        graph.addNode(new CampusNode("LT", "LT Building", 22.72591, 75.87364));
        graph.addNode(new CampusNode("GIRLS_HOSTEL", "SGSITS Girls Hostel", 22.72764135006807, 75.87385960594767));
        graph.addNode(new CampusNode("JC_BOSE_HOSTEL", "JC Bose Boys Hostel", 22.727901689496182, 75.87277589181929));
        graph.addNode(new CampusNode("HOSTEL_PLAY_GROUND", "Hostel Play Ground", 22.727384462045027, 75.87266953884864));
        graph.addNode(new CampusNode("MV_HOSTEL", "MV Boys Hostel", 22.72677316459135, 75.87212806086463));
        graph.addNode(new CampusNode("VOLLEYBALL_COURT", "Volleyball Court", 22.726416914464966, 75.8728468928091));
        graph.addNode(new CampusNode("PHYSICS_LAB", "Physics Lab", 22.725777770107452, 75.87263714826459));
        graph.addNode(new CampusNode("EC", "EC Department", 22.725777770107452, 75.87263714826459));
        graph.addNode(new CampusNode("BADMINTON_COURT", "Badminton Court", 22.72531410789783, 75.8738285312777));
        graph.addNode(new CampusNode("CS", "CS Department", 22.725182401612333, 75.87341698356055));
        graph.addNode(new CampusNode("CAFE_91", "Cafe-91", 22.724987152811654, 75.87352595515536));
        graph.addNode(new CampusNode("ENGINEERING_CELL", "Engineering Cell", 22.725524011366474, 75.87274501504965));
        graph.addNode(new CampusNode("MECHANICAL", "Mechanical Department", 22.725200653970262, 75.87273042206783));
        graph.addNode(new CampusNode("GOLDEN_JUBILEE_AUDITORIUM", "Golden Jubilee Auditorium", 22.725092334174665, 75.87223216814168));
        graph.addNode(new CampusNode("SILVERIA_HALL", "Silveria Hall", 22.725466435505002, 75.8719651432613));
        graph.addNode(new CampusNode("TENNIS_COURT", "Tennis Court", 22.7260188041178, 75.87276487674693));
        graph.addNode(new CampusNode("BASKETBALL_COURT", "Basketball Court", 22.725907881214678, 75.87249173735488));
        graph.addNode(new CampusNode("TEAM_GS_RACERS", "Team GS Racers", 22.726096724276974, 75.87211157335415));
        graph.addNode(new CampusNode("DH_HALL", "DH Hall", 22.7259187068436, 75.8722304294318));
        graph.addNode(new CampusNode("STATIONARY", "Stationary", 22.725856239049104, 75.87197561958354));
        graph.addNode(new CampusNode("CENTRAL_LIBRARY", "The Central Library", 22.72566513704539, 75.87143303219506));
        graph.addNode(new CampusNode("MAIN_GATE", "Main Gate", 22.724880884385385, 75.87132976716471));
        graph.addNode(new CampusNode("DIRECTORS_OFFICE", "Director’s Office", 22.725393901263818, 75.87137151358388));
        graph.addNode(new CampusNode("EI", "EI Department", 22.72535364151925, 75.87117708026558));
        graph.addNode(new CampusNode("CIVIL", "Civil Department", 22.725307891797577, 75.87080210178179));
        graph.addNode(new CampusNode("CHEMISTRY_LAB", "Chemistry Lab", 22.725512850453516, 75.87012555329957));
        graph.addNode(new CampusNode("SAC_GROUND", "SAC Ground", 22.725365285904786, 75.8696105126131));
        graph.addNode(new CampusNode("PHARMACY", "Pharmacy Department", 22.725887624704317, 75.86978040365841));
        graph.addNode(new CampusNode("IDEA_LAB", "IDEA Lab", 22.72584907119545, 75.87074446802146));
        graph.addNode(new CampusNode("SIF", "Incubation Forum (SIF)", 22.7257918627425, 75.87109099188079));
        graph.addNode(new CampusNode("WORKSHOP", "Workshop", 22.72587270076637, 75.87122178102078));

        // edges for waypoints
        graph.addBidirectionalEdge("GATE", "WP1", 1);
        graph.addBidirectionalEdge("WP1", "WP2", 1);
        graph.addBidirectionalEdge("WP2", "ATC", 1);
        graph.addBidirectionalEdge("GATE", "WP1", 1);
        graph.addBidirectionalEdge("WP1", "WP3", 1);
        graph.addBidirectionalEdge("WP3", "WP4", 1);

        graph.addBidirectionalEdge("WP1", "WP2", 1);
        graph.addBidirectionalEdge("WP2", "WP3", 1);
        graph.addBidirectionalEdge("WP2", "ATC", 1);

        graph.addBidirectionalEdge("WP4", "WP5", 1);
        graph.addBidirectionalEdge("WP5", "WP6", 1);

        graph.addBidirectionalEdge("WP6", "WP7", 1);
        graph.addBidirectionalEdge("WP7", "WP8", 1);
        graph.addBidirectionalEdge("WP8", "WP9", 1);
        graph.addBidirectionalEdge("WP9", "WP10", 1);
        graph.addBidirectionalEdge("WP10", "WP11", 1);
        graph.addBidirectionalEdge("WP11", "WP12", 1);
        graph.addBidirectionalEdge("WP11", "WP30", 1);

        graph.addBidirectionalEdge("WP30", "WP31", 1);

        graph.addBidirectionalEdge("WP12", "WP13", 1);
        graph.addBidirectionalEdge("WP13", "WP14", 1);

        graph.addBidirectionalEdge("WP14", "WP15", 1);
        graph.addBidirectionalEdge("WP15", "WP16", 1);

        graph.addBidirectionalEdge("WP13", "WP16", 1);

        graph.addBidirectionalEdge("WP16", "WP17", 1);
        graph.addBidirectionalEdge("WP17", "WP18", 1);
        graph.addBidirectionalEdge("WP18", "WP19", 1);
        graph.addBidirectionalEdge("WP19", "WP20", 1);
        graph.addBidirectionalEdge("WP20", "WP21", 1);
        graph.addBidirectionalEdge("WP21", "WP22", 1);
        graph.addBidirectionalEdge("WP22", "WP23", 1);
        graph.addBidirectionalEdge("WP23", "WP24", 1);
        graph.addBidirectionalEdge("WP23", "WP25", 1);
        graph.addBidirectionalEdge("WP25", "WP26", 1);

        graph.addBidirectionalEdge("WP27", "WP28", 1);
        graph.addBidirectionalEdge("WP28", "WP29", 1);
        graph.addBidirectionalEdge("WP29", "WP13", 1);

        // edges for waypoints and buildings
        graph.addBidirectionalEdge("WP1", "GATE", 1);
        graph.addBidirectionalEdge("WP4", "GIRLS_HOSTEL", 1);
        graph.addBidirectionalEdge("WP2", "ATC", 1);
        graph.addBidirectionalEdge("WP1", "LT", 1);

        graph.addBidirectionalEdge("WP6", "JC_BOSE_HOSTEL", 1);
        graph.addBidirectionalEdge("WP7", "JC_BOSE_HOSTEL", 1);

        graph.addBidirectionalEdge("WP10", "MV_HOSTEL", 1);

        graph.addBidirectionalEdge("WP12", "VOLLEYBALL_COURT", 1);
        graph.addBidirectionalEdge("WP12", "HOSTEL_PLAY_GROUND", 1);

        graph.addBidirectionalEdge("WP30", "TEAM_GS_RACERS", 1);

        graph.addBidirectionalEdge("WP29", "DH_HALL", 1);
        graph.addBidirectionalEdge("WP29", "STATIONARY", 1);
        graph.addBidirectionalEdge("WP29", "BASKETBALL_COURT", 1);
        graph.addBidirectionalEdge("WP29", "SILVERIA_HALL", 1);

        graph.addBidirectionalEdge("WP13", "EC", 1);
        graph.addBidirectionalEdge("WP13", "TENNIS_COURT", 1);
        graph.addBidirectionalEdge("WP13", "MECHANICAL", 1);

        graph.addBidirectionalEdge("WP14", "LT", 1);
        graph.addBidirectionalEdge("WP14", "BADMINTON_COURT", 1);

        graph.addBidirectionalEdge("WP15", "BADMINTON_COURT", 1);
        graph.addBidirectionalEdge("WP15", "CAFE_91", 1);

        graph.addBidirectionalEdge("WP16", "MECHANICAL", 1);
        graph.addBidirectionalEdge("WP16", "PHYSICS_LAB", 1);

        graph.addBidirectionalEdge("WP17", "MECHANICAL", 1);

        graph.addBidirectionalEdge("WP18", "GOLDEN_JUBILEE_AUDITORIUM", 1);

        graph.addBidirectionalEdge("WP20", "DIRECTORS_OFFICE", 1);
        graph.addBidirectionalEdge("WP20", "MAIN_GATE", 1);

        graph.addBidirectionalEdge("WP22", "SAC_GROUND", 1);
        graph.addBidirectionalEdge("WP23", "SAC_GROUND", 1);

        graph.addBidirectionalEdge("WP24", "PHARMACY", 1);

        graph.addBidirectionalEdge("WP25", "CHEMISTRY_LAB", 1);

        graph.addBidirectionalEdge("WP25", "CIVIL", 1);
        graph.addBidirectionalEdge("WP26", "IDEA_LAB", 1);
        graph.addBidirectionalEdge("WP26", "SIF", 1);



        graph.addBidirectionalEdge("WP27", "CENTRAL_LIBRARY", 1);

        graph.addBidirectionalEdge("WP28", "WORKSHOP", 1);

        return graph;
    }
}