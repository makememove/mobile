package makememove.ml.makememove.dpsystem.documents;

import java.util.Date;

import makememove.ml.makememove.dpsystem.presenters.Presenter;

public class EventDocument extends Document {
    private Date date;
    private int published;
    private String title;
    private String description;
    private String location;
    private int length;
    private int lowestSkillPoint;
    private int highestSkillPoint;

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
