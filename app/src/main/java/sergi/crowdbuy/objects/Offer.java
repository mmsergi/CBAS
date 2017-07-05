package sergi.crowdbuy.objects;

import java.io.Serializable;

/**
 * Created by gersoft on 27/04/2017.
 */

public class Offer implements Serializable{

    public String title, description, participants, price, currency, key;

    public Offer(){

    }

    public Offer(String title, String description, String participants, String price, String currency, String key){
        this.title = title;
        this.description = description;
        this.participants = participants;
        this.price = price;
        this.currency = currency;
        this.key = key;
    }

}
