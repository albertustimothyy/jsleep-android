package AlbertusTimothyGunawanJSleepKM.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable {
    public int accountId;
    public String name;
    public int size;
    public Price price;
    public Facility facility;
    public String address;
    public BedType bedType;
    public City city;
    public ArrayList<Date> booked = new ArrayList<>();
}
