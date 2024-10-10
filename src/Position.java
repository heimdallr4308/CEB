public class Position {
    private String positionName;
    private String positionStatus;

    public Position(String positionName, String positionStatus) {
        this.positionName = positionName;
        this.positionStatus = positionStatus;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getPositionStatus() {
        return positionStatus;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setPositionStatus(String positionStatus) {
        this.positionStatus = positionStatus;
    }
}