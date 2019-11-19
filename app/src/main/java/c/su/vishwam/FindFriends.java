package c.su.vishwam;

public class FindFriends {
    public String image, type, phone,name,id;

    public FindFriends(){

    }

    public FindFriends(String image, String type, String phone, String name, String id) {
        this.image = image;
        this.type = type;
        this.phone = phone;
        this.name = name;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
