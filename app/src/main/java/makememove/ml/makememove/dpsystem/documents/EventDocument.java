package makememove.ml.makememove.dpsystem.documents;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import makememove.ml.makememove.dpsystem.documents.subdocuments.Category;
import makememove.ml.makememove.dpsystem.documents.subdocuments.CreatorMockup;

public class EventDocument extends Document{
    private int id;
    private Date date;
    @SerializedName("public")
    private int published;
    private String title;
    private String description;
    private String location;
    private int length;
    private int lowestSkillPoint;
    private int highestSkillPoint;
    private int sportId;
    private CreatorMockup creator;
    private Category category;

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

    public CreatorMockup getCreator() {
        return creator;
    }

    public void setCreator(CreatorMockup creator) {
        this.creator = creator;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void setData(Document document) {

    }

/*
    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void getData() {

    }

    @Override
    public void service() {

    }

    public void load(String command){
        presenter.loadDocument(command);
    }

    public void save(String command){
        presenter.saveDocument(command);
    }
*/}
