package in.delbird.chemist.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by deepak on 3/2/16.
 */
public class SearchListModel implements Serializable {

    private String next_page_token;
    private ArrayList<SearchModel> results = new ArrayList<>();

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public ArrayList<SearchModel> getResults() {
        return results;
    }


    public void setResults(ArrayList<SearchModel> results) {
        this.results = results;
    }

    public void add()
    {

    }

}
