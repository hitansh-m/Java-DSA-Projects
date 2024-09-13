package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered
 * linked list structure that contains USA communitie's Climate and Economic
 * information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;

    /*
     * Constructor
     * 
     * **** DO NOT EDIT *****
     */
    public ClimateEconJustice() {
        firstState = null; // HEAD
    }

    /*
     * Get method to retrieve instance variable firstState
     * 
     * @return firstState
     * 
     * **** DO NOT EDIT *****
     */
    public StateNode getFirstState() {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county,
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     *         **** DO NOT EDIT *****
     */
    public void createLinkedStructure(String inputFile) {

        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine(); // Skips header

        // Reads the file one line at a time
        while (StdIn.hasNextLine()) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
     * Adds a state to the first level of the linked structure.
     * Do nothing if the state is already present in the structure.
     * 
     * @param inputLine a line from the input file
     */
    public void addToStateLevel(String inputLine) {
        String[] s = inputLine.split(",");
        StateNode States = new StateNode(s[2], null, null);
        if (firstState == null) {
            firstState = States;
        }
        StateNode ptr = firstState;
        while (ptr.next != null) {
            if (ptr.getName().equals(States.getName())) {
                break;
            }
            ptr = ptr.next;
        }
        if (ptr.getName().equals(States.getName())) {
            return;
        }
        ptr.next = States;

    }

    /*
     * if (firstState==null){
     * firstState = d;
     * }
     * StateNode ptr = firstState;
     * StateNode ptr2 = ptr.next;
     * while (ptr2 == null){
     * ptr = ptr.next}
     * ptr = d;
     */

    /*
     * Adds a county to a state's list of counties.
     * 
     * Access the state's list of counties' using the down pointer from the State
     * class.
     * Do nothing if the county is already present in the structure.
     * 
     * @param inputFile a line from the input file
     */
    public void addToCountyLevel(String inputLine) {
        String[] s = inputLine.split(",");
        CountyNode Counties = new CountyNode(s[1], null, null);
        StateNode ptr = firstState;

        while (ptr != null) {
            if (ptr.getName().equals(s[2])) {
                if (ptr.down == null) {
                    ptr.down = Counties;
                    return;
                }
                CountyNode ptr2 = ptr.down;
                while (ptr2.next != null) {
                    if (ptr2.getName().equals(Counties.getName())) {
                        return;
                    }
                    ptr2 = ptr2.next;
                }
                if (ptr2.getName().equals(Counties.getName())) {
                    return;
                }
                ptr2.next = Counties;
            }
            ptr = ptr.next;
        }
    }

    /*
     * Adds a community to a county's list of communities.
     * 
     * Access the county through its state
     * - search for the state first,
     * - then search for the county.
     * Use the state name and the county name from the inputLine to search.
     * 
     * Access the state's list of counties using the down pointer from the StateNode
     * class.
     * Access the county's list of communities using the down pointer from the
     * CountyNode class.
     * Do nothing if the community is already present in the structure.
     * 
     * @param inputFile a line from the input file
     */
    public void addToCommunityLevel(String inputLine) {
        String[] s = inputLine.split(",");
        Data data = new Data(Double.parseDouble(s[3]), Double.parseDouble(s[4]), Double.parseDouble(s[5]),
                Double.parseDouble(s[8]), Double.parseDouble(s[9]), s[19], Double.parseDouble(s[49]),
                Double.parseDouble(s[37]), Double.parseDouble(s[121]));
        CommunityNode Communities = new CommunityNode(s[0], null, data);
        CountyNode Counties = new CountyNode(s[1], null, Communities);
        StateNode ptr = firstState;
        while (ptr != null) {
            if (ptr.getName().equals(s[2])) {
                CountyNode ptr2 = ptr.down;
                while (ptr2 != null) {
                    if (ptr2.getName().equals(Counties.getName())) {
                        CommunityNode ptr3 = ptr2.down;
                        if (ptr3 == null) {
                            ptr2.down = Communities;
                            return;
                        }
                        while (ptr3.next != null) {
                            if (ptr3.getName().equals(Communities.getName()))
                                return;
                            ptr3 = ptr3.next;
                        }
                        if (ptr3.getName().equals(Communities.getName()))
                            return;
                        ptr3.next = Communities;
                    }
                    ptr2 = ptr2.next;
                }
            }
            ptr = ptr.next;
        }
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial
     * group
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial
     *                     groups
     * @param race         the race which will be returned
     * @return the amount of communities that contain the same or higher percentage
     *         of the given race
     */
    public int disadvantagedCommunities(double userPrcntage, String race) {
        int count = 0;
        for (StateNode ptr = firstState; ptr != null; ptr = ptr.next) {
            for (CountyNode ptr2 = ptr.down; ptr2 != null; ptr2 = ptr2.next) {
                for (CommunityNode ptr3 = ptr2.down; ptr3 != null; ptr3 = ptr3.next) {
                    if (race.equals("African American")) {
                        if (ptr3.getInfo().getPrcntAfricanAmerican() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("True")) {
                            count++;
                        }
                    }
                    if (race.equals("Native American")) {
                        if (ptr3.getInfo().getPrcntNative() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("True")) {
                            count++;
                        }

                    }
                    if (race.equals("Asian American")) {
                        if (ptr3.getInfo().getPrcntAsian() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("True")) {
                            count++;
                        }

                    }
                    if (race.equals("White American")) {
                        if (ptr3.getInfo().getPrcntWhite() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("True")) {
                            count++;
                        }
                    }
                    if (race.equals("Hispanic American")) {
                        if (ptr3.getInfo().getPrcntHispanic() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("True")) {
                            count++;
                        }
                    }
                }
            }
        }

        return count; // update this line
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial
     * group
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial
     *                     groups
     * @param race         the race which will be returned
     * @return the amount of communities that contain the same or higher percentage
     *         of the given race
     */
    public int nonDisadvantagedCommunities(double userPrcntage, String race) {
        int count = 0;
        for (StateNode ptr = firstState; ptr != null; ptr = ptr.next) {
            for (CountyNode ptr2 = ptr.down; ptr2 != null; ptr2 = ptr2.next) {
                for (CommunityNode ptr3 = ptr2.down; ptr3 != null; ptr3 = ptr3.next) {
                    if (race.equals("African American")) {
                        if (ptr3.getInfo().getPrcntAfricanAmerican() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("False")) {
                            count++;
                        }
                    }
                    if (race.equals("Native American")) {
                        if (ptr3.getInfo().getPrcntNative() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("False")) {
                            count++;
                        }

                    }
                    if (race.equals("Asian American")) {
                        if (ptr3.getInfo().getPrcntAsian() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("False")) {
                            count++;
                        }

                    }
                    if (race.equals("White American")) {
                        if (ptr3.getInfo().getPrcntWhite() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("False")) {
                            count++;
                        }
                    }
                    if (race.equals("Hispanic American")) {
                        if (ptr3.getInfo().getPrcntHispanic() * 100 >= userPrcntage
                                && ptr3.getInfo().getAdvantageStatus().equals("False")) {
                            count++;
                        }
                    }
                }
            }
        }

        return count; // update this line
    }

    /**
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */
    public ArrayList<StateNode> statesPMLevels(double PMlevel) {
        ArrayList<StateNode> s = new ArrayList<>();
        for (StateNode ptr = firstState; ptr != null; ptr = ptr.next) {
            for (CountyNode ptr2 = ptr.down; ptr2 != null; ptr2 = ptr2.next) {
                for (CommunityNode ptr3 = ptr2.down; ptr3 != null; ptr3 = ptr3.next) {
                    if (ptr3.getInfo().getPMlevel() >= PMlevel) {
                        if (!(s.contains(ptr))) {
                            s.add(ptr);
                            break;
                        }
                    }
                }
            }
        }

        return s; // update this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood(double userPercntage) {
        int count = 0;
        for (StateNode ptr = firstState; ptr != null; ptr = ptr.next) {
            for (CountyNode ptr2 = ptr.down; ptr2 != null; ptr2 = ptr2.next) {
                for (CommunityNode ptr3 = ptr2.down; ptr3 != null; ptr3 = ptr3.next) {
                    if (ptr3.getInfo().chanceOfFlood >= userPercntage) {
                        count++;
                    }
                }
            }
        }

        return count; // update this line
    }

    /**
     * Given a state inputted by user, returns the communities with
     * the 10 lowest incomes within said state.
     * 
     * @param stateName the State to be analyzed
     * @return the top 10 lowest income communities in the State, with no particular
     *         order
     */
    public ArrayList<CommunityNode> lowestIncomeCommunities(String stateName) {
        ArrayList<CommunityNode> Comms = new ArrayList<>();
        for (StateNode ptr = firstState; ptr != null; ptr = ptr.next) {
            if (ptr.getName().equals(stateName)) {
                for (CountyNode ptr2 = ptr.down; ptr2 != null; ptr2 = ptr2.next) {
                    for (CommunityNode ptr3 = ptr2.down; ptr3 != null; ptr3 = ptr3.next) {
                        double percentPovertyLine = ptr3.getInfo().getPercentPovertyLine();
                        if (Comms.size() < 10 || Comms.isEmpty()) {
                            Comms.add(ptr3);
                        } else {
                            int m = 0;
                            for (int i = 1; i < Comms.size(); i++) {
                                if (Comms.get(i).getInfo().getPercentPovertyLine() < Comms.get(m).getInfo()
                                        .getPercentPovertyLine()) {
                                    m = i;
                                }
                            }
                            if (percentPovertyLine > Comms.get(m).getInfo().getPercentPovertyLine()) {
                                Comms.set(m, ptr3);
                            }
                        }
                    }
                }
            }
        }
        return Comms;
    }
}
