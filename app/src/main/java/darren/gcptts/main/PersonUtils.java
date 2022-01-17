package darren.gcptts.main;

public class PersonUtils {

    private String personName;
    private String jobProfile;
    private String id;
    private String longDesc;
    private String video;
    private String audio;
    private String language;
    private String sourceImgae;
    private String sourceName;
    private String date;
    private String location;
    private String image;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;

    public PersonUtils(String personName, String jobProfile, String image, String id, String longDesc, String video, String audio
            , String language, String sourceImgae, String sourceName, String date, String location,String category) {
        this.personName = personName;
        this.jobProfile = jobProfile;
        this.image = image;
        this.id = id;
        this.longDesc = longDesc;
        this.video = video;
        this.audio = audio;
        this.language = language;
        this.sourceImgae = sourceImgae;
        this.sourceName = sourceName;
        this.date = date;
        this.location = location;
        this.category = category;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSourceImgae() {
        return sourceImgae;
    }

    public void setSourceImgae(String sourceImgae) {
        this.sourceImgae = sourceImgae;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}