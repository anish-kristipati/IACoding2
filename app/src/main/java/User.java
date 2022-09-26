public class User {
    private String address;
    public User(){
        address = "1263 Pacific Ave. Kansas City KS";
    }
    public User(String add){
        this.address = add;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
        
    }
    
}
