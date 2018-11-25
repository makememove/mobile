package makememove.ml.makememove.dpsystem.documents;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import makememove.ml.makememove.dpsystem.documents.subdocuments.Category;
import makememove.ml.makememove.dpsystem.documents.subdocuments.CreatorMockup;
import makememove.ml.makememove.dpsystem.documents.subdocuments.FinishedRank;
import makememove.ml.makememove.dpsystem.documents.subdocuments.ResultDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.SportMockup;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;


public class EventDocument extends Document{
    private int id;
    private Date date;

    @SerializedName("isPublic")
    private int published;

    private String title;
    private String description;
    private String location;
    private int length;
    private int lowestSkillPoint;
    private int highestSkillPoint;
    private int sportId;
    private int creatorId;
    private int categoryId;
    private Category category;
    private SportMockup sport;
    @SerializedName("creator")
    private CreatorMockup creatorMockup;
    private Integer maxAttending;
    private Integer memberLimit;
    private int closed;
    private List<ResultDocument> rankings;
    private List<Team> teams;

    public EventDocument(){
        super();
        closed = 0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLowestSkillPoint() {
        return lowestSkillPoint;
    }

    public void setLowestSkillPoint(int lowestSkillPoint) {
        this.lowestSkillPoint = lowestSkillPoint;
    }

    public int getHighestSkillPoint() {
        return highestSkillPoint;
    }

    public void setHighestSkillPoint(int highestSkillPoint) {
        this.highestSkillPoint = highestSkillPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getCreator() {
        return creatorId;
    }

    public void setCreator(int creator) {
        this.creatorId = creator;
    }

    @Override
    public void setData(Document document) {

    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public CreatorMockup getCreatorMockup() {
        return creatorMockup;
    }

    public void setCreatorMockup(CreatorMockup creatorMockup) {
        this.creatorMockup = creatorMockup;
    }



    public Integer getMemberLimit() {
        return memberLimit;
    }

    public void setMemberLimit(Integer memberLimit) {
        this.memberLimit = memberLimit;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public Integer getMaxAttending() {
        return maxAttending;
    }

    public void setMaxAttending(Integer maxAttending) {
        this.maxAttending = maxAttending;
    }

    public SportMockup getSport() {
        return sport;
    }

    public void setSport(SportMockup sport) {
        this.sport = sport;
    }


    public List<ResultDocument> getRankings() {
        return rankings;
    }

    public void setRankings(List<ResultDocument> rankings) {
        this.rankings = rankings;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getTeamName(int index){
        return teams.get(index).getName();
    }
}
