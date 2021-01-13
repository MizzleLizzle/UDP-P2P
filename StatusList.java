import java.util.ArrayList;

/**
 * StatusList
 */
public class StatusList {

    ArrayList<Status> localyKnownStatus;
    String standort;

    public StatusList() {
        this.localyKnownStatus = new ArrayList<Status>();
    }

    public StatusList(String standort) {
        this.standort = standort;
        this.localyKnownStatus = new ArrayList<Status>();
        localyKnownStatus.add(new Status(standort));
    }

    public void addStatus(Status newStatus) {
        localyKnownStatus.add(newStatus);
    }

    //updates the calling object with an external list of statuses
    public void update(StatusList externalyKnownStatus) {
        for (Status extStatus : externalyKnownStatus.getLocalyKnownStatus()) {
            Boolean found = false;
            for (Status locStatus : localyKnownStatus) {
                //more recent status for a known standort overwrites the local record
                if (locStatus.getStandort().equals(extStatus.getStandort())) {
                    found = true;
                    if (locStatus.getTimestamp().isBefore(extStatus.getTimestamp())) {
                        locStatus = extStatus;
                    }
                }
            }
            //unknown status is appended to the local record
            if (!found) {
                localyKnownStatus.add(extStatus);
            }
        }
    }

    public ArrayList<Status> getLocalyKnownStatus() {
        return localyKnownStatus;
    }

    //generates structured representation of the list of Status using the structured representation defined for Status, representations are delimited by |
    public String toStructuredString() {
        String out = "";
        for (Status status : localyKnownStatus) {
            out += status.toStructuredString() + "|";
        }
        return out;
    }

    //generates readable representation of the list of statuses
    public String toString() {
        String out = "Bekannte Messwerte bei " + standort +"\n";
        for (Status status : localyKnownStatus) {
            out += status + "\n\n";
        }
        return out;
    }

    //parses structured String representation of list of status to StatusList object
    public static StatusList parse(String formattedIn) throws IncorrectStatusStringRepresentation{
        String[] formattedStatus = formattedIn.split("\\|");
        try {
            StatusList out = new StatusList();
            for (String status : formattedStatus) {
                out.addStatus(Status.parse(status));
            }
            return out;
        } catch (IncorrectStatusStringRepresentation e) {
            throw e;
        }
    }
    
}