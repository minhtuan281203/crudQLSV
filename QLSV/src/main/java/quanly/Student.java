package quanly;

public class Student {
    private String ten;
    private int id;
    private int age;
    private String address;
    private String imageUrl;

    public Student(int id, String ten, int age, String address, String imageUrl) {
        super();
        this.id = id;
        this.ten = ten;
        this.age = age;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
