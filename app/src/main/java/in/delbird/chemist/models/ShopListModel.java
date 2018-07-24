package in.delbird.chemist.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by deepak on 3/2/16.
 */
public class ShopListModel implements Serializable {

    private String next_page_token;
    private ArrayList<ShopModel> results = new ArrayList<>();

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public ArrayList<ShopModel> getResults() {
        return results;
    }


    public void setResults(ArrayList<ShopModel> results) {
        this.results = results;
    }

    public void add()
    {

    }

}
