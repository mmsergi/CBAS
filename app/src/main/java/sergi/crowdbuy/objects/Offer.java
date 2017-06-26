package sergi.crowdbuy.objects;

/**
 * Created by gersoft on 27/04/2017.
 */

public class Offer {

    public String title, description, people, price, currency;

    public Offer(String title, String description, String people, String price, String currency){
        this.title = title;
        this.description = description;
        this.people = people;
        this.price = price;
        this.currency = currency;
    }

}
