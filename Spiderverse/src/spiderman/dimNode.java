package spiderman;
public class dimNode {
     int curD;
     String name;
     int accD;
    public dimNode(int curD, String name, int accD){
        this.accD = accD;
        this.curD = curD;
        this.name = name;
    }
    public int getCurD() {
        return curD;
    }
    public void setCurD(int curD) {
        this.curD = curD;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAccD() {
        return accD;
    }
    public void setAccD(int accD) {
        this.accD = accD;
    }
}
