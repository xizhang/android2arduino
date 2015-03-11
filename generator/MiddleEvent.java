public class MiddleEvent {
    public String name;
    public int id;
    
    public MiddleEvent(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    public String toString() {
        return "id:" + id + " name" + name;
    }
}