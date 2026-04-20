import java.util.List;

public class OrderModel {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private List<String> color;
    private String comment;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMetroStation() { return metroStation; }
    public void setMetroStation(String metroStation) { this.metroStation = metroStation; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<String> getColor() { return color; }
    public void setColor(List<String> color) { this.color = color; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}