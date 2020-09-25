package activity;

import io.temporal.activity.ActivityInterface;
import java.util.List;
import jsontopojo.CompanyProperties;
import model.Company;

@ActivityInterface
public interface HubSpotActivities {

  /**
   * Read objects from hubspot API.
   *
   * @param apikey api key
   * @return List of Companies
   */
  List<CompanyProperties> read(String apikey);

  /**
   * Transform hubspot objects to java models.
   * @return list of transformed Companies
   */
  List<Company>  process(List<CompanyProperties> companyProperties);

  /**
   * Write java objects to Kafka.
   *
   * @param companies companies
   * @return ;
   */
  void write( List<Company> companies);

  /**
   * Exit condition.
   * @return boolean;
   */
  boolean isExit() ;

}
