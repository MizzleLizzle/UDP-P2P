import java.time.LocalDateTime;
import java.lang.Math.*;
/**
 * Status
 */
public class Status {

    private String standort;
    private Double temperatur;
    private Double luftfeuchte;
    private LocalDateTime timestamp;

    //initializes with given Standort and generates pseudorandom measurements
    public Status(String standort) {
        this.temperatur = Math.random() * 30 - 15;
        this.standort = standort;
        this.luftfeuchte = Math.random() * 100;
        this.timestamp = LocalDateTime.now();
    }

    public Status(String standort, Double temperatur, Double luftfeuchte, LocalDateTime timestamp){
        this.temperatur = temperatur;
        this.standort = standort;
        this.luftfeuchte = luftfeuchte;
        this.timestamp = timestamp;
    }

    //generates new pseudorandom measurements and updates timestamp
    public void wertGenerator() {
        temperatur = (Math.random() * 2 -1) + temperatur;
        this.luftfeuchte = (Math.random() * 4 -2) + luftfeuchte;
        timestamp = LocalDateTime.now();
    }

    public Double getLuftfeuchte() {
        return luftfeuchte;
    }

    public String getStandort() {
        return standort;
    }

    public Double getTemperatur() {
        return temperatur;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public void setLuftfeuchte(Double luftfeuchte) {
        this.luftfeuchte = luftfeuchte;
    }

    public void setTemperatur(Double temperatur) {
        this.temperatur = temperatur;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    //generates readable String representation of Status Object
    public String toString() {
        String out = "Standort: " + standort + "\n";
        out += "Temperatur: " + temperatur + "\n";
        out += "Luftfeuchte: " + luftfeuchte + "\n";
        out += "Zeit: " + timestamp + "\n";
        return out;
    }

    //generates structured String representation of Status object, key value pairs are associated by -> and delimited by ,
    public String toStructuredString() {
        String out = "Standort->" + standort;
        out += ",Temperatur->" + temperatur;
        out += ",Luftfeuchte->" + luftfeuchte;
        out += ",Zeit->" + timestamp;
        return out;
    }

    //parses structured String representation of Status to Status object
    public static Status parse(String structuredIn) throws IncorrectStatusStringRepresentation{
        String[] kVPairs = structuredIn.split(",");
        if(kVPairs.length != 4) {
            throw new IncorrectStatusStringRepresentation(structuredIn + "Wrong amount of key Value pairs");
        }
        String[] seqkVPairs = new String[kVPairs.length * 2];
        for (int i = 0; i < kVPairs.length; i++) {
            String[] temp = kVPairs[i].split("->");
            if (temp.length != 2) {
                throw new IncorrectStatusStringRepresentation(structuredIn + " Wrong amount of -> in " + kVPairs[i]);
            }
            seqkVPairs[2*i] = temp[0];
            seqkVPairs[2*i +1] = temp[1];
        }
        int attrFound = 0;
        String Standort = "";
        Double Temperatur = -10000.0;
        Double Luftfeuchte = -10000.0;
        LocalDateTime Zeit = LocalDateTime.now();

        //parse Standort
        for (int i = 0; i < seqkVPairs.length; i++) {
            if (seqkVPairs[i].equals("Standort")) {
                Standort = seqkVPairs[i+1];
                attrFound++;
            }
        }
        //parse Temperatur
        for (int i = 0; i < seqkVPairs.length; i++) {
            if (seqkVPairs[i].equals("Temperatur")) {
                Temperatur = Double.parseDouble(seqkVPairs[i+1]);
                attrFound++;
            }
        }

        //parse Luftfeuchte
        for (int i = 0; i < seqkVPairs.length; i++) {
            if (seqkVPairs[i].equals("Luftfeuchte")) {
                Luftfeuchte = Double.parseDouble(seqkVPairs[i+1]);
                attrFound++;
            }
        }

        //parse Zeit
        for (int i = 0; i < seqkVPairs.length; i++) {
            if (seqkVPairs[i].equals("Zeit")) {
                Zeit = LocalDateTime.parse(seqkVPairs[i+1]);
                attrFound++;
            }
        }
        if (attrFound != 4) {
            throw new IncorrectStatusStringRepresentation(structuredIn + " found " + attrFound + " Attributes");
        }
        return new Status(Standort, Temperatur, Luftfeuchte, Zeit);
    }
}