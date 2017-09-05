package sergi.crowdbuy.objects;

/**
 * Created by gersoft on 05/09/2017.
 */


public class GridObjects {
    private String name;
    private int photo;

    public GridObjects(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
