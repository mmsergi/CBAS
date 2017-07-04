package sergi.crowdbuy.objects;

import java.io.Serializable;

/**
 * Created by gersoft on 27/04/2017.
 */

public class Offer implements Serializable{

    public String title, description, people, price, currency;

    public Offer(){

    }

    public Offer(String title, String description, String people, String price, String currency){
        this.title = title;
        this.description = description;
        this.people = people;
        this.price = price;
        this.currency = currency;
    }

}
