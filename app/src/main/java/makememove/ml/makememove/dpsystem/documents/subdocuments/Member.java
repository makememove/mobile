package makememove.ml.makememove.dpsystem.documents.subdocuments;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.adapters.UserItem;

public class Member {
    private int id;
    private List<UserItem> users;
    public Member(){
        users = new ArrayList<UserItem>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<UserItem> getUsers() {
        return users;
    }

    public void setUsers(List<UserItem> users) {
        this.users = users;
    }
}

