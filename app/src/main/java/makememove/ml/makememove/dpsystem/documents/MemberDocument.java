package makememove.ml.makememove.dpsystem.documents;


import makememove.ml.makememove.dpsystem.documents.subdocuments.Member;

public class MemberDocument extends Document{
    private Member team;

    public MemberDocument(){
        super();
        team = new Member();
    }


    public Member getTeam() {
        return team;
    }

    public void setTeam(Member team) {
        this.team = team;
    }

    @Override
    public void setData(Document document) {
        this.team = ((MemberDocument) document).getTeam();
        sendNotification();
    }
}
