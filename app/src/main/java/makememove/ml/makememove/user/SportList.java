package makememove.ml.makememove.user;

import java.util.List;

public class SportList {
    private List<Sport> sports;

    public SportList(List<Sport> sports) {
        this.sports = sports;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }
}
