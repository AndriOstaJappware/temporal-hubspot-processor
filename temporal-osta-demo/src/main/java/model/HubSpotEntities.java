package model;
import java.util.List;

public enum HubSpotEntities {

    CONTACTS("https://api.hubapi.com/crm/v3/objects/contacts", List.of("createdate","email","firstname","hs_object_id","lastmodifieddate","lastname")),
    DEALS("https://api.hubapi.com/crm/v3/objects/deals",List.of(   "amount", "closedate", "createdate", "dealname", "dealstage","hs_lastmodifieddate", "hs_object_id", "pipeline")),
    COMPANIES("https://api.hubapi.com/crm/v3/objects/companies",List.of("createdate", "domain", "hs_lastmodifieddate", "hs_object_id", "name"));

    private String link;
    private List<String> columns;

    HubSpotEntities(String link, List<String> columns) {
        this.link = link;
        this.columns = columns;
    }

    public String getLink() {
        return link;
    }

    public List<String> getColumns() {
        return columns;
    }
}
